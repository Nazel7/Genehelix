package com.spring.boot.App2.springbootprojectwithdatarest.controllers;

import com.spring.boot.App2.springbootprojectwithdatarest.interfaces.IUser;
import com.spring.boot.App2.springbootprojectwithdatarest.repositories.UserRepo;
import com.spring.boot.App2.springbootprojectwithdatarest.services.EmployeeService;
import com.spring.boot.App2.springbootprojectwithdatarest.entities.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import utils.EmployeeUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login-page")
    public String login() {

        return "fancy-login";
    }

    @GetMapping("/dashboard")
    public String getAuthenticatedUser(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        IUser user = userDetails.getActiveUser();
        System.out.println(user.getFirstName());
        model.addAttribute("activeUser", user);
        model.addAttribute("activeRole", userDetails.getActiveRole());
        switch (userDetails.getActiveRole()) {
            case "ROLE_ADMIN":
                return gotoEmployeePage(model);
            case "ROLE_CUSTOMER":
                return gotoCustomerPage();
            case "ROLE_EMPLOYEE":
                return gotoEmployeePage();
            default:
                return "access-denied";
        }
    }

    private String gotoEmployeePage(Model model) {
        return EmployeeUtil.getEmployeePage(model, employeeService, 1);
    }

    private String gotoCustomerPage(){

        return "customer-page";
    }

    private String gotoEmployeePage(){

        return "employee-page";
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