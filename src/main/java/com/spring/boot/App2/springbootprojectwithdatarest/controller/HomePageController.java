package com.spring.boot.App2.springbootprojectwithdatarest.controller;

import com.spring.boot.App2.springbootprojectwithdatarest.appServiceDAO.EmployeeServiceDAO;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Customer;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Employee;
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
public class HomePageController {

    @Autowired
    private EmployeeServiceDAO employeeServiceDAO;
    private List<String> reviewList;

    public HomePageController() {
        this.reviewList= new ArrayList<>();
    }

    @InitBinder
    public void dataTrimmer(WebDataBinder dataBinder){
        StringTrimmerEditor sTrimmer= new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, sTrimmer);
    }

    @GetMapping("/")
    public String homePage2(){

        return "homePage";
    }

    @GetMapping("/home-page")
    public String homePage(){

        return "homePage";
    }

    @GetMapping("/home-page/customer-reg")
    public String customerReg( Model model){
        Customer customer= new Customer();
        model.addAttribute("homeRegCustomer", customer);

        return "home-reg-customer";
    }

    @PostMapping("/home-page/reg-customer")
    public String postHomeRegCustomer(@Valid @ModelAttribute("homeRegCustomer") Customer customer,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "home-reg-customer";
        }else {
            if(customer != null){
                employeeServiceDAO.addEmployeeCustomer(customer);

            }
            return "redirect:/home-page";
        }

    }
    @GetMapping("/home-search")
    public String searchEmployee(@RequestParam("searchEmployeesHome") String employeeProperty,
                                 Model model){
        model.addAttribute("entityProperty", employeeProperty);
        return searchEmployeePaginatedPage(1,employeeProperty, model);
    }

    @GetMapping("/hpage/{pageNo}")
    public String searchEmployeePaginatedPage(@PathVariable("pageNo") int pageNo,
                                              @ModelAttribute("entityProperty") String entityName, Model model){

        int pageSize= 5;
        Page<Employee> page= employeeServiceDAO.getSearchPaginatedEmployeeHome(entityName, pageNo, pageSize);
        List<Employee> employees= page.getContent();
        if (employees.isEmpty()){
            String emptyEmployee= "There is no employee found.....";
            System.out.println("REVIEWMESSAGE: "+emptyEmployee);
            model.addAttribute("emptyEmployee", emptyEmployee);
            return "empty-employee";
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("employeeList", page.getContent());
        model.addAttribute("totalPage",page.getTotalPages());


        return "employee-homesearch-list";
    }
    @GetMapping("/showEmployeeReviewHome")
    public String searchEmployeeReviewList(@RequestParam("showReviewsHome") int employeeID, Model model){
        System.out.println("wpep=" + employeeID);

      model.addAttribute("employeeSearchId", employeeID);
        reviewList = employeeServiceDAO.showReviews(employeeID);

        AppController controller= new AppController();

       return controller.errorMessage(reviewList,
                "There is no review found.....",
                "empty-review-home",
                "home-review-list", model,
                "emptyReview",
                "reviews"
        );

    }

    @GetMapping("/home-page/logon-customer")
    public String homeLogonCustomer(Model model){

        return homeCustomerPaginated(1, model);
    }
  @GetMapping("/homecustomer-page/{pageNo}")
    public String homeCustomerPaginated(@PathVariable("pageNo") int pageNo, Model model){
        int pageSize= 5;
        Page<Customer> page= employeeServiceDAO.getAllCustomers(pageNo, pageSize);

      model.addAttribute("currentPage", pageNo);
      model.addAttribute("totalItems", page.getTotalElements());
      model.addAttribute("homeCustomers", page.getContent());
      model.addAttribute("totalPage",page.getTotalPages());

        return "home-logon-customer";
  }

  @GetMapping("/home-customers/search")
    public String logonSearchCustomer( @RequestParam("searchHomeLogonCustomer") String customerProperty, Model model){

        Page<Customer> page= employeeServiceDAO.getAllCustomers(customerProperty, 1, 5);

       List<Customer> customers= page.getContent();
       if (customers.isEmpty()){
           String emptyCustomer= "There is no customer found.....";
           System.out.println("REVIEWMESSAGE: "+emptyCustomer);
           model.addAttribute("emptyCustomer", emptyCustomer);
           return "empty-customer";
       }
       model.addAttribute("searchHomeCustomers", customerProperty);
      model.addAttribute("currentPage", 1);
      model.addAttribute("totalItems", page.getTotalElements());
      model.addAttribute("homeCustomers", page.getContent());
      model.addAttribute("totalPage",page.getTotalPages());

        return "home-logon-customer";
  }

  @GetMapping("/logout")
    public String logout(Model model){
        String logout= "You have been logged out.";
        model.addAttribute("logoutMessage", logout);

        return "redirect:/home-page";
  }

  @GetMapping("/home-page/logonCustomer")
    public String customerPage(@RequestParam("homeCustomerUpdateID") int customerId, Model model){
    Customer customer=  employeeServiceDAO.getCustomerById(customerId);
    model.addAttribute("customer", customer);
    return "customer-page";
  }
   
}
