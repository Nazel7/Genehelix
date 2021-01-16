package com.genehelix.repositories;

import com.genehelix.entities.UserResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserResumeRepo extends JpaRepository<UserResume, Integer> {

    @Query("SELECT r FROM UserResume r INNER JOIN customer c " +
            "on r.customer.id=c.id WHERE c.id=?1")
    UserResume getUserResumeByCustomerId(int cDId);


}
