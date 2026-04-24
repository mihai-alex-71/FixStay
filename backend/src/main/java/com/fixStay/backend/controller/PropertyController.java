package com.fixStay.backend.controller;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping("/create-property")
    public String addProperty(@RequestBody PropertyRequest propertyRequest){
        return propertyService.createProperty(propertyRequest);
    }

    @GetMapping("/get-properties")
    public List<Property> showProperties(@RequestParam String email){
        return  propertyService.showProperty(email);
    }
}
