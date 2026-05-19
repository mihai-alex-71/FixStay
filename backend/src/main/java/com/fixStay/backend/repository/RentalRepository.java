package com.fixStay.backend.repository;

import com.fixStay.backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByGuest_EmailAddress(String email);

    List<Rental> findAllByPropertyId(Long propertyId);

    boolean existsByPropertyIdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(Long propertyId, LocalDate start, LocalDate end);

    boolean existsByPropertyIdAndIdNotAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
            Long propertyId, Long rentalId, LocalDate start, LocalDate end);
}