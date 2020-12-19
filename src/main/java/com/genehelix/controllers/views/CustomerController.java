package com.genehelix.controllers.views;

import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.interfaces.IEmployeeService;
import com.genehelix.utils.CustomerUtil;
import com.genehelix.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private IEmployeeService IEmployeeService;

   private  List<Customer> customers;

    @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder) {
        StringTrimmerEditor sTrimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }

    public CustomerController() {
        this.customers = new ArrayList<>();
    }

    @GetMapping("/customer")
    public String customerPage(){

        return "customer-page";
    }

    @GetMapping("/customers")
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

    @GetMapping("/customers/search")
    public String searchCustomer(@RequestParam("searchEmployeeCustomer") String customerName,
                                 @RequestParam("employeeId") int employeeId, Model model) {
        customers = IEmployeeService.searchEmployeeCustomer(customerName, employeeId);
        return ErrorMessageUtil.errorMessage(customers,
                "There is no customer(s) found.....",
                "empty-customer",
                "customer-for-employee-list", model,
                "emptyCustomer",
                "employeeCustomers"
        );

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
                                       @RequestParam("employeeID") int employeeId, Model model,
                                       BindingResult bindingResult) {

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

    @GetMapping("/customer/showFormForCustomerUpdate")
    public String customerUpdate(@RequestParam("customerUpdate") int id,
                                 Model model) {
        System.out.println("CustomerID: " + id);
        Customer customer = IEmployeeService.getCustomerById(id);
        model.addAttribute("customer", customer);

        return "add-update-customer";

    }

    @PostMapping("/customer/postUpdateEmployeeCustomer")
    public String updateEmployeeCustomer(@ModelAttribute("customer") Customer customer) {

        if (customer.getEmployee().getId() > 0)
            IEmployeeService.addEmployeeCustomer(customer);

        return "redirect:/company-employees/employee-list";
    }



    @GetMapping("/customer/delete")
    public String deleteEmployeeCustomer(@RequestParam("customerDelete") int employeeCustomerId) {
        IEmployeeService.deleteEmployeeCustomer(employeeCustomerId);

        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/customers/general-list")
    public String customerGeneraList(Model model) {

        return paginatedCustomerList(1, model);
    }

    @GetMapping("/customers-generalist/{pageNo}")
    public String paginatedCustomerList(@PathVariable("pageNo") int pageNo, Model model) {

        int pageSize = 5;
        Page<Customer> page = IEmployeeService.getAllCustomers(pageNo, pageSize);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("Customers_list", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "customer-general-page";

    }

    @GetMapping("/customers-generalist/search")
    public String searchCustomerGeneralist(@RequestParam("searchHomeLogonCustomer") String customerProperty, Model model) {

        Page<Customer> page = IEmployeeService.getAllCustomers(customerProperty, 1,5);

        List<Customer> customers = page.getContent();
        if (customers.isEmpty()) {
            String emptyCustomer = "There is no customer found.....";
            System.out.println("REVIEWMESSAGE: " + emptyCustomer);
            model.addAttribute("emptyCustomer", emptyCustomer);
            return "empty-customer";
        }
        model.addAttribute("searchCustomers-generalist", customerProperty);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("Customers_list", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "customer-general-page";
    }

}
