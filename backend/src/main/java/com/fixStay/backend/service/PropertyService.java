package com.fixStay.backend.service;

import com.fixStay.backend.dto.PropertyRequest;
import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.PropertyRepository;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {


    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;


    public PropertyService(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    public String createProperty(PropertyRequest request){

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

        propertyRepository.save(prop);

        return "new property has successfully added to your dashboard!";
    }

    public List<Property> showProperty(String hostEmailAddress){
        return propertyRepository.findAllByHost_EmailAddress(hostEmailAddress);
    }

}
