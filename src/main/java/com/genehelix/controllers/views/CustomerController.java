package com.genehelix.controllers.views;

import com.genehelix.entities.*;
import com.genehelix.interfaces.*;
import com.genehelix.utils.ErrorMessageUtil;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private IEmployeeCustomerService IEmployeeCustomerService;

    @Autowired
    private IUserResumeService iUserResumeService;

    @Autowired
    IUserProfilePhotoService profilePhotoService;

    @Autowired
    private ISecureUserService secureUserService;

    @Autowired
    private IHcService iHcService;

    @Autowired
    private IMedicalResultStatusService statusService;


    private List<Customer> customers;

    @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder) {
        StringTrimmerEditor sTrimmer = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }

    public CustomerController() {
        this.customers = new ArrayList<>();
    }

    @GetMapping("/customer")
    public String customerPage() {

        return "customer-page";
    }

    @GetMapping("/customer/photoUpload")
    public String gotoCustomerProfilePhotoPage(@RequestParam("c-userId") int cDId, Model model) {
        CustomerProfilePhoto profilePhoto = new CustomerProfilePhoto();
        CustomerProfilePhoto customerProfilePhoto = profilePhotoService.getCustomerProfilePhotoByCustomerId(cDId);
        if (customerProfilePhoto == null) {
            model.addAttribute("userPhoto", null);
            model.addAttribute("PhotoId", null);
            model.addAttribute("customerIdR", cDId);
            model.addAttribute("photoObject", profilePhoto);

            return "customer-photo";
        }

        model.addAttribute("userPhoto", customerProfilePhoto);
        model.addAttribute("PhotoId", customerProfilePhoto.getId());
        model.addAttribute("customerIdR", cDId);
        model.addAttribute("photoObject", profilePhoto);
        return "customer-photo";
    }

    @PostMapping("/customer/c-uploaded_photo")
    public String postCustomerPhoto(@RequestParam("muiltiPartFile") MultipartFile file,
                                    @RequestParam("userId") int cDId,
                                    @ModelAttribute("photoObject") CustomerProfilePhoto customerProfilePhoto,
                                    RedirectAttributes r) {
        String fileName = Util.fileConvertToString(file);
        Customer customer = IEmployeeCustomerService.getCustomerById(cDId);
        try {
            if (!fileName.trim().isEmpty()) {
                profilePhotoService.saveCustomerPorfilePhoto(file, customerProfilePhoto, customer);
                r.addFlashAttribute("message", "Photo Upload Successfully!");

                return "redirect:/dashboard";
            }
            return "customer-photo";
        } catch (Exception e) {
            e.printStackTrace();

            return "customer-photo";
        }


    }

    @GetMapping("/customer/c-photo-update")
    public String updateCustomerPhoto(@RequestParam("userId") int cDId, Model model) {

        CustomerProfilePhoto customerProfilePhoto = profilePhotoService.getCustomerProfilePhotoByCustomerId(cDId);

        model.addAttribute("photoObject", customerProfilePhoto);
        model.addAttribute("customerIdR", cDId);
        model.addAttribute("userPhoto", customerProfilePhoto);

        return "customer-photo-update";

    }

    @GetMapping("/customers")
    public String customerList(@RequestParam("showCustomers") int employeeID, Model model) {
        System.out.println("EmployeeId: " + employeeID);

        model.addAttribute("employeeId", employeeID);
        customers = IEmployeeCustomerService.getEmployeeCustomerList(employeeID);

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
        Page<Customer> page = IEmployeeCustomerService.findPaginatedCustomer(pageNo, pageSize, employeeId);
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
        customers = IEmployeeCustomerService.searchEmployeeCustomer(customerName, employeeId);
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

    @GetMapping("/customer/showFormForCustomerUpdate")
    public String customerUpdate(@RequestParam("customerUpdate") int id, Model model) {
        Customer customer = IEmployeeCustomerService.getCustomerById(id);
        System.out.println(customer.getId());
        User user = secureUserService.getUserByCustomerId(id);


        System.out.println("UserStuff " + user.getCustomer().getId());
        model.addAttribute("secureUser", user.getPassWord());
        model.addAttribute("employeeId", customer.getEmployee().getId());
        model.addAttribute("customerObject", customer);

        return "add-update-employee-customer";

    }

    @PostMapping("/customer/update-customer")
    public String postUpdateEmployeeCustomer(@ModelAttribute("customerObject") Customer customer,
                                             @RequestParam("userEmployeeId") int eId,
                                             @RequestParam("password") String password) {

        Employee employee = IEmployeeCustomerService.getEmployee(eId);
        customer.setEmployee(employee);
        User user = secureUserService.getUserByCustomerId(customer.getId());
        user.setUserName(customer.getEmail());

        if (!Util.compareString(password, user.getPassWord())) {
            user.setPassWord(Util.hashPassword(password));
        } else {
            user.setPassWord(password);
        }


        IEmployeeCustomerService.addEmployeeCustomer(customer);
        secureUserService.saveSecureUser(user);

        return "redirect:/customers?showCustomers=" + eId;
    }


    @PostMapping("/customers/postEmployeeCustomer")
    public String postEmployeeCustomer(@ModelAttribute("newEmployeeCustomer") Customer customer,
                                       @RequestParam("employeeCID") int employeeId,
                                       BindingResult bindingResult,
                                       @RequestParam("cEmail") String cEmail,
                                       @RequestParam("password") String password,
                                       Model model
    ) {

        boolean isEmail = Util.compareString(cEmail, customer.getEmail());
        User user = new User();


        if (bindingResult.hasErrors() || !isEmail) {

            return "add-new-employee-customer";
        } else {

            model.addAttribute("employeeIdUpdateCustomer", employeeId);
            Employee employee = IEmployeeCustomerService.getEmployee(employeeId);
            if (employee != null) {
                user.setAuthority("ROLE_CUSTOMER");
                user.setUserName(customer.getEmail());
                user.setTinyint(true);
                user.setCustomer(customer);
                user.setPassWord(Util.hashPassword(password));
                customer.setEmployee(employee);
                IEmployeeCustomerService.addEmployeeCustomer(customer);
                secureUserService.saveSecureUser(user);
            }
            return "redirect:/company-employees/employee-list";
        }
    }


    @GetMapping("/customer/delete")
    public String deleteEmployeeCustomer(@RequestParam("customerDelete") int employeeCustomerId) {
        IEmployeeCustomerService.deleteEmployeeCustomer(employeeCustomerId);

        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("/customers/general-list")
    public String customerGeneraList(Model model) {

        return paginatedCustomerList(1, model);
    }

    @GetMapping("/customers-generalist/{pageNo}")
    public String paginatedCustomerList(@PathVariable("pageNo") int pageNo, Model model) {

        int pageSize = 5;
        Page<Customer> page = IEmployeeCustomerService.getAllCustomers(pageNo, pageSize);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("Customers_list", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "customer-general-page";

    }

    @GetMapping("/customers-generalist/search")
    public String searchCustomerGeneralist(@RequestParam("searchHomeLogonCustomer") String customerProperty, Model model) {

        Page<Customer> page = IEmployeeCustomerService.getAllCustomers(customerProperty, 1, 5);

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

    @GetMapping("/customer/resumeUpload")
    public String uploadResume(@RequestParam("userId") int cDId, Model model) {
        UserResume resume = new UserResume();
        System.out.println("reUCID: " + cDId);
        UserResume userResume = iUserResumeService.getUserResumeByCustomeerId(cDId);
        if (userResume == null) {
            model.addAttribute("userResume", null);
            model.addAttribute("resumeId", null);
            model.addAttribute("customerIdR", cDId);
            model.addAttribute("resumeObject", resume);
            return "customer-resume";
        }

        model.addAttribute("userResume", userResume);
        model.addAttribute("resumeId", userResume.getId());
        model.addAttribute("customerIdR", cDId);
        model.addAttribute("resumeObject", resume);
        return "customer-resume";
    }


    @PostMapping("/customer/uploaded_resume")
    public String customerUploadResume(@RequestParam("muiltiPartFile") MultipartFile file,
                                       @RequestParam("userId") int cdId,
                                       @ModelAttribute("resumeObject") UserResume resume,
                                       RedirectAttributes redirectAttributes) {
        Customer customer = IEmployeeCustomerService.getCustomerById(cdId);
        String fileName = Util.fileConvertToString(file);
        System.out.println("FileNameP: " + fileName);
        System.out.println("YESCDID: " + customer.getId());


        try {
            if (!fileName.trim().isEmpty()) {
                System.out.println("File:" + file.getOriginalFilename());
                resume.setCustomer(customer);
                iUserResumeService.saveUserResume(file, resume);
                redirectAttributes.addFlashAttribute("message", "File Upload Successfully!");
                return "redirect:/dashboard";
            }
            return "customer-resume";

        } catch (IOException e) {

            e.printStackTrace();
            return "customer-resume";

        }


    }

    @GetMapping("/customer/resume-update")
    public String customerResumeUpdate(@RequestParam("userId") int cDId, Model model) {
        UserResume userResume = iUserResumeService.getUserResumeByCustomeerId(cDId);

        model.addAttribute("customerIdR", cDId);
        model.addAttribute("userResume", userResume);
        model.addAttribute("resumeObject", userResume);
        return "customer-resume-update";

    }


    @GetMapping("/resume/download")
    public void downloadResume(@Param("resumeId") int resumeId, HttpServletResponse response) throws Exception {

        UserResume userResume = iUserResumeService.getUserResumeById(resumeId);

        Util.resumeDownloader(resumeId, response, userResume);
    }

    @GetMapping("/customer/manager")
    public String getCustomerManager(@RequestParam("userId") int cId, Model model) {
        Customer customer = IEmployeeCustomerService.getCustomerById(cId);
        Employee employee = customer.getEmployee();

        model.addAttribute("manager", employee);
        model.addAttribute("user", customer);

        return "customer-manager";
    }

    @GetMapping("/customer/change-password")
    public String changePassword(@RequestParam("customerId-p") int cId, Model model) {

        model.addAttribute("customerIdP", cId);

        return "c-change-password";
    }

    @PostMapping("/customer/post-new-password")
    public String postChangePassword(@RequestParam("customerIdP") int cId,
                                     @RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("cNewPassword") String cNewPassword,
                                     RedirectAttributes r) {

        String encodedPassword = secureUserService.getPasswordByCustomerId(cId);

        boolean isPassword = Util.decodePassword(oldPassword, encodedPassword);
        System.out.println("decodedPassword: " + isPassword);

        boolean isSame = Util.compareString(newPassword, cNewPassword);

        if (!isPassword || !isSame) {

            r.addFlashAttribute("message", "password or old password is wrong!");
            return "c-change-password";
        }


        r.addFlashAttribute("message", "password changed successfully!");
        String newPasscode = Util.hashPassword(newPassword);

        User user = secureUserService.getUserByCustomerId(cId);
        System.out.println("UserName: " + user.getUserName());
        user.setPassWord(newPasscode);
        secureUserService.saveSecureUser(user);

        System.out.println(user.getPassWord());

        return "redirect:/dashboard";

    }

    @GetMapping("/customer/e-page-customer-u-medical-f")
    public String uploadMedicalResult(@RequestParam("customerId") int cId, Model model) {

        Customer customer = IEmployeeCustomerService.getCustomerById(cId);

        model.addAttribute("customer", customer);

        return "c-upload-mr";
    }


    @PostMapping("/customer/c-upload-mr")
    public String postMedicalResult(@RequestParam("customerId") int customerId,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes r) throws IOException {

        Customer customer = IEmployeeCustomerService.getCustomerById(customerId);

        MedicalResult medicalResult = new MedicalResult();

        if (Util.checkFileNameError(file) == null) {

            r.addFlashAttribute("message", "error uploading file...");

            return "c-upload-mr";
        }


        r.addFlashAttribute("message", "file upload successfully...");

        medicalResult.setCustomer(customer);
        IEmployeeCustomerService.addEmployeeCustomer(customer);
        IEmployeeCustomerService.saveUserMedicalResult(file, medicalResult);

        return "redirect:/e-page/1?employeeId=" + customer.getEmployee().getId();
    }

    @GetMapping("/customer/e-page-customer-mr-rm")
    public String deleteLatestMedicalResult(@RequestParam("customerId") int cId, RedirectAttributes r) {

        Customer customer = IEmployeeCustomerService.getCustomerById(cId);

        try{

            List<MedicalResult> medicalResults = IEmployeeCustomerService.findMedicalResultsByCustomerId(cId);


            MedicalResult medicalResult = medicalResults.get(0);

            IEmployeeCustomerService.deleteMedicalResult(medicalResult);


        }catch(Exception e){
         e.printStackTrace();

            r.addFlashAttribute("message", "Medical Result not found");
            return "redirect:/e-page/1?employeeId=" + customer.getEmployee().getId();
        }

        r.addFlashAttribute("message", "Recent Medical Result deleted..");
        return "redirect:/e-page/1?employeeId=" + customer.getEmployee().getId();

    }

    @GetMapping("/customer-mr")
    public String getMedicalResult(@RequestParam("customerId") int cId, Model model) {


        model.addAttribute("customerId", cId);

        return getMedicalResultPaginated(1, cId, model);

    }


    @GetMapping("/customer/mr-page/{pageNo}")
    public String getMedicalResultPaginated(@PathVariable("pageNo") int pageNo,
                                            @ModelAttribute("customerId") int cId,
                                            Model model) {

        int pageSize = 5;

        Page<MedicalResult> page = IEmployeeCustomerService.findAllByCustomerId(cId, pageNo, pageSize);

        model.addAttribute("cId", cId);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("mr", page.getContent());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("customerId", cId);

        return "customer-mr-list";

    }

    @GetMapping("/customer-mr/search")
    public String getCustomerMedicalResultSearched(@RequestParam("searchResult") String searchParam,
                                                   @RequestParam("customerId") int cId,
                                                   Model model, RedirectAttributes r){
        try{

            List<MedicalResult> medicalResults=
                    IEmployeeCustomerService.findMedicalResultsByNameContainingAndCustomerId(searchParam, cId);
            System.out.println("MEDCCC: "+ medicalResults.get(0).getName());

            model.addAttribute("mr", medicalResults);
            model.addAttribute("customerId", cId);

            return "customer-mr-list";

        }catch (Exception e){
            e.printStackTrace();

            r.addFlashAttribute("message", "Medical Result not found..");
            return "redirect:/customer-mr?customerId="+ cId;
        }


    }

    @GetMapping("/mr/download")
    public void downloadMedicalResult(@RequestParam("mrId") int mrId, HttpServletResponse response) throws IOException {

        MedicalResult result = IEmployeeCustomerService.findMedicalResultById(mrId);

        Util.medicalResultDownloader(mrId, response, result);

    }

    @GetMapping("/customer/checkout-mr")
    public String getHcStatus(@RequestParam("hc-serviceId") int hcID, RedirectAttributes r) {

        HcService hcService = iHcService.getHcServiceById(hcID);

        return statusService.saveMRStatus(hcService, hcID, r);
    }

    @GetMapping("/customers/search-epage")
    public String getSearchedEmployeePageCustomer(@RequestParam("searchResult") String searchResult,
                                                  @RequestParam("employeeId") int eId, Model model){

        List<Customer> customers= IEmployeeCustomerService.searchEmployeeCustomer(searchResult, eId);


        model.addAttribute("employeeId", eId);
        model.addAttribute("employeeCustomers", customers);


        return     "e-page-customer-list";
    }


}
