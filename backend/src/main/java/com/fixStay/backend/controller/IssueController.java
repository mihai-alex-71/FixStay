package com.fixStay.backend.controller;

import com.fixStay.backend.dto.IssueRequest;
import com.fixStay.backend.model.Issue;
import com.fixStay.backend.service.IssueService;
import org.springframework.web.bind.annotation.*;

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
    public String reportIssue(@RequestBody IssueRequest issueRequest){
        return  issueService.reportIssue(issueRequest);
    }

    @GetMapping("/host-issues")
    public List<Issue> getIssueForHost(@RequestParam String email){
        return  issueService.getIssueForHost(email);
    }

    @GetMapping("/my-reports")
    public List<Issue> getGuestReports(@RequestParam String email){
        return issueService.getGuestReports(email);
    }

    @PostMapping("/publish/{id}")
    public String publishIssue(@PathVariable Long id, @RequestParam String email){
        return issueService.publishIssue(id, email);
    }

    @GetMapping("/issue-available")
    public List<Issue> getAvailableTasks(){
        return issueService.getAvailableTasks();
    }

    @PostMapping("/service-apply/{id}")
    public String assignIssueToServiceProvider(@PathVariable Long id, @RequestParam String email){
        return issueService.assignIssueToServiceProvider(id, email);
    }

    //provider active job
    @GetMapping("service-provider/jobs")
    public List<Issue> showActiveJobs(@RequestParam String email){
        return  issueService.getIssueByServiceProvider(email);
    }

    @GetMapping("/all")
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @PostMapping("/{id}/rate")
    public String rateIssue(@PathVariable Long id,
                            @RequestParam String hostEmail,
                            @RequestParam int rating){
        return issueService.rateIssue(id, hostEmail, rating);
    }

    @PostMapping("/{id}/resolve")
    public String resolveIssues(@PathVariable Long id,
                                @RequestParam String providerEmail){
        return  issueService.resolveIssues(id,providerEmail);
    }

    @GetMapping("/provider-rating")
    public Double getProviderRating(@RequestParam String providerEmail){
        return issueService.getProviderRating(providerEmail);
    }

}