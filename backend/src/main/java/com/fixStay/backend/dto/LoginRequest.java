package com.fixStay.backend.dto;

// data catcher ( security reason )


public record LoginRequest(String email, String password) {

}
