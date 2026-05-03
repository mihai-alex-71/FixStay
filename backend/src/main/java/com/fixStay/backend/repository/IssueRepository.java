package com.fixStay.backend.repository;

import com.fixStay.backend.model.Issue;
import com.fixStay.backend.model.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
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
}