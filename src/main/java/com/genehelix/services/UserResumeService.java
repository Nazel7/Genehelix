package com.genehelix.services;

import com.genehelix.entities.UserResume;
import com.genehelix.interfaces.IUserResumeService;
import com.genehelix.repositories.UserResumeRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Date;
import java.util.Optional;


@Service
public class UserResumeService implements IUserResumeService {
    @Autowired
    UserResumeRepo resumeRepo;


    @Override
    public void saveUserResume(MultipartFile file, UserResume resume) throws IOException {
        String fileName= Util.fileConvertToString(file);
        System.out.println(fileName);
        if(fileName.trim().isEmpty() || fileName.trim().contains(",") || fileName.trim().contains("..")){
            throw new NoSuchFileException("Not acceptable file format. file contains any of unacceptable char in filename." +
                    "Please edit filename");
        }

        resume.setResume(Util.formatFileTOByteArray(file));
        resume.setResumeName(fileName);
        resume.setResumeSize(file.getSize());
        resume.setDate(new Date());
        resumeRepo.save(resume);
    }

    @Override
    public void updateUserResume(MultipartFile file, UserResume resume) {
        resume.setResume(Util.formatFileTOByteArray(file));
        resumeRepo.save(resume);
    }

    @Override
    public UserResume getUserResumeByCustomeerId(int cDId) {

        return resumeRepo.getUserResumeByCustomerId(cDId);
    }

    @Override
    public UserResume getUserResumeById(int userResumeId) {
      Optional<UserResume> userResume = resumeRepo.findById(userResumeId);
        return userResume.orElse(null);
    }

    @Override
    public UserResume getUserResumeByEmployeeId(int eId) {

        return resumeRepo.getUserResumeByEmployeeId(eId);
    }

}
