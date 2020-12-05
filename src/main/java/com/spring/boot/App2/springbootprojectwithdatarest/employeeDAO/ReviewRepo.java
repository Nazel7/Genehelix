package com.spring.boot.App2.springbootprojectwithdatarest.employeeDAO;

import com.spring.boot.App2.springbootprojectwithdatarest.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer>{


}
