package com.genehelix.interfaces;

import com.genehelix.entities.HcService;
import com.genehelix.entities.User;

import java.util.List;

public interface IUserDetail {

     int getId();

    String getTwitter();

    String getLinkedin();

    String getHomeAddress();

    String getMobile();

    String getOccupation();

    List<HcService> getService();

    String getDateTime();


}
