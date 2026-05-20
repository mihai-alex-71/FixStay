package com.fixStay.backend.dto;

import com.fixStay.backend.model.PropertyStatus;

public record PropertyResponse(Long id, String name, String address, Double pricePerNight, String imageUrl,String pictureFileName, PropertyStatus status, String hostEmail) {
}
