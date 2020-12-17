package com.genehelix.controllers.views;


import com.genehelix.interfaces.IEmployeeService;
import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.utils.ErrorMessageUtil;
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


    public EmployeeController() {
        this.reviewList = new ArrayList<>();
        this.employees = new ArrayList<>();
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
        }
            IEmployeeService.addEmployee(employee);
            return "redirect:/company-employees/employee-list";

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
        return ErrorMessageUtil.errorMessage(reviewList,
                "There is no review found.....",
                "empty-review-home",
                "review-list", model,
                "emptyReview",
                "reviews"
        );
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






}
