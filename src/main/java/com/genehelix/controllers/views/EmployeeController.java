package com.genehelix.controllers.views;


import com.genehelix.entities.Employee;
import com.genehelix.entities.User;
import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.interfaces.ISecureUserService;
import com.genehelix.utils.EmployeeUtil;
import com.genehelix.utils.Util;
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

    @GetMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("updateLink") int id, Model model) {
        Employee employee = IEmployeeCustomerService.getEmployee(id);
        User user= secureUserService.getUserByEmployeeId(id);

        model.addAttribute("secureUser", user);
        model.addAttribute("employee", employee);

        return "add-update-employee";
    }

    @PostMapping("/postEmployee")
    public String postEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult,
                               @RequestParam("cEmail") String confirmEmail,
                               @RequestParam("password") String password
    ) {
        boolean isEmail = Util.compareString(confirmEmail, employee.getEmail());
        User user = new User();
        if (bindingResult.hasErrors() || !isEmail) {
            return "add-employee";
        }
        user.setTinyint(true);
        user.setPassWord(Util.hashPassword(password));
        user.setAuthority("ROLE_EMPLOYEE");
        user.setUserName(confirmEmail);
        user.setEmployee(employee);
        IEmployeeCustomerService.addEmployee(employee);
        secureUserService.saveSecureUser(user);

        return "redirect:/company-employees/employee-list";

    }



    @PostMapping("/postUpdateEmployee")
    public String postEmployeeUpdate(@Valid @ModelAttribute("employee") Employee employee,
                                     BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "add-employee";
        }

        IEmployeeCustomerService.addEmployee(employee);

        return "redirect:/company-employees/employee-list";
    }

    @PostMapping("/post-log-detail")
    public String postUpdatedEmployee(@ModelAttribute("secureUser") User user,
                                      @RequestParam("userEmployeeId") int eId,
                                      @RequestParam("cEmail") String confirmEmail,
                                      @RequestParam("password") String password){

        Employee employee= IEmployeeCustomerService.getEmployee(eId);
        boolean isSameEmail= Util.compareString(employee.getEmail(), confirmEmail);

        if (!isSameEmail){
           return "add-update-employee";
        }

        String encodedPassword= Util.hashPassword(password);
        user.setPassWord(encodedPassword);
        user.setEmployee(employee);
        user.setUserName(confirmEmail);
        employee.setUser(user);
        secureUserService.saveSecureUser(user);
        IEmployeeCustomerService.addEmployee(employee);

      return "redirect:/company-employees/employee-list";
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
