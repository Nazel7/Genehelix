package com.genehelix.interfaces;

import com.genehelix.entities.UserResume;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserResumeService {

    void saveUserResume(MultipartFile file, UserResume userResume) throws IOException;

    void updateUserResume(MultipartFile file, UserResume resume);

    UserResume getUserResumeByCustomeerId(int cDId);

    UserResume getUserResumeById(int userResumeId);

}
