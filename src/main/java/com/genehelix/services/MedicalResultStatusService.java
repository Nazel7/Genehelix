package com.genehelix.services;

import com.genehelix.entities.HcService;
import com.genehelix.entities.MedicalResultStatus;
import com.genehelix.interfaces.IMedicalResultStatusService;
import com.genehelix.repositories.MR_StatusRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class MedicalResultStatusService implements IMedicalResultStatusService {

    @Autowired
    private MR_StatusRepo mr_statusRepo;

    @Override
    public String saveMRStatus(HcService hcService, int hcID, RedirectAttributes r) {

        MedicalResultStatus status= hcService.getMedicalResult_status();

        if (status.getStatus().equals("NR")){

            MedicalResultStatus medicalResult_status1 = Util.setMR_status(status);

            mr_statusRepo.save(medicalResult_status1);

            r.addFlashAttribute("message", "status updated!" );

        }else {

            r.addFlashAttribute("message", "Result previously received by you!" );
        }


        return "redirect:/dashboard";


    }

    @Override
    public void saveMedicalResultStatus(MedicalResultStatus medicalResultStatus) {

        mr_statusRepo.save(medicalResultStatus);
    }


}
