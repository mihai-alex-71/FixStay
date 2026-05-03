package com.fixStay.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequest {
    private String title;
    private String description;
    private Long propertyId;
    private String guestEmail; // Folosim email-ul pentru a identifica Guest-ul
}