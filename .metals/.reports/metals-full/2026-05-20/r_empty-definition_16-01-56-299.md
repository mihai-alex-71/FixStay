error id: file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/service/PropertyService.java:_empty_/PropertyRequest#hostEmailAddress#
file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/service/PropertyService.java
empty definition using pc, found symbol in pc: _empty_/PropertyRequest#hostEmailAddress#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1383
uri: file:///E:/uni/an3sem2/IWP/project/FixStay/backend/src/main/java/com/fixStay/backend/service/PropertyService.java
text:
```scala
package com.fixStay.backend.service;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.dto.PropertyResponse;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.PropertyStatus;
import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService {


    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public PropertyService(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    public String createProperty(PropertyRequest request, MultipartFile image){

        Optional<User> user = userRepository.findUserByEmailAddress(request.@@hostEmailAddress());

        if(user.isEmpty()){
            return " host with this email does not exists ";
        }

        Property prop = new Property();

        User host = user.get();

        prop.setName(request.name());
        prop.setAddress(request.address());
        prop.setPricePerNight(request.pricePerNight());
        prop.setHost(host);

        prop.setApprovalStatus(PropertyStatus.PENDING);
        prop.setListed(true);
        // ----------------------------------

        try {
            if(image != null && !image.isEmpty()){
                String UPLOAD_DIR = "uploads/";
                File directory = new File(UPLOAD_DIR);
                if(!directory.exists()){
                    directory.mkdirs();
                }

                String originalFileName = image.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);
                Files.copy(image.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

                prop.setPictureFileName(uniqueFileName);
            }
        }catch (IOException e){
            return "Err saving image! "+ e.getMessage();
        }

        propertyRepository.save(prop);

        return "new property has successfully added to your dashboard!";
    }

    public List<PropertyResponse> showProperty(String hostEmailAddress){
        List<Property> properties =  propertyRepository.findAllByHost_EmailAddress(hostEmailAddress);

        return properties.stream().map(property -> new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getAddress(),
                property.getPricePerNight(),
                property.getImageUrl(),
                property.getApprovalStatus(),
                property.getHost().getEmailAddress()
        )).collect(Collectors.toList());
    }

    // --- METODA MODIFICATĂ: Guestul vede doar proprietățile Aprobate și Listate ---
    public List<Property> showAllProperties() {
        List<Property> allProps = propertyRepository.findAll();
        List<Property> visibleProps = new ArrayList<>();

        for (Property p : allProps) {
            if (p.getApprovalStatus() != null && p.getApprovalStatus() == PropertyStatus.APPROVED && p.isListed()) {
                visibleProps.add(p);
            }
        }

        return visibleProps;
    }

    public  List<PropertyResponse> showAllPnedingProperties(){
        List<Property> pendingProperties =  propertyRepository.findByApprovalStatus(PropertyStatus.PENDING);
        return pendingProperties.stream().map(property -> new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getAddress(),
                property.getPricePerNight(),
                property.getImageUrl(),
                property.getApprovalStatus(),
                property.getHost().getEmailAddress()
        )).collect(Collectors.toList());
    }

    public  String chageStatusProperty(Long propertyID, PropertyStatus status, String adminEmail){
        Optional<User> adminOptional = userRepository.findUserByEmailAddress(adminEmail);

        if (adminOptional.isEmpty()){
            return  "user not found";
        }
        User admin = adminOptional.get();
        if (admin.getRole() != Role.ADMIN){
            return  "Only admins can access this function";
        }


        Optional<Property> propertyOptional = propertyRepository.findById(propertyID);
        if (propertyOptional.isEmpty()){
            return "property not found";
        }

        Property property = propertyOptional.get();
        if (property.getApprovalStatus() != PropertyStatus.PENDING){
            return  "property already is not pending for admin verification";
        }

        property.setApprovalStatus(status);
        propertyRepository.save(property);
        return  "property "+property.getName() + " status have been set to "+status.toString();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/PropertyRequest#hostEmailAddress#