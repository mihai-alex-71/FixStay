//package com.fixStay.backend.service;
//
//import com.fixStay.backend.dto.IssueRequest;
//import com.fixStay.backend.model.*;
//import com.fixStay.backend.repository.IssueRepository;
//import com.fixStay.backend.repository.PropertyRepository;
//import com.fixStay.backend.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;//s3
//
//import java.util.List;
//import java.util.Optional;
////s3
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//public class IssueService {
//
//    private final IssueRepository issueRepository;
//    private final UserRepository userRepository;
//    private final PropertyRepository propertyRepository;
//
//    public IssueService(IssueRepository issueRepository,
//                        UserRepository userRepository,
//                        PropertyRepository propertyRepository) {
//        this.issueRepository = issueRepository;
//        this.userRepository = userRepository;
//        this.propertyRepository = propertyRepository;
//    }
//
//    public String reportIssue(IssueRequest request) {
//        User user = userRepository.findUserByEmailAddress(request.getGuestEmail())
//                .orElseThrow(() -> new RuntimeException("user not found"));
//
//        Property property = propertyRepository.findById(request.getPropertyId())
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//
//        Issue newIssue = new Issue();
//        newIssue.setTitle(request.getTitle());
//        newIssue.setDescription(request.getDescription());
//
//        if (user.getRole() == Role.GUEST) {
//            newIssue.setStatus(IssueStatus.PENDING);
//        } else {
//            newIssue.setStatus(IssueStatus.OPEN);
//        }
//
//        newIssue.setGuest(user);
//        newIssue.setProperty(property);
//
//        issueRepository.save(newIssue);
//        return "Issue reported successfully!";
//    }
//
//    public List<Issue> getIssueForHost(String email) {
//        return issueRepository.findByProperty_Host_EmailAddress(email);
//    }
//
//    public List<Issue> getGuestReports(String email) {
//        return issueRepository.findByGuest_EmailAddress(email);
//    }
//
//    // posting the issue for Service providers :
//
//    public String publishIssue(Long issueId, String hostEmail) {
//        Optional<Issue> issueOptional = issueRepository.findById(issueId);
//
//        if (issueOptional.isPresent()) {
//            Issue issue = issueOptional.get();
//
//            String actualOwnerEmail = issue.getProperty().getHost().getEmailAddress();
//
//            if (!hostEmail.equals(actualOwnerEmail)) {
//                return "Security Alert: You do not have permission to publish issues for this property.";
//            }
//            issue.setStatus(IssueStatus.OPEN);
//
//            issueRepository.save(issue);
//
//            return " your issue now is public, waiting for service providers to find it out.";
//        }
//        return "Issue not found";
//    }
//
//
//    // retrieving open issues :
//    public List<Issue> getAvailableTasks() {
//        return issueRepository.findByStatus(IssueStatus.OPEN);
//    }
//
//    public String assignIssueToServiceProvider(Long issueID, String providerEmail) {
//        Optional<Issue> issueOptional = issueRepository.findById(issueID);
//
//        if (issueOptional.isPresent()) {
//            Issue issue = issueOptional.get();
//
//            if (issue.getStatus() != IssueStatus.OPEN) {
//                return "This task is already assigned to another service provider or is still in host approval.";
//            }
//            Optional<User> optionalServiceProvider = userRepository.findUserByEmailAddress(providerEmail);
//            if (optionalServiceProvider.isPresent()) {
//                User serviceProvider = optionalServiceProvider.get();
//                if (serviceProvider.getRole() != Role.SERVICE_PROVIDER) {
//                    return "cannot assign a role to a "+ serviceProvider.getRole()+" user";
//                }
//                issue.setProvider(serviceProvider);
//                issue.setStatus(IssueStatus.IN_PROGRESS);
//                issueRepository.save(issue);
//                return "Success! You have claimed this task.";
//            }
//            return "provider not found";
//        }
//        return "issue not found";
//    }
//
//    public List<Issue> getIssueByServiceProvider(String email){
//        return issueRepository.findByProviderEmailAddress(email);
//    }
//}
package com.fixStay.backend.service;

import com.fixStay.backend.dto.IssueRequest;
import com.fixStay.backend.model.*;
import com.fixStay.backend.repository.IssueRepository;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public IssueService(IssueRepository issueRepository,
                        UserRepository userRepository,
                        PropertyRepository propertyRepository) {
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

            return "Your issue is now public, waiting for service providers.";
        }

        return "Issue not found";
    }

    public List<Issue> getAvailableTasks() {
        return issueRepository.findByStatus(IssueStatus.OPEN);
    }

    public String assignIssueToServiceProvider(Long issueID, String providerEmail) {
        Optional<Issue> issueOptional = issueRepository.findById(issueID);

        if (issueOptional.isPresent()) {
            Issue issue = issueOptional.get();

            if (issue.getStatus() != IssueStatus.OPEN) {
                return "This task is already assigned or unavailable.";
            }

            Optional<User> optionalServiceProvider =
                    userRepository.findUserByEmailAddress(providerEmail);

            if (optionalServiceProvider.isPresent()) {
                User serviceProvider = optionalServiceProvider.get();

                if (serviceProvider.getRole() != Role.SERVICE_PROVIDER) {
                    return "Cannot assign task to " + serviceProvider.getRole();
                }

                issue.setProvider(serviceProvider);
                issue.setStatus(IssueStatus.IN_PROGRESS);
                issueRepository.save(issue);

                return "Success! You claimed this task.";
            }

            return "Provider not found";
        }

        return "Issue not found";
    }

    public List<Issue> getIssueByServiceProvider(String email) {
        return issueRepository.findByProviderEmailAddress(email);
    }

    public String completeIssue(Long issueId,
                                String providerEmail,
                                MultipartFile proofImage,
                                String notes) throws IOException {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (issue.getProvider() == null) {
            return "No provider assigned to this task.";
        }

        if (!issue.getProvider().getEmailAddress().equals(providerEmail)) {
            return "You are not assigned to this task.";
        }

        if (issue.getStatus() != IssueStatus.IN_PROGRESS) {
            return "Only active tasks can be completed.";
        }

        //FIX
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        java.io.File directory = new java.io.File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // save file
        String fileName = java.util.UUID.randomUUID()
                + "_" + proofImage.getOriginalFilename();

        java.io.File destinationFile = new java.io.File(uploadDir + fileName);

        proofImage.transferTo(destinationFile);

        issue.setCompletionProof("/uploads/" + fileName);

        //fix end

        // update issue
        issue.setCompletionNotes(notes);
        issue.setCompletedAt(java.time.LocalDateTime.now());
        issue.setStatus(IssueStatus.COMPLETED);

        issueRepository.save(issue);

        return "Task marked as completed successfully.";
    }

    public String verifyIssue(Long issueId, Integer rating, String review) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (issue.getStatus() != IssueStatus.COMPLETED) {
            return "Only completed issues can be verified.";
        }

        if (rating == null || rating < 1 || rating > 5) {
            return "Rating must be between 1 and 5.";
        }

        issue.setStatus(IssueStatus.VERIFIED);
        issue.setProviderRating(rating);
        issue.setProviderReview(review);

        issueRepository.save(issue);

        return "Issue verified and provider rated successfully.";
    }
}