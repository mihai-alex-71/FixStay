package com.fixStay.backend.controller;

import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/rentals")
public class RentalController {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public RentalController(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    // Pt a vedea datele Guest-ului (daca are chirie sau nu)
    @GetMapping("/guest-info")
    public User getGuestInfo(@RequestParam String email) {
        return userRepository.findUserByEmailAddress(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Guest-ul apasa "Rent Now"
    @PostMapping("/rent")
    public String rentProperty(@RequestParam String email, @RequestParam Long propertyId) {
        User guest = userRepository.findUserByEmailAddress(email)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        guest.setCurrentProperty(property);
        userRepository.save(guest);

        return "Successfully rented the property!";
    }
}