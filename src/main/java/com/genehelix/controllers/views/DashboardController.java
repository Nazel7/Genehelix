package com.genehelix.controllers.views;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.User;
import com.genehelix.interfaces.*;
import com.genehelix.services.UserDetailService;
import com.genehelix.services.UsersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.genehelix.utils.EmployeeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class DashboardController {

    @Autowired
    private IEmployeeService IEmployeeService;
    @Autowired
    private IUserSevice iUserService;

    @Autowired
   private IUsersDetailService iUsersDetailService;


     @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder) {
        StringTrimmerEditor sTrimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }

    @GetMapping("/login-page")
    public String login() {

        return "fancy-login";
    }

    @GetMapping("/dashboard")
    public String getUserDashboard(@AuthenticationPrincipal UserDetailService userDetails, Model model) {
        IUserDetail userDetail= userDetails.getActiveUserDetail();
        IUser user = userDetails.getActiveUser();
        System.out.println(user.getFirstName());
//        System.out.println("UserDetail: "+ userDetail.getOccupation());

        model.addAttribute("userDetail", userDetail);
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
        return EmployeeUtil.getEmployeePage(model, IEmployeeService, 1);
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

    @GetMapping("/setting/userDetails")
     public String setCustomerDetails(@RequestParam("userID") int userID, Model model){
        User user= iUserService.findUserById(userID);
        CustomerDetails customerDetail= user.getCustomerDetails();

        model.addAttribute("newCustomerDetail", customerDetail);

        return "new-customer-detail";
            }


    @GetMapping("/setting/newCustomerDetails")
    public String setNewCustomerDetails(@ModelAttribute("activeUser") User user, Model model){
        CustomerDetails customerDetail= new CustomerDetails();
        user.setCustomerDetails(customerDetail);

        model.addAttribute("newCustomerDetail", customerDetail);

        return "new-customer-detail";
    }

    @PostMapping("/setting/postNewCustomerDetails")
    public String postNewCustomerDetails(@Valid @ModelAttribute("newCustomerDetail") CustomerDetails customerDetails,
                                         Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){

            return "new-customer-detail";
        }
        CustomerDetails customerDetails1= iUsersDetailService.saveUserDetails(customerDetails);
          model.addAttribute("customerDetails", customerDetails1);

        return "redirect:/dashboard";
    }
}