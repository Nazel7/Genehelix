package com.genehelix.interfaces;

import com.genehelix.entities.HcService;
import com.genehelix.entities.MedicalResultStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public interface IMedicalResultStatusService {

    String saveMRStatus(HcService hcService, int hcID, RedirectAttributes r);

    void saveMedicalResultStatus(MedicalResultStatus medicalResultStatus);
}
