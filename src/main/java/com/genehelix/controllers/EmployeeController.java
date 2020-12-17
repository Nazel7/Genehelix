package com.genehelix.controllers;


import com.genehelix.interfaces.IEmployeeService;
import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.entities.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.genehelix.utils.EmployeeUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/company-employees")
public class EmployeeController {
    private List<String> reviewList;
    List<Employee> employees;
    List<Customer> customers;

    public EmployeeController() {
        this.reviewList = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    @Autowired
    private IEmployeeService IEmployeeService;

    @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder) {
        StringTrimmerEditor sTrimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }

    @GetMapping("/employee")
    public String employeePage(){

        return "employee-page";
    }

    @GetMapping("/employee-list")
    public String getEmployees(Model model) {
        return paginatedPage(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String paginatedPage(@PathVariable("pageNo") int pageNo, Model model) {
        return EmployeeUtil.getEmployeePage(model, IEmployeeService, pageNo);
    }

    @GetMapping("/showAddForm")
    public String addNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "add-employee";
    }

    @PostMapping("/postEmployee")
    public String postEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-employee";
        } else {
            IEmployeeService.addEmployee(employee);
            return "redirect:/company-employees/employee-list";
        }
    }

    @GetMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("updateLink") int id, Model model) {
        Employee employee = IEmployeeService.getEmployee(id);
        model.addAttribute("employee", employee);
        return "add-employee";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("deleteLink") int id) {
        IEmployeeService.deleteEmployee(id);
        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/search")
    public String searchEmployee(@RequestParam("searchEmployees") String employee, Model model) {

        Page<Employee> page = IEmployeeService.getSearchPaginatedEmployeeHome(employee, 1, 5);
        employees = page.getContent();
        if (employees.isEmpty()) {
            String emptyEmployee = "There is no employee found!";
            model.addAttribute("emptyEmployee", emptyEmployee);
            return "empty-employee";
        }

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("employeeList", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "employee-list";
    }


    @GetMapping("/showEmployeeReview")
    public String reviewList(@RequestParam("showReviews") int employeeID, Model model) {
        System.out.println("wpe=" + employeeID);

        reviewList = IEmployeeService.showReviews(employeeID);
        return errorMessage(reviewList,
                "There is no review found.....",
                "empty-review-home",
                "review-list", model,
                "emptyReview",
                "reviews"
        );
    }

    @GetMapping("/showEmployeeCustomers")
    public String customerList(@RequestParam("showCustomers") int employeeID, Model model) {
        System.out.println("EmployeeId: " + employeeID);
        model.addAttribute("employeeId", employeeID);
        customers = IEmployeeService.getEmployeeCustomerList(employeeID);

        if (customers.isEmpty()) {
            String emptyCustomer = "There is no customer found.....";
            System.out.println("Customer-MESSAGE: " + emptyCustomer);
            model.addAttribute("emptyCustomer", emptyCustomer);
            return "empty-customer";
        } else {
            return paginatedCustomer(1, employeeID, model);
        }

    }

    @GetMapping("/customerPage/{pageNo}")
    public String paginatedCustomer(@PathVariable("pageNo") int pageNo,
                                    @ModelAttribute("employeeId") int employeeId, Model model) {

        int pageSize = 3;

        System.out.println(employeeId);
        Page<Customer> page = IEmployeeService.findPaginatedCustomer(pageNo, pageSize, employeeId);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("employeeCustomers", page.getContent());

        return "customer-for-employee-list";

    }

    @GetMapping("/customers/showFormForAdd")
    public String showFormToAddEmployeeCustomer(@RequestParam("employeeId") int employeeId, Model model) {
        Customer customer = new Customer();
        model.addAttribute("newEmployeeCustomer", customer);
        model.addAttribute("employeeId", employeeId);
        return "add-new-employee-customer";
    }

    @PostMapping("/customers/postEmployeeCustomer")
    public String postEmployeeCustomer(@Valid @ModelAttribute("newEmployeeCustomer") Customer customer,
                                       @RequestParam("employeeID") int employeeId, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "add-new-employee-customer";
        } else {
            System.out.println("employee123: " + employeeId);
            model.addAttribute("employeeIdUpdateCustomer", employeeId);
            Employee employee = IEmployeeService.getEmployee(employeeId);
            if (employee != null) {
                customer.setEmployee(employee);
                IEmployeeService.addEmployeeCustomer(customer);
            }
            return "redirect:/company-employees/employee-list";
        }
    }

    @GetMapping("/showFormForCustomerUpdate")
    public String customerUpdate(@RequestParam("customerUpdate") int id,
                                 Model model) {
        System.out.println("CustomerID: " + id);
        Customer customer = IEmployeeService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "add-update-customer";

    }

    @PostMapping("/customers/postUpdateEmployeeCustomer")
    public String updateEmployeeCustomer(@ModelAttribute("customer") Customer customer) {

        if (customer.getEmployee().getId() > 0)
            IEmployeeService.addEmployeeCustomer(customer);

        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/customers/search")
    public String searchCustomer(@RequestParam("searchEmployeeCustomer") String customerName,
                                 @RequestParam("employeeId") int employeeId, Model model) {
        customers = IEmployeeService.searchEmployeeCustomer(customerName, employeeId);
        return errorMessage(customers,
                "There is no customer(s) found.....",
                "empty-customer",
                "customer-for-employee-list", model,
                "emptyCustomer",
                "employeeCustomers"
        );

    }

    @GetMapping("/delete")
    public String deleteEmployeeCustomer(@RequestParam("customerDelete") int employeeCustomerId) {
        IEmployeeService.deleteEmployeeCustomer(employeeCustomerId);

        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/showFormForEmployeeCustomerReview")
    public String showEmployeeCustomerReview(@RequestParam("customerReview") int customerId, Model model) {
        reviewList = IEmployeeService.showCustomerReviewList(customerId);
        model.addAttribute("customerId", customerId);

        return errorMessage(reviewList,
                "There is no review found....",
                "review-not-found",
                "review-list", model,
                "emptyReview",
                "reviews"
        );

    }

    @GetMapping("/customers/showFormForAddReview")
    public String formForCustomerReview(@RequestParam("customerId") int customerId, Model model) {
        Review review = new Review();
        model.addAttribute("newReview", review);
        model.addAttribute("customerId", customerId);
        return "add-new-review";
    }

    @PostMapping("/customers/postCustomerReview")
    public String postCustomerReview(@ModelAttribute("newReview") Review review,
                                     @RequestParam("customerId") int customerId) {
        System.out.println("ReviewCustomerId: " + customerId);
        Customer customer = IEmployeeService.getCustomerById(customerId);
        if (customer != null) {
            review.setCustomer(customer);
            IEmployeeService.addNewReview(review);
        }
        return "redirect:/company-employees/employee-list";
    }

    public String errorMessage(List<?> list,
                               String message,
                               String returnText1,
                               String returnText2,
                               Model model,
                               String modelAtt1,
                               String modelAtrr2) {
        if (list.isEmpty()) {
            System.out.println("methodMessage: " + message);
            model.addAttribute(modelAtt1, message);
            return returnText1;
        } else {
            model.addAttribute(modelAtrr2, list);
            return returnText2;
        }
    }

}