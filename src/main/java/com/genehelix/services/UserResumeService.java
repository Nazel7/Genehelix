package com.genehelix.services;

import com.genehelix.entities.UserResume;
import com.genehelix.interfaces.IUser;
import com.genehelix.interfaces.IUserResumeService;
import com.genehelix.repositories.UserResumeRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;


@Service
public class UserResumeService implements IUserResumeService {
    @Autowired
    UserResumeRepo resumeRepo;


    @Override
    public void saveUserResume(MultipartFile file, UserResume resume) throws IOException {
        String fileName= StringUtils.cleanPath((Objects.requireNonNull(file.getOriginalFilename())));
        System.out.println(fileName);
        if(fileName.trim().isEmpty()){
            System.out.println("not acceptable file format.");
            return;
        }

        resume.setResume(Util.formatFile(file));
        resumeRepo.save(resume);
    }

    @Override
    public void saveExistingUserResume(MultipartFile file, UserResume resume) {
        resume.setResume(Util.formatFile(file));
        resumeRepo.save(resume);
    }

}
