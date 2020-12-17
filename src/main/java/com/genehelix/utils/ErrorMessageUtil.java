package com.genehelix.utils;

import org.springframework.ui.Model;

import java.util.List;

public class ErrorMessageUtil {

    public static String errorMessage(List<?> list,
                               String message,
                               String returnText1,
                               String returnText2,
                               Model model,
                               String modelAtt1,
                               String modelAtrr2) {
        if (list.isEmpty()) {
            System.out.println("methodMessage: " + message);
            model.addAttribute(modelAtt1, message);
            return returnText1;
        } else {
            model.addAttribute(modelAtrr2, list);
            return returnText2;
        }
    }
}
