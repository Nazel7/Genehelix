package com.genehelix.controllers.views;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.*;
import com.genehelix.interfaces.*;
import com.genehelix.services.UserDetailService;
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

@Controller
public class DashboardController {

    @Autowired
    private IEmployeeService IEmployeeService;
    @Autowired
   private IUsersDetailService iUsersDetailService;

    @Autowired
    private IHCServiceListService ihcServiceListService;

    @Autowired
    private IService iService;

//    @Autowired
//    private UserDetailService userDetailService;

   private List<String> serviceListTitle;

   private List<HCServiceList> hcServiceLists;

    private List<HcServiceResponse> hcServiceResponse;


    public DashboardController() {
        this.serviceListTitle = new ArrayList<>();
        this.hcServiceResponse= new ArrayList<>();
        this.hcServiceLists= new ArrayList<>();
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

        // Switching Users (Customer & Employee)
        IUser user = userDetails.getActiveUser();
        model.addAttribute("activeUser", user);
        model.addAttribute("activeRole", userDetails.getActiveRole());

//        set User new Service on select-option
          hcServiceLists= ihcServiceListService.findAll();
        System.out.println(hcServiceLists.get(0).getServiceTitle());
          HcService hcService= new HcService();
        model.addAttribute("newHcService", hcService);

        switch (userDetails.getActiveRole()) {
            case "ROLE_ADMIN":
                return gotoEmployeePage(model);
            case "ROLE_CUSTOMER":
                CustomerDetails customerDetails= iUsersDetailService.getCustomerDetailsByCustomerId(user.getId());
                if(customerDetails != null){
                int customersId= user.getId();
                System.out.println("CCCC::"+ customersId);
                hcServiceResponse= iService.getHCServiceNameAndDate(customersId);
                    int lastIndex1= hcServiceResponse.size()-1;
                    System.out.println("index "+lastIndex1);
                    if(lastIndex1 >= 0){
                HcServiceResponse serviceResponse1= hcServiceResponse.get(lastIndex1);
                        model.addAttribute("customerId", customersId);
                        model.addAttribute("hcServiceResponse", hcServiceResponse);
                        model.addAttribute("hcServiceResponseIndex", lastIndex1);
                        model.addAttribute("hcServiceLists", hcServiceLists);
                        model.addAttribute("latestServiceDate", serviceResponse1.getDate());
                        model.addAttribute("latestService", serviceResponse1.getName());
                        model.addAttribute("userDetail", customerDetails);

                        return gotoCustomerPage();
                    }
                    else {
                        model.addAttribute("hcServiceLists", hcServiceLists);
                        model.addAttribute("userDetail", customerDetails);
                        return gotoCustomerPage();
                    }


                }else{
                    model.addAttribute("hcServiceLists", hcServiceLists);
                    return gotoCustomerPage();
                }


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


    @PostMapping("/customerService/submit")
    public String postNewCustomerDetailService(@RequestParam("customerId") int cDId,
                                               @ModelAttribute("newHcService") HcService hcService){

      Customer customer= IEmployeeService.getCustomerById(cDId);
        System.out.println("custDTSErvice:: "+ customer.getEmail());
        hcService.setCustomerh(customer);

        iService.saveHcService(hcService);

        return "redirect:/dashboard";
    }

    // Associate another postMapping
    @GetMapping("/setting/userDetails")
     public String updateCustomerDetails(@RequestParam("customerId") int cdId, Model model){
        System.out.println("CustomerDID: "+ cdId);
        CustomerDetails customerDetail= iUsersDetailService.getCustomerDetailsByCustomerId(cdId);
        System.out.println(customerDetail.getHomeAddress());
        model.addAttribute("customerId", cdId);
            model.addAttribute("newCustomerDetail", customerDetail);

            return "new-customer-detail";
        }

        @PostMapping("/setting/updateCustomerDetails")
        public String postUpdateCustomerDetail(@ModelAttribute("newCustomerDetail") CustomerDetails customerDetails){

            iUsersDetailService.saveUserDetails(customerDetails);

            return "redirect:/dashboard";

        }


    @GetMapping("/setting/newCustomerDetails")
    public String setNewCustomerDetails(@ModelAttribute("activeUser") User user, Model model){
        CustomerDetails customerDetail= new CustomerDetails();
       int activeUserId= user.getId();
        System.out.println("newCustomerDUserId: " + activeUserId);
        model.addAttribute("newCustomerDetail", customerDetail);
        model.addAttribute("activeUserId", activeUserId);

        return "new-customer-detail";
    }

    @PostMapping("/setting/postNewCustomerDetails")
    public String postNewCustomerDetails(@Valid @ModelAttribute("newCustomerDetail") CustomerDetails customerDetails,
                                          Model model,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){

            return "new-customer-detail";
        }
//        userDetailService.setUserDetail(customerDetails);
        CustomerDetails customerDetails1= iUsersDetailService.saveUserDetails(customerDetails);

          model.addAttribute("customerDetails", customerDetails1);

        return "redirect:/dashboard";
    }




}