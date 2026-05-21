package com.fixStay.backend.repository;

import com.fixStay.backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByGuest_EmailAddress(String email);

    List<Rental> findAllByPropertyId(Long propertyId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rental r " +
            "WHERE r.property.id = :propertyId " +
            "AND r.isCompleted = false " +
            "AND r.endDate >= :startDate " +
            "AND r.startDate <= :endDate")
    boolean existsActiveRentalByProperty(
            @Param("propertyId") Long propertyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rental r " +
            "WHERE r.property.id = :propertyId " +
            "AND r.id <> :rentalId " +
            "AND r.isCompleted = false " +
            "AND r.endDate >= :startDate " +
            "AND r.startDate <= :endDate")
    boolean existsActiveRentalByPropertyExcluding(
            @Param("propertyId") Long propertyId,
            @Param("rentalId") Long rentalId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}