package com.fixStay.backend.controller;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.service.PropertyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping(value = "/create-property", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProperty(
            @ModelAttribute PropertyRequest propertyRequest,
            @RequestParam(value = "image", required = false) MultipartFile image){
        return propertyService.createProperty(propertyRequest, image);
    }

    @GetMapping("/get-properties")
    public List<Property> showProperties(@RequestParam String email){
        return  propertyService.showProperty(email);
    }
}
