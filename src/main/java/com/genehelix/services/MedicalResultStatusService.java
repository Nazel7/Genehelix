package com.genehelix.services;

import com.genehelix.entities.HcService;
import com.genehelix.entities.MedicalResultStatus;
import com.genehelix.interfaces.IMR_StatusService;
import com.genehelix.repositories.MR_StatusRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class MedicalResultStatusService implements IMR_StatusService {

    @Autowired
    private MR_StatusRepo mr_statusRepo;

    @Override
    public String saveMRStatus(HcService hcService, int hcID, RedirectAttributes r) {

        MedicalResultStatus status= hcService.getMedicalResult_status();

        if (status == null){

            MedicalResultStatus medicalResult_status = new MedicalResultStatus();

            MedicalResultStatus medicalResult_status1 = Util.setMR_status(medicalResult_status);

            medicalResult_status1.setHcService(hcService);

            mr_statusRepo.save(medicalResult_status1);

            r.addFlashAttribute("message", "status updated!" );

        }else {
            r.addFlashAttribute("message", "Result previously received by you!" );
        }


        return "redirect:/dashboard";


    }








}
