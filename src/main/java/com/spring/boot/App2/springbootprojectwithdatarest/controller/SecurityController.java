package com.spring.boot.App2.springbootprojectwithdatarest.controller;

import com.spring.boot.App2.springbootprojectwithdatarest.appRepositories.UserRepopository;
import com.spring.boot.App2.springbootprojectwithdatarest.appServices.EmployeeService;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Employee;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.MyUserDetails;
import com.spring.boot.App2.springbootprojectwithdatarest.interfaces.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepopository userRepopository;

    @GetMapping("/login-page")
    public String login() {

        return "fancy-login";
    }

@GetMapping("/dashboard")
public String getAuthenticatedUser(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
    IUser user = userDetails.getActiveUser();
    model.addAttribute("activeUser", user);
    model.addAttribute("activeRole", userDetails.getActiveRole());
    switch (userDetails.getActiveRole()) {
        case "ROLE_ADMIN" :
            return gotoEmployeePage(model);
        case "ROLE_CUSTOMER" :
            return gotoEmployeePage(model);
        case "ROLE_EMPLOYEE" :
            return gotoEmployeePage(model);
        default:
            return "access-denied";
    }
}

private String gotoEmployeePage(Model model) {
    int pageSize = 5;
    Page<Employee> page = employeeService.findPaginated(1, pageSize);
    System.out.println(page.getTotalPages());
    model.addAttribute("currentPage", 1);
    model.addAttribute("totalItems", page.getTotalElements());
    model.addAttribute("employeeList", page.getContent());
    model.addAttribute("totalPage", page.getTotalPages());

    return "employee-list";
}

    @GetMapping("/accessDeniedPage")
    public String accessDenied() {

        return "access-denied";
    }


    @PostMapping("/loginProcessing")
    public String loginSuccess(HttpServletRequest request) {
        return "access-denied";
    }

}