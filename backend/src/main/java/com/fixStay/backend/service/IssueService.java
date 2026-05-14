package com.fixStay.backend.service;

import com.fixStay.backend.dto.IssueRequest;
import com.fixStay.backend.model.*;
import com.fixStay.backend.repository.IssueRepository;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;


    public IssueService(IssueRepository issueRepository, UserRepository userRepository, PropertyRepository propertyRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    public String reportIssue(IssueRequest request) {
        User user = userRepository.findUserByEmailAddress(request.getGuestEmail())
                .orElseThrow(() -> new RuntimeException("user not found"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());

        String role;

        if (user.getRole() == Role.GUEST) {
            newIssue.setStatus(IssueStatus.PENDING);
        } else {
            newIssue.setStatus(IssueStatus.OPEN);
        }
        newIssue.setGuest(user);
        newIssue.setProperty(property);

        issueRepository.save(newIssue);
        return "Issue reported successfully!";
    }

    public List<Issue> getIssueForHost(String email) {
        return issueRepository.findByProperty_Host_EmailAddress(email);
    }

    public List<Issue> getGuestReports(String email) {
        return issueRepository.findByGuest_EmailAddress(email);
    }


    // posting the issue for Service providers :

    public String publishIssue(Long issueId, String hostEmail) {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);

        if (issueOptional.isPresent()) {
            Issue issue = issueOptional.get();

            String actualOwnerEmail = issue.getProperty().getHost().getEmailAddress();

            if (!hostEmail.equals(actualOwnerEmail)) {
                return "Security Alert: You do not have permission to publish issues for this property.";
            }
            issue.setStatus(IssueStatus.OPEN);

            issueRepository.save(issue);

            return " your issue now is public, waiting for service providers to find it out.";
        }
        return "Issue not found";
    }


    // retrieving open issues :
    public List<Issue> getAvailableTasks() {
        return issueRepository.findByStatus(IssueStatus.OPEN);
    }

    public String assignIssueToServiceProvider(Long issueID, String providerEmail) {
        Optional<Issue> issueOptional = issueRepository.findById(issueID);

        if (issueOptional.isPresent()) {
            Issue issue = issueOptional.get();

            if (issue.getStatus() != IssueStatus.OPEN) {
                return "This task is already assigned to another service provider or is still in host approval.";
            }
            Optional<User> optionalServiceProvider = userRepository.findUserByEmailAddress(providerEmail);
            if (optionalServiceProvider.isPresent()) {
                User serviceProvider = optionalServiceProvider.get();
                if (serviceProvider.getRole() != Role.SERVICE_PROVIDER) {
                    return "cannot assign a role to a "+ serviceProvider.getRole()+" user";
                }
                issue.setProvider(serviceProvider);
                issue.setStatus(IssueStatus.IN_PROGRESS);
                issueRepository.save(issue);
                return "Success! You have claimed this task.";
            }
            return "provider not found";
        }
        return "issue not found";
    }

    public List<Issue> getIssueByServiceProvider(String email){
        return issueRepository.findByProviderEmailAddress(email);
    }
}
