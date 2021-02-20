package com.genehelix.controllers.views;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.*;
import com.genehelix.interfaces.*;
import com.genehelix.services.UserDetailService;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.genehelix.utils.EmployeeUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DashboardController {

    @Autowired
    private IEmployeeCustomerService IEmployeeCustomerService;
    @Autowired
    private IUsersDetailService iUsersDetailService;

    @Autowired
    private IHCServiceListService ihcServiceListService;

    @Autowired
    private IService iService;

    @Autowired
    private IUserProfilePhotoService iuserPhoto;

    @Autowired
    private IUserProfilePhotoService iUserProfilePhotoService;

    private List<String> serviceListTitle;

    private List<HCServiceList> hcServiceLists;

    private List<HcServiceResponse> hcServiceResponse;


    public DashboardController() {
        this.serviceListTitle = new ArrayList<>();
        this.hcServiceResponse = new ArrayList<>();
        this.hcServiceLists = new ArrayList<>();
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
        IUser user1 = userDetails.getActiveUser();
        System.out.println("userID: " + user1.getId());
        model.addAttribute("activeUser", user1);
        model.addAttribute("activeRole", userDetails.getActiveRole());

//        set User new Service on select-option
        hcServiceLists = ihcServiceListService.findAll();
        System.out.println(hcServiceLists.get(0).getServiceTitle());
        int customersId = user1.getId();
        System.out.println("uuu:" + customersId);
        HcService hcService = new HcService();
        model.addAttribute("newHcService", hcService);

        // I am working here; plan to create a databses table for empty profile photo.
        // CustomerPhoto call...
        CustomerProfilePhoto customerProfilePhoto = iuserPhoto.getCustomerProfilePhotoByCustomerId(user1.getId());
        EmptyProfilePhoto emptyProfilePhoto= iUserProfilePhotoService.getEmptyProfilePhotoById(1);
        System.out.println("photoId"+emptyProfilePhoto.getId());

        switch (userDetails.getActiveRole()) {
            case "ROLE_ADMIN":
                return gotoEmployeePage(model);
            case "ROLE_CUSTOMER":

                CustomerDetails customerDetails = iUsersDetailService.getCustomerDetailsByCustomerId(user1.getId());
                if (customerDetails != null) {
                    System.out.println("CC:" + customersId);
                    hcServiceResponse = iService.getHCServiceNameAndDate(customersId);
                    int lastIndex1 = hcServiceResponse.size() - 1;
                    System.out.println("index " + lastIndex1);
                    if (lastIndex1 >= 0) {
                        HcServiceResponse serviceResponse1 = hcServiceResponse.get(lastIndex1);
                        model.addAttribute("cPhoto", Objects.requireNonNullElse(customerProfilePhoto, emptyProfilePhoto));
                        model.addAttribute("customerId", customersId);
                        model.addAttribute("hcServiceResponse", hcServiceResponse);
                        model.addAttribute("hcServiceResponseIndex", lastIndex1);
                        model.addAttribute("hcServiceLists", hcServiceLists);
                        model.addAttribute("latestServiceDate", serviceResponse1.getDate());
                        model.addAttribute("latestService", serviceResponse1.getName());
                        model.addAttribute("userDetail", customerDetails);
                        return gotoCustomerPage();
                    } else {

                        //if error input all the value in global else..
                            model.addAttribute("userDetail", customerDetails);

                            return gotoCustomerPage();

                    }


                } else {
                    System.out.println("userIDEXP: " + customersId);
                    model.addAttribute("cPhoto", Objects.requireNonNullElse(customerProfilePhoto, emptyProfilePhoto));
                    model.addAttribute("customerId", customersId);
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
        return EmployeeUtil.getEmployeePage(model, IEmployeeCustomerService, 1);
    }

    private String gotoCustomerPage() {


        return "customer-page";
    }

    private String gotoEmployeePage() {

        return "employee-page";
    }

    @GetMapping("/accessDeniedPage")
    public String accessDenied() {

        return "access-denied";
    }

    @GetMapping("/hc-serviceList")
    public String getHcServiceList(Model model){
        hcServiceLists= ihcServiceListService.findAll();
        model.addAttribute("hcServiceLists", hcServiceLists);

        return "register-for-service";
    }


    @PostMapping("/loginProcessing")
    public String loginSuccess(HttpServletRequest request) {
        return "access-denied";
    }


    @PostMapping("/customerService/submit")
    public String postNewCustomerDetailService(@RequestParam("customerId") int cDId,
                                               @ModelAttribute("newHcService") HcService hcService) {

        Customer customer = IEmployeeCustomerService.getCustomerById(cDId);
        System.out.println("custDTSErvice:: " + customer.getEmail());
        hcService.setCustomerh(customer);

        iService.saveHcService(hcService);

        return "redirect:/dashboard";
    }

    // Associate another postMapping
    @GetMapping("/setting/userDetails")
    public String updateCustomerDetails(@RequestParam("customerId") int cdId, Model model) {
        System.out.println("CustomerDID: " + cdId);
        CustomerDetails customerDetail = iUsersDetailService.getCustomerDetailsByCustomerId(cdId);
        System.out.println(customerDetail.getHomeAddress());
        model.addAttribute("customerId", cdId);
        model.addAttribute("updateCustomerDetail", customerDetail);


        return "update-customer-detail";
    }

    @PostMapping("/setting/updateCustomerDetails")
    public String postUpdateCustomerDetail(@ModelAttribute("newCustomerDetail") CustomerDetails customerDetails) {

        iUsersDetailService.saveUserDetails(customerDetails);

        return "redirect:/dashboard";

    }


    @GetMapping("/setting/newCustomerDetails")
    public String setNewCustomerDetails(@AuthenticationPrincipal UserDetailService userDetails, Model model) {
        CustomerDetails customerDetail = new CustomerDetails();
        int activeUserId = userDetails.getActiveUser().getId();
        Customer customer = (Customer) userDetails.getActiveUser();
        CustomerDetails customerDetails = customer.getCustomerDetails();
        System.out.println("cdACT: " + activeUserId);
        System.out.println("activeUserCusId: " + customer.getId());
        System.out.println("newCustomerDUserId: " + activeUserId);
        model.addAttribute("customerDetailCheck", customerDetails);
        model.addAttribute("newCustomerDetail", customerDetail);
        model.addAttribute("activeUserId", activeUserId);

        return "new-customer-detail";
    }

    @PostMapping("/setting/postNewCustomerDetails")
    public String postNewCustomerDetails(@Valid @RequestParam("customerId") int custId,
                                         @ModelAttribute("newCustomerDetail") CustomerDetails customerDetails,
                                         BindingResult bindingResult) {
        System.out.println("CDEEISD: " + custId);
        if (bindingResult.hasErrors()) {

            return "new-customer-detail";
        } else {

            Customer customer = IEmployeeCustomerService.getCustomerById(custId);
            System.out.println("custEmail: " + customer.getEmail());
            customerDetails.setCustomer(customer);
            iUsersDetailService.saveUserDetails(customerDetails);


            return "redirect:/dashboard";
        }

    }

    @GetMapping("/empty-photo-upload")
    public String uploadEmptyProfilePhoto(){

        return "empty-photo";
    }

@PostMapping("/empty-photo/empty_photo-uploaded")
    public  String saveEmptyProfilePhoto(@RequestParam("file")MultipartFile file) throws Exception {

        EmptyProfilePhoto emptyProfilePhoto= new EmptyProfilePhoto();
        String fileName= Util.fileConvertToString(file);
        if (fileName.trim().isEmpty() || fileName.trim().contains("..") || fileName.trim().contains(";")){
            throw new NoSuchFileException(" File contains invalid characters");
        }
        String base64FileString= Util.fileCovertToImageBase64String(file);

        emptyProfilePhoto.setProfilePhoto(base64FileString);
        iUserProfilePhotoService.saveEmptyProfilePhoto(emptyProfilePhoto);

        return "redirect:/dashboard";

}
}