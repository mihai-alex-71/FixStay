package com.fixStay.backend.controller;

import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.Status;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/admin") s1 change
@RequestMapping("/api/admin")
@CrossOrigin

public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}