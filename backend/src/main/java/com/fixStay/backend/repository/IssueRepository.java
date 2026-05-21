package com.fixStay.backend.repository;

import com.fixStay.backend.model.Issue;
import com.fixStay.backend.model.IssueStatus;
import com.fixStay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    // Pentru a gasi problemele raportate de un anumit Guest
    List<Issue> findByGuest_EmailAddress(String email);

    // Pentru ca Host-ul sa vada problemele de la toate proprietatile lui
    List<Issue> findByProperty_Host_EmailAddress(String email);

    // Pentru ca Provider-ul sa vada joburile disponibile (status = OPEN)
    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByProviderEmailAddress(String serviceProviderEmailAddress);

    @Query("SELECT AVG(i.rating) FROM Issue i WHERE i.provider = :provider AND i.rating IS NOT NULL")
    Double findAverageRatingByProvider(@Param("provider") User provider);

    long countByProviderAndStatus(User provider, IssueStatus status);
}