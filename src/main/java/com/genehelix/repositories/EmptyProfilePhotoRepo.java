package com.genehelix.repositories;

import com.genehelix.entities.EmptyProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmptyProfilePhotoRepo extends JpaRepository<EmptyProfilePhoto, Integer> {


    EmptyProfilePhoto getEmptyProfilePhotoById(int emtyId);


}
