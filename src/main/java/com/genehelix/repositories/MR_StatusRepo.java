package com.genehelix.repositories;

import com.genehelix.entities.MedicalResultStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MR_StatusRepo extends JpaRepository<MedicalResultStatus, Integer> {


}
