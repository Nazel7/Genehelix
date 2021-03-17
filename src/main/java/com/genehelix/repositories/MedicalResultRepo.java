package com.genehelix.repositories;

import com.genehelix.entities.MedicalResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalResultRepo extends JpaRepository<MedicalResult, Integer> {

    Page<MedicalResult> findAllByCustomerId(int cId, Pageable pageable);

     @Query("SELECT mr FROM MedicalResult mr where  customer.id= ?1 order by mr.id desc")
    List<MedicalResult> findMedicalResultsByCustomerIdOrderByMedicalResultIdDesc(int cId);

     MedicalResult findById(int mrId);



}

