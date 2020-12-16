package com.spring.boot.App2.springbootprojectwithdatarest.appRepositories;

import com.spring.boot.App2.springbootprojectwithdatarest.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {


}
