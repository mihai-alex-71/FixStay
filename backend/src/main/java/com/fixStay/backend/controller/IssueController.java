package com.fixStay.backend.controller;

import com.fixStay.backend.dto.IssueRequest;
import com.fixStay.backend.dto.VerifyIssueRequest;
import com.fixStay.backend.model.Issue;
import com.fixStay.backend.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/report")
    public String reportIssue(@RequestBody IssueRequest issueRequest) {
        return issueService.reportIssue(issueRequest);
    }

    @GetMapping("/host-issues")
    public List<Issue> getIssueForHost(@RequestParam String email) {
        return issueService.getIssueForHost(email);
    }

    @GetMapping("/my-reports")
    public List<Issue> getGuestReports(@RequestParam String email) {
        return issueService.getGuestReports(email);
    }

    @PostMapping("/publish/{id}")
    public String publishIssue(@PathVariable Long id,
                               @RequestParam String email) {
        return issueService.publishIssue(id, email);
    }

    @GetMapping("/issue-available")
    public List<Issue> getAvailableTasks() {
        return issueService.getAvailableTasks();
    }

    @PostMapping("/service-apply/{id}")
    public String assignIssueToServiceProvider(@PathVariable Long id,
                                               @RequestParam String email) {
        return issueService.assignIssueToServiceProvider(id, email);
    }

    // Provider active jobs
    @GetMapping("/service-provider/jobs")
    public List<Issue> showActiveJobs(@RequestParam String email) {
        return issueService.getIssueByServiceProvider(email);
    }

    //s3
    // Provider completes task + uploads proof
    @PostMapping("/complete/{id}")
    public String completeTask(
            @PathVariable Long id,
            @RequestParam String email,
            @RequestParam("proofImage") MultipartFile proofImage,
            @RequestParam(required = false) String notes
    ) {
        System.out.println("COMPLETE ENDPOINT HIT");

        try {
            String result = issueService.completeIssue(id, email, proofImage, notes);
            System.out.println("RESULT: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    @PostMapping("/verify/{issueId}")
    public ResponseEntity<String> verifyIssue(
            @PathVariable Long issueId,
            @RequestBody VerifyIssueRequest request
    ) {
        return ResponseEntity.ok(
                issueService.verifyIssue(
                        issueId,
                        request.getRating(),
                        request.getReview()
                )
        );
    }
}