package com.genehelix.controllers.views;


import com.genehelix.entities.Employee;
import com.genehelix.entities.EmployeeProfilePhoto;
import com.genehelix.entities.User;
import com.genehelix.entities.UserResume;
import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.interfaces.ISecureUserService;
import com.genehelix.interfaces.IUserProfilePhotoService;
import com.genehelix.interfaces.IUserResumeService;
import com.genehelix.utils.EmployeeUtil;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/company-employees")
public class EmployeeController {

    @Autowired
    private IEmployeeCustomerService IEmployeeCustomerService;

    @Autowired
    private ISecureUserService secureUserService;

    @Autowired
    private IUserProfilePhotoService profilePhotoService;

    @Autowired
    private IUserResumeService iUserResumeService;

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
    @GetMapping("/employee/photoUpload")
    public String gotoEmployeeProfilePhotoPage(@RequestParam("e-userId") int eId, Model model) {
        EmployeeProfilePhoto profilePhoto= new EmployeeProfilePhoto();
        EmployeeProfilePhoto employeeProfilePhoto = profilePhotoService.getEmployeeProfilePhotoByEmployeeId(eId);
        if (employeeProfilePhoto == null) {
            model.addAttribute("userPhoto", null);
            model.addAttribute("PhotoId", null);

        }else {
            model.addAttribute("userPhoto", employeeProfilePhoto);
            model.addAttribute("PhotoId", employeeProfilePhoto.getId());
        }
        model.addAttribute("employeeIdR", eId);
        model.addAttribute("photoObject", profilePhoto);

        return "employee-photo";
    }


    @GetMapping("/employee/e-photo-update")
    public String updateEmployeePhoto(@RequestParam("userId") int eId, Model model){

       EmployeeProfilePhoto employeeProfilePhoto= profilePhotoService.getEmployeeProfilePhotoByEmployeeId(eId);

        model.addAttribute("photoObject", employeeProfilePhoto);
        model.addAttribute("employeeIdR", eId);
        model.addAttribute("userPhoto", employeeProfilePhoto);

        return "employee-photo-update";

    }

    @PostMapping("/employee/e-uploaded_photo")
    public String postCustomerPhoto(@RequestParam("muiltiPartFile") MultipartFile file,
                                    @RequestParam("userId") int eId,
                                    @ModelAttribute("photoObject") EmployeeProfilePhoto employeeProfilePhoto,
                                    RedirectAttributes r){
        String fileName= Util.fileConvertToString(file);
        Employee employee= IEmployeeCustomerService.getEmployee(eId);
        try{
            if (!fileName.trim().isEmpty()){
                profilePhotoService.saveEmployeePorfilePhoto(file, employeeProfilePhoto, employee);
                r.addFlashAttribute("message", "Photo Upload Successfully!");

                return "redirect:/dashboard";
            }
            return "employee-photo";
        }catch (Exception e){
            e.printStackTrace();

            return "employee-photo";
        }


    }

// Employee Resume setUp.
    @GetMapping("/employee/resumeUpload")
    public String uploadResume(@RequestParam("userId") int eId, Model model) {
        UserResume resume = new UserResume();
        UserResume userResume = iUserResumeService.getUserResumeByEmployeeId(eId);
        if (userResume == null) {
            model.addAttribute("userResume", null);
            model.addAttribute("resumeId", null);

        }else{

            model.addAttribute("userResume", userResume);
            model.addAttribute("resumeId", userResume.getId());
        }

        model.addAttribute("employeeIdR", eId);
        model.addAttribute("resumeObject", resume);

        return "employee-resume";
    }


    @PostMapping("/employee/uploaded_resume")
    public String employeeUploadResume(@RequestParam("muiltiPartFile") MultipartFile file,
                                       @RequestParam("userId") int eId,
                                       @ModelAttribute("resumeObject") UserResume resume,
                                       RedirectAttributes redirectAttributes) {
        Employee employee = IEmployeeCustomerService.getEmployee(eId);
        String fileName = Util.fileConvertToString(file);

        try {
            if (!fileName.trim().isEmpty()) {
                System.out.println("File:" + file.getOriginalFilename());
                resume.setEmployee(employee);
                iUserResumeService.saveUserResume(file, resume);
                redirectAttributes.addFlashAttribute("message", "File Upload Successfully!");
                return "redirect:/dashboard";
            }
            return "employee-resume";

        } catch (IOException e) {

            e.printStackTrace();
            return "employee-resume";

        }


    }

    @GetMapping("/employee/resume-update")
    public String employeeResumeUpdate(@RequestParam("userId") int eId, Model model) {
        UserResume userResume = iUserResumeService.getUserResumeByEmployeeId(eId);

        model.addAttribute("employeeIdR", eId);
        model.addAttribute("userResume", userResume);
        model.addAttribute("resumeObject", userResume);

        return "employee-resume-update";

    }


    @GetMapping("/resume/download")
    public void downloadResume(@Param("resumeId") int resumeId, HttpServletResponse response) throws Exception {

        UserResume userResume = iUserResumeService.getUserResumeById(resumeId);
        Util.resumeDownloader(resumeId, response, userResume);
    }



    @GetMapping("/employee/change-password")
    public String changePassword(@RequestParam("employeeId-p") int eId, Model model){

        model.addAttribute("employeeIdP" , eId);

        return "e-change-password";
    }

    @PostMapping("/employee/post-new-password")
    public String postChangePassword(@RequestParam("employeeIdP") int eId,
                                     @RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("eNewPassword") String eNewPassword,
                                     RedirectAttributes r){

        String encodedPassword= secureUserService.getPasswordByEmployeeId(eId);

        boolean isPassword= Util.decodePassword(oldPassword, encodedPassword);

        boolean isSame= Util.compareString(newPassword, eNewPassword);

        if (!isPassword || !isSame){

            r.addFlashAttribute("message", "password or old password is wrong!");

            return "redirect:/company-employees/employee/change-password?employeeId-p="+ eId;
        }


        r.addFlashAttribute("message", "password changed successfully!");
        String newPasscode= Util.hashPassword(newPassword);

        User user= secureUserService.getUserByEmployeeId(eId);
        user.setPassWord(newPasscode);
        secureUserService.saveSecureUser(user);

        return "redirect:/dashboard";

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
