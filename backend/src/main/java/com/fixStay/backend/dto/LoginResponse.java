package com.fixStay.backend.dto;

public record LoginResponse(String message, String role, String emailAddress, String firstName) {
}
