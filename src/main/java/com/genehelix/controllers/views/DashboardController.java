package com.genehelix.controllers.views;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.*;
import com.genehelix.interfaces.*;
import com.genehelix.services.UserDetailService;
import com.genehelix.utils.EmployeeUtil;
import com.genehelix.utils.ErrorMessageUtil;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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
    private IHcService iHcService;

    @Autowired
    private IUserProfilePhotoService iuserPhoto;

    @Autowired
    private IUserProfilePhotoService iUserProfilePhotoService;

    @Autowired
    private ISecureUserService iSecureUserService;

    @Autowired
    private IMedicalResultStatusService statusService;


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
        HcService hcService = new HcService();

//        set User new Service on select-option
        hcServiceLists = ihcServiceListService.findAll();
        int userId = user1.getId();

        // I am working here; plan to create a databses table for empty profile photo.
        // CustomerPhoto call...
        CustomerProfilePhoto customerProfilePhoto = iuserPhoto.getCustomerProfilePhotoByCustomerId(user1.getId());

        EmployeeProfilePhoto employeeProfilePhoto= iuserPhoto.getEmployeeProfilePhotoByEmployeeId(userId);

        EmptyProfilePhoto emptyProfilePhoto= iUserProfilePhotoService.getEmptyProfilePhotoById(1);

        model.addAttribute("activeUser", user1);
        model.addAttribute("activeRole", userDetails.getActiveRole());
        model.addAttribute("newHcService", hcService);


        switch (userDetails.getActiveRole()) {
            case "ROLE_ADMIN":
                return gotoEmployeePage(model);
            case "ROLE_CUSTOMER":

                CustomerDetails customerDetails = iUsersDetailService.getCustomerDetailsByCustomerId(user1.getId());
                hcServiceResponse = iHcService.getHCServiceNameAndDate(userId);
                int lastIndex1 = hcServiceResponse.size() - 1;

                if (lastIndex1 >= 0) {
                    HcServiceResponse serviceResponse1 = hcServiceResponse.get(lastIndex1);
                    model.addAttribute("latestServiceId", serviceResponse1.getId());
                    model.addAttribute("hcServiceResponse", hcServiceResponse);
                    model.addAttribute("hcServiceResponseIndex", lastIndex1);
                    model.addAttribute("latestServiceDate", serviceResponse1.getDate());
                    model.addAttribute("latestService", serviceResponse1.getName());
                }
                model.addAttribute("userDetail", customerDetails);
                model.addAttribute("cPhoto", Objects.requireNonNullElse(customerProfilePhoto, emptyProfilePhoto));
                model.addAttribute("customerId", userId);
                model.addAttribute("hcServiceLists", hcServiceLists);
                return gotoCustomerPage();


            case "ROLE_EMPLOYEE":


                EmployeeDetails employeeDetails = iUsersDetailService.getEmployeeDetailsByEmployeeId(userId);
                User user= iSecureUserService.getUserByAuthority("ROLE_ADMIN");
                hcServiceResponse = iHcService.getHCServiceNameAndDateForEmployee(userId);
                int lastIndex1ForEmployee = hcServiceResponse.size() - 1;


                if (lastIndex1ForEmployee >= 0) {
                    HcServiceResponse serviceResponse = hcServiceResponse.get(lastIndex1ForEmployee);
                    System.out.println("EmploLASTSER: "+ serviceResponse.getId());
                    model.addAttribute("latestServiceId", serviceResponse.getId());
                    model.addAttribute("hcServiceResponseForEmployee", hcServiceResponse);
                    model.addAttribute("hcServiceResponseIndexForEmployee", lastIndex1ForEmployee);
                    model.addAttribute("latestServiceDateEm", serviceResponse.getDate());
                    model.addAttribute("latestServiceEm", serviceResponse.getName());

                }

                model.addAttribute("adminEmail", user.getUserName());
                model.addAttribute("userDetail", employeeDetails);
                model.addAttribute("ePhoto", Objects.requireNonNullElse(employeeProfilePhoto, emptyProfilePhoto));
                model.addAttribute("employeeId", userId);
                model.addAttribute("hcServiceLists", hcServiceLists);

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

    @GetMapping("/new-hc-serviceList")
    public String getHcServiceList(@RequestParam("userId") int cId, Model model){

        hcServiceLists= ihcServiceListService.findAll();
        HcService hcService= new HcService();

        model.addAttribute("newHcService", hcService);
        model.addAttribute("customerId", cId);
        model.addAttribute("hcServiceLists", hcServiceLists);

        return "register-for-service";
    }

    @PostMapping("/customerService/submit")
    public String postNewCustomerDetailService(@RequestParam("customerId") int cDId,
                                               @ModelAttribute("newHcService") HcService hcService) {

        MedicalResultStatus medicalResultStatus= new MedicalResultStatus();
        Customer customer = IEmployeeCustomerService.getCustomerById(cDId);
        if (customer == null){
            return "redirect:/dashboard";
        }
        hcService.setCustomerh(customer);

        medicalResultStatus.setHcService(hcService);
        MedicalResultStatus status= Util.setMR_statusToNR(medicalResultStatus);
        medicalResultStatus.setStatus(status.getStatus());

        iHcService.saveHcService(hcService);
        statusService.saveMedicalResultStatus(medicalResultStatus);


        return "redirect:/dashboard";
    }


    @PostMapping("/loginProcessing")
    public String loginSuccess(HttpServletRequest request) {
        return "access-denied";
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

// Employee APIs
@PostMapping("/employeeService/submit")
public String postNewEmployeeDetailService(@RequestParam("employeeId") int eId,
                                           @ModelAttribute("newHcService") HcService hcService) {

        MedicalResultStatus medicalResultStatus= new MedicalResultStatus();

    Employee employee = IEmployeeCustomerService.getEmployee(eId);

    if (employee == null){

        return "redirect:/dashboard";
    }

    hcService.setEmployeeh(employee);

    //    set default mediclResultStatus
    medicalResultStatus.setHcService(hcService);
    MedicalResultStatus status= Util.setMR_statusToNR(medicalResultStatus);
    medicalResultStatus.setStatus(status.getStatus());


    iHcService.saveHcService(hcService);
    statusService.saveMedicalResultStatus(medicalResultStatus);

    return "redirect:/dashboard";
}


    // Associate another postMapping
    @GetMapping("/setting/userDetails-employee")
    public String updateEmployeeDetails(@RequestParam("employeeId") int eId, Model model) {
      EmployeeDetails employeeDetails = iUsersDetailService.getEmployeeDetailsByEmployeeId(eId);
        System.out.println(employeeDetails.getHomeAddress());
        model.addAttribute("employeeId", eId);
        model.addAttribute("updateEmployeeDetail", employeeDetails);


        return "update-employee-detail";
    }

    @PostMapping("/setting/updateEmployeeDetails")
    public String postUpdateEmployeeDetail(@ModelAttribute("newEmployeeDetail") EmployeeDetails employeeDetails) {

        iUsersDetailService.saveUserDetails(employeeDetails);

        return "redirect:/dashboard";

    }


    @GetMapping("/setting/newEmployeeDetails")
    public String setNewEmployeeDetails(@AuthenticationPrincipal UserDetailService userDetails, Model model) {
        EmployeeDetails employeeDetail = new EmployeeDetails();
        int activeUserId = userDetails.getActiveUser().getId();
        Employee employee = (Employee) userDetails.getActiveUser();
        EmployeeDetails employeeDetails = employee.getEmployeeDetails();

        model.addAttribute("employeeDetailCheck", employeeDetails);
        model.addAttribute("newEmployeeDetail",employeeDetail);
        model.addAttribute("activeUserId", activeUserId);

        return "new-employee-detail";
    }

    @PostMapping("/setting/postNewEmployeeDetails")
    public String postNewEmployeeDetails(@Valid @RequestParam("employeeId") int eId,
                                         @ModelAttribute("newEmployeeDetail") EmployeeDetails employeeDetails,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "new-employee-detail";
        }
        else {

            Employee employee = IEmployeeCustomerService.getEmployee(eId);

           employeeDetails.setEmployee(employee);
            iUsersDetailService.saveUserDetails(employeeDetails);


            return "redirect:/dashboard";
        }

    }

    @GetMapping("/employee/e-review")
    public String getReviewForEmployee(@AuthenticationPrincipal UserDetailService userDetails, Model model){
        Employee activeEmployee= (Employee) userDetails.getActiveUser();

       List<String> reviewList = IEmployeeCustomerService.showReviews(activeEmployee.getId());

       model.addAttribute("activeEmployee", activeEmployee);
        return ErrorMessageUtil.errorMessage(reviewList,
                "There is no review found....",
                "review-not-found",
                "review-list", model,
                "emptyReview",
                "reviews"
        );

    }

    @GetMapping("/employee/e-customer-list")
    public String getEmployeeCustomerList(@RequestParam("employeeId") int eId, Model model){

        model.addAttribute("employeeId", eId);
      return employeeCustomerPage(1, eId, model);


    }

    @GetMapping("/e-page/{pageNo}")
    public  String employeeCustomerPage(@PathVariable("pageNo") int pageNo,
                                        @ModelAttribute("employeeId") int eId, Model model){



        int pageSize = 5;

        Page<Customer> page = IEmployeeCustomerService.findPaginatedCustomer(pageNo, pageSize, eId);

        model.addAttribute("employeeId", eId);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("employeeCustomers", page.getContent());


        return "e-page-customer-list";
    }

}