package com.genehelix.interfaces;

import com.genehelix.entities.HcService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public interface IMR_StatusService {

    String saveMRStatus(HcService hcService, int hcID, RedirectAttributes r);
}
