package com.genehelix.controllers.views;

import com.genehelix.entities.HcService;
import com.genehelix.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HCServiceController {
    @Autowired
    IService iService;

    @GetMapping("/hcsevice-list/customer")
    public String getHcServiceListForCustomer(@RequestParam("userId") int cId, Model model){

       List<HcService> hcServices= iService.getHCServiceListByCustomerId(cId);

       if (hcServices == null){

           return "redirect:/dashboard";
       }
       model.addAttribute("hcServices", hcServices);

       return "hc-service-page";
    }

}
