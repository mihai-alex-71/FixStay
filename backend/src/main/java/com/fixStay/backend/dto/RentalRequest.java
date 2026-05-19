package com.fixStay.backend.dto;

import java.time.LocalDate;

public record RentalRequest(
        String guestEmail,
        Long propertyId,
        LocalDate startDate,
        LocalDate endDate
) {}