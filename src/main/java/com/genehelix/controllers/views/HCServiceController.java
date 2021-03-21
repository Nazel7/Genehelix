package com.genehelix.controllers.views;

import com.genehelix.entities.HcService;
import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.interfaces.IHcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HCServiceController {
    @Autowired
    IHcService iHcService;

    @Autowired
    private IEmployeeCustomerService iEmployeeCustomerService;

    @GetMapping("/hcsevice-list/customer")
    public String getHcServiceListForCustomer(@RequestParam("userId") int cId, Model model, RedirectAttributes r){

       List<HcService> hcServices= iHcService.getHCServiceListByCustomerId(cId);

       if (hcServices == null){
           r.addFlashAttribute("message", "You have no service available");
           return "redirect:/dashboard";
       }
       model.addAttribute("customerId", cId);
       model.addAttribute("hcServices", hcServices);

       return "hc-service-page";
    }


    @GetMapping("/hcsevice-list/employee")
    public String getHcServiceListForEmployee(@RequestParam("userId") int eId, Model model, RedirectAttributes r){

        List<HcService> hcServices= iHcService.getHCServiceListByEmployeeId(eId);

        if (hcServices == null){
              r.addFlashAttribute("message", "You have no service available");
            return "redirect:/dashboard";
        }
        System.out.println("IDD: "+ eId);
        model.addAttribute("employeeId", eId);
        model.addAttribute("userEmployee", iEmployeeCustomerService.getEmployee(eId));
        model.addAttribute("hcServices", hcServices);

        return "hc-service-page";
    }

    @GetMapping("/hc-search/employee")
    public String getEmployeeHcServiceSearched(@RequestParam("searchParam") String searchResult,
                                               @RequestParam("employeeId") int eId,
                                               Model model){

        List<HcService> hcServices= iHcService.getHcServicesByNameContainingAndEmployeehId(searchResult, eId);
        System.out.println("searchHc: "+ hcServices.get(0).getId());

        model.addAttribute("employeeId", eId);
        model.addAttribute("hcServices", hcServices);
        model.addAttribute("userEmployee", iEmployeeCustomerService.getEmployee(eId));

        return "hc-service-page";
    }
}
