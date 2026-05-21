package com.fixStay.backend.controller;

import com.fixStay.backend.dto.RentalRequest;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.Rental;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.RentalRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/rentals")
public class RentalController {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RentalRepository rentalRepository;

    public RentalController(UserRepository userRepository, PropertyRepository propertyRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.rentalRepository = rentalRepository;
    }

    // --- SCURTĂTURĂ 1: Află automat cine e gazda pentru un Guest ---
    @GetMapping("/active-host")
    public ResponseEntity<?> getActiveHost(@RequestParam String guestEmail) {

        // 1. Căutăm rezervarea activă
        var optionalRental = rentalRepository.findAllByGuest_EmailAddress(guestEmail).stream()
                .filter(r -> !r.isCompleted())
                .findFirst();

        // 2. Dacă o găsim, trimitem pachetul cu date (Map)
        if (optionalRental.isPresent()) {
            Rental r = optionalRental.get();
            Map<String, Object> result = new HashMap<>();
            result.put("hostEmail", r.getProperty().getHost().getEmailAddress());
            result.put("propertyId", r.getProperty().getId());
            result.put("propertyName", r.getProperty().getName());

            return ResponseEntity.ok(result);
        }
        // 3. Dacă nu o găsim, trimitem mesaj de eroare (String)
        else {
            return ResponseEntity.status(404).body("No active rental found");
        }
    }

    // --- SCURTĂTURĂ 2: Află automat cine e chiriașul unei case (Fără prompt!) ---
    @GetMapping("/active-guest")
    public ResponseEntity<?> getActiveGuest(@RequestParam Long propertyId) {
        return rentalRepository.findAllByPropertyId(propertyId).stream()
                .filter(r -> !r.isCompleted())
                .findFirst()
                .map(r -> ResponseEntity.ok(r.getGuest().getEmailAddress()))
                .orElseGet(() -> ResponseEntity.status(404).body("No active guest found"));
    }

    @GetMapping("/guest-info")
    public ResponseEntity<?> getGuestInfo(@RequestParam String email) {
        return userRepository.findUserByEmailAddress(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentProperty(@RequestBody RentalRequest request) {
        User guest = userRepository.findUserByEmailAddress(request.guestEmail())
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        Property property = propertyRepository.findById(request.propertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (request.startDate().isAfter(request.endDate())) {
            return ResponseEntity.badRequest().body("Eroare: Data de început trebuie să fie înainte de data de final!");
        }

        boolean isOccupied = rentalRepository.existsActiveRentalByProperty(
                request.propertyId(), request.startDate(), request.endDate());

        if (isOccupied) {
            return ResponseEntity.badRequest().body("Ne pare rău! Această proprietate este deja închiriată în perioada selectată.");
        }

        Rental newRental = new Rental();
        newRental.setGuest(guest);
        newRental.setProperty(property);
        newRental.setStartDate(request.startDate());
        newRental.setEndDate(request.endDate());
        newRental.setCompleted(false);

        rentalRepository.save(newRental);

        guest.setCurrentProperty(property);
        userRepository.save(guest);

        return ResponseEntity.ok("Succes! Ai închiriat proprietatea.");
    }

    @PostMapping("/extend")
    public ResponseEntity<String> extendRental(@RequestBody com.fixStay.backend.dto.ExtendRentalRequest request) {
        Rental rental = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new RuntimeException("Rezervarea nu a fost găsită!"));

        if (request.newEndDate().isBefore(rental.getEndDate()) || request.newEndDate().isEqual(rental.getEndDate())) {
            return ResponseEntity.badRequest().body("Eroare: Noua dată trebuie să fie ulterioară celei curente!");
        }

        boolean isOccupied = rentalRepository.existsActiveRentalByPropertyExcluding(
                rental.getProperty().getId(), rental.getId(), rental.getStartDate(), request.newEndDate());

        if (isOccupied) {
            return ResponseEntity.badRequest().body("Ne pare rău! Nu poți prelungi, casa a fost deja rezervată.");
        }

        rental.setEndDate(request.newEndDate());
        rentalRepository.save(rental);

        return ResponseEntity.ok("Prelungire reușită!");
    }

    @PostMapping("/terminate")
    public ResponseEntity<String> terminateRental(@RequestParam String guestEmail){
        Optional<User> guestOptional = userRepository.findUserByEmailAddress(guestEmail);
        if(guestOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("guess not found");
        }

        User guest = guestOptional.get();
        Rental activeRental =  rentalRepository.findAllByGuest_EmailAddress(guestEmail)
                .stream()
                .filter(r -> !r.isCompleted()) // rental completed  ?
                .findFirst()
                .orElse(null);

        if (activeRental == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no active rental found");
        }

        activeRental.setCompleted(true);
        rentalRepository.save(activeRental);

        guest.setCurrentProperty(null);
        userRepository.save(guest);

        return ResponseEntity.ok("your stay has been ended hope you enjoyed");
    }
}