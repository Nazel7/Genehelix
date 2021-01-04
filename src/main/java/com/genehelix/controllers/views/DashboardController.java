package com.genehelix.controllers.views;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.HcService;
import com.genehelix.entities.User;
import com.genehelix.interfaces.*;
import com.genehelix.services.UserDetailService;
import com.genehelix.services.UsersDetailService;
import net.minidev.json.JSONUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private IEmployeeService IEmployeeService;
    @Autowired
    private IUserSevice iUserService;

    @Autowired
   private IUsersDetailService iUsersDetailService;

    @Autowired
    private IHCServiceListService ihcServiceListService;

    @Autowired
    private IService iService;

   private List<String> serviceList;
    private List<String> hcServiceNames;
    private List<HcServiceResponse> hcServiceResponse;


    public DashboardController() {
        this.serviceList = new ArrayList<>();
        this.hcServiceNames= new ArrayList<>();
        this.hcServiceResponse= new ArrayList<>();
    }

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
        IUserDetail userDetail = userDetails.getActiveUserDetail();
        int userdetailsId= userDetail.getId();
        System.out.println(userdetailsId);

        IUser user = userDetails.getActiveUser();
        System.out.println(user.getFirstName());
        System.out.println(userDetail.getTwitter());
        serviceList= ihcServiceListService.getHCServiceList();
//        System.out.println("UserDetail: "+ userDetail.getOccupation());

        model.addAttribute("userDetail", userDetail);
        model.addAttribute("activeUser", user);
        model.addAttribute("activeRole", userDetails.getActiveRole());
        model.addAttribute("services", serviceList);
        switch (userDetails.getActiveRole()) {
            case "ROLE_ADMIN":
                return gotoEmployeePage(model);
            case "ROLE_CUSTOMER":
//                hcServiceNames= iService.getHcServiceNames(userdetailsId);
//                int lastIndex= hcServiceNames.size()-1;
                hcServiceResponse= iService.getHCServiceNameAndDate(userdetailsId);
                int lastIndex= hcServiceResponse.size()-1;
                HcServiceResponse serviceResponse= hcServiceResponse.get(lastIndex);

                model.addAttribute("latestService", serviceResponse.getName());
                model.addAttribute("latestServiceDate", serviceResponse.getDate());
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
         serviceList= ihcServiceListService.getHCServiceList();

        model.addAttribute("newCustomerDetail", customerDetail);
        model.addAttribute("services", serviceList);
        return "new-customer-detail";
            }


    @GetMapping("/setting/newCustomerDetails")
    public String setNewCustomerDetails(@ModelAttribute("activeUser") User user, Model model){
        CustomerDetails customerDetail= new CustomerDetails();
        List<String> serviceList= ihcServiceListService.getHCServiceList();
        user.setCustomerDetails(customerDetail);
        model.addAttribute("services", serviceList);
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