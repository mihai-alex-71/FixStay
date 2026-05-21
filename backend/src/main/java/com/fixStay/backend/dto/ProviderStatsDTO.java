package com.fixStay.backend.dto;

public record ProviderStatsDTO(
        String firstName,
        String lastName,
        String emailAddress,
        long resolvedTasks,
        long inProgressTasks,
        Double averageRating
) {}