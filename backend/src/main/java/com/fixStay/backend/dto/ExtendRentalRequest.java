package com.fixStay.backend.dto;

import java.time.LocalDate;

public record ExtendRentalRequest(
        Long rentalId,
        LocalDate newEndDate
) {}