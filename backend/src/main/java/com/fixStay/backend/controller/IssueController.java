package com.fixStay.backend.controller;

import com.fixStay.backend.dto.IssueRequest;
import com.fixStay.backend.model.Issue;
import com.fixStay.backend.model.IssueStatus;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.IssueRepository;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public IssueController(IssueRepository issueRepository, UserRepository userRepository, PropertyRepository propertyRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    // GUEST: Creeaza o noua problema
    @PostMapping("/report")
    public String reportIssue(@RequestBody IssueRequest request) {
        User guest = userRepository.findUserByEmailAddress(request.getGuestEmail())
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setStatus(IssueStatus.PENDING);
        newIssue.setGuest(guest);
        newIssue.setProperty(property);

        issueRepository.save(newIssue);
        return "Issue reported successfully!";
    }

    // HOST: Vede toate problemele de la proprietatile lui
    @GetMapping("/host-issues")
    public List<Issue> getHostIssues(@RequestParam String email) {
        return issueRepository.findByProperty_Host_EmailAddress(email);
    }

    @GetMapping("/my-reports")
    public List<Issue> getMyReports(@RequestParam String email) {
        return issueRepository.findByGuest_EmailAddress(email);
    }
}