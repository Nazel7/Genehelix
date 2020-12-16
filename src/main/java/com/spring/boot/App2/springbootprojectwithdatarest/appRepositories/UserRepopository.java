package com.spring.boot.App2.springbootprojectwithdatarest.appRepositories;

import com.spring.boot.App2.springbootprojectwithdatarest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepopository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u where u.userName= ?1")
    User getUser(String username);

    User findByUserName(String username);
}
