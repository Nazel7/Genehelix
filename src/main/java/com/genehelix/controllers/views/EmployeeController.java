package com.genehelix.controllers.views;


import com.genehelix.entities.User;
import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.entities.Employee;
import com.genehelix.interfaces.ISecureUserService;
import com.genehelix.utils.Util;
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

    @Autowired
    private IEmployeeCustomerService IEmployeeCustomerService;

    @Autowired
    private ISecureUserService secureUserService;

    @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder) {
        StringTrimmerEditor sTrimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }


    private List<String> reviewList;

    List<Employee> employees;


    public EmployeeController() {
        this.reviewList = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    @GetMapping("/employee")
    public String employeePage() {

        return "employee-page";
    }

    @GetMapping("/employee-list")
    public String getEmployees(Model model) {
        return paginatedPage(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String paginatedPage(@PathVariable("pageNo") int pageNo, Model model) {
        return EmployeeUtil.getEmployeePage(model, IEmployeeCustomerService, pageNo);
    }

    @GetMapping("/showAddForm")
    public String addNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "add-employee";
    }

    @PostMapping("/postEmployee")
    public String postEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult,
                               @RequestParam("cEmail") String cEmail,
                               @RequestParam("password") String password
    ) {
        boolean isEmail = Util.compareString(cEmail, employee.getEmail());
        User user = new User();
        if (bindingResult.hasErrors() || !isEmail) {
            return "add-employee";
        }
        user.setTinyint(true);
        user.setPassWord(Util.hashPassword(password));
        user.setAuthority("ROLE_EMPLOYEE");
        user.setUserName(cEmail);
        user.setEmployee(employee);
        IEmployeeCustomerService.addEmployee(employee);
        secureUserService.saveSecureUser(user);

        return "redirect:/company-employees/employee-list";

    }

    @GetMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("updateLink") int id, Model model) {
        Employee employee = IEmployeeCustomerService.getEmployee(id);
        model.addAttribute("employee", employee);
        return "add-employee";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("deleteLink") int id) {
        IEmployeeCustomerService.deleteEmployee(id);
        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/search")
    public String searchEmployee(@RequestParam("searchEmployees") String employee, Model model) {

        Page<Employee> page = IEmployeeCustomerService.getSearchPaginatedEmployeeHome(employee, 1, 5);
        List<Employee> employees = page.getContent();
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


}
