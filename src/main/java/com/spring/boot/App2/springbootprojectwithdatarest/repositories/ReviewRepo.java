package com.spring.boot.App2.springbootprojectwithdatarest.repositories;

import com.spring.boot.App2.springbootprojectwithdatarest.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {


}
