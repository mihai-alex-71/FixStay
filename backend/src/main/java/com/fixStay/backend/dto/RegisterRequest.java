package com.fixStay.backend.dto;

import com.fixStay.backend.model.Role;

public record RegisterRequest(String firstName, String lastName, String email, String password, Role role) {
}
