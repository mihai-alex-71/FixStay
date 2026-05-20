package com.fixStay.backend.controller;

import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.Status;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.fixStay.backend.model.Issue;
import com.fixStay.backend.model.IssueStatus;
import com.fixStay.backend.repository.IssueRepository;

@RestController
//@RequestMapping("/admin") s1 change
@RequestMapping("/api/admin")
@CrossOrigin

public class AdminController {

    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public AdminController(UserRepository userRepository, IssueRepository issueRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    // STEP 1: get all pending service providers
    @GetMapping("/providers/pending")
    public List<User> getPendingProviders() {
        return userRepository.findByRoleAndStatus(
                Role.SERVICE_PROVIDER,
                Status.PENDING
        );
    }

    //s1
    @PostMapping("/providers/{id}/approve")
    public String approveProvider(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();

        user.setStatus(Status.APPROVED);
        userRepository.save(user);

        return "Provider approved successfully";
    }

    @PostMapping("/providers/{id}/reject")
    public String rejectProvider(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();

        user.setStatus(Status.REJECTED);
        userRepository.save(user);

        return "Provider rejected successfully";
    }

    @GetMapping("/providers/verified")
    public List<User> getVerifiedProviders() {
        return userRepository.findByRoleAndStatus(Role.SERVICE_PROVIDER, Status.APPROVED);
    }

    @GetMapping("/tasks/active")
    public List<Issue> getActiveTasks() {
        List<Issue> active = issueRepository.findByStatus(IssueStatus.OPEN);
        active.addAll(issueRepository.findByStatus(IssueStatus.IN_PROGRESS));
        return active;
    }
}