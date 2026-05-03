package com.fixStay.backend.service;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.model.Property;
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

@Service
public class PropertyService {


    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public PropertyService(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    public String createProperty(PropertyRequest request, MultipartFile image){

        Optional<User> user = userRepository.findUserByEmailAddress(request.hostEmailAddress());

        if(user.isEmpty()){
            return " host with this email does not exists ";
        }

        Property prop = new Property();

        User host = user.get();

        prop.setName(request.name());
        prop.setAddress(request.address());
        prop.setPricePerNight(request.pricePerNight());
        prop.setHost(host);

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

    public List<Property> showProperty(String hostEmailAddress){
        return propertyRepository.findAllByHost_EmailAddress(hostEmailAddress);
    }

}
