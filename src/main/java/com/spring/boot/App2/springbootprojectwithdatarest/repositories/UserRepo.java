package com.spring.boot.App2.springbootprojectwithdatarest.repositories;

import com.spring.boot.App2.springbootprojectwithdatarest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u where u.userName= ?1")
    User getUser(String username);

    User findByUserName(String username);
}
