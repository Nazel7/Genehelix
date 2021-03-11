package com.genehelix.controllers.views;

import com.genehelix.entities.HcService;
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

    @GetMapping("/hcsevice-list/customer")
    public String getHcServiceListForCustomer(@RequestParam("userId") int cId, Model model){

       List<HcService> hcServices= iHcService.getHCServiceListByCustomerId(cId);

       if (hcServices == null){

           return "redirect:/dashboard";
       }
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
        model.addAttribute("hcServices", hcServices);

        return "hc-service-page";
    }
}
