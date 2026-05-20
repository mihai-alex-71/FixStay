error id: file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/controller/PropertyController.java:com/fixStay/backend/dto/PropertyResponse#
file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/controller/PropertyController.java
empty definition using pc, found symbol in pc: com/fixStay/backend/dto/PropertyResponse#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 123
uri: file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/controller/PropertyController.java
text:
```scala
package com.fixStay.backend.controller;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.dto.@@PropertyResponse;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.PropertyStatus;
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
    public List<PropertyResponse> showProperties(@RequestParam String email){
        return  propertyService.showProperty(email);
    }

    @GetMapping("/all")
    public List<Property> getAllProperties() {
        return propertyService.showAllProperties();
    }

    @GetMapping("/admin/get-pending-properties")
    public List<PropertyResponse> getAllPendingProperties(){ return  propertyService.showAllPnedingProperties();}

    @PostMapping("/admin/update-property-status/{propertyId}")
    public String chageStatusProperty(
            @PathVariable Long propertyId,
            @RequestParam PropertyStatus propertyStatus,
            @RequestParam String adminEmail
    ){
        return  propertyService.chageStatusProperty(propertyId,propertyStatus,adminEmail);
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: com/fixStay/backend/dto/PropertyResponse#