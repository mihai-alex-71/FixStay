package com.fixStay.backend.dto;

public record MessageRequest(
        String senderEmail,
        String receiverEmail,
        String content
) {}