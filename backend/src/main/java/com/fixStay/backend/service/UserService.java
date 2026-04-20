package com.fixStay.backend.service;

import com.fixStay.backend.dto.LoginRequest;
import com.fixStay.backend.dto.RegisterRequest;
import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.User;
import org.springframework.stereotype.Service;

import com.fixStay.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import com.fixStay.backend.dto.LoginResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public String registerUser(RegisterRequest request){
        if (userRepository.existsByEmailAddress(request.email())) {
            return "Error: email already registered!";
        }
        if (request.role() != Role.HOST && request.role() != Role.SERVICE_PROVIDER && request.role() != Role.GUEST) {
            return  " cannot set roles other than host or Service provider . In order to become an admin you need to contact the website owner";
        }

        String hashedPassword = passwordEncoder.encode(request.password());


        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmailAddress(request.email());
        user.setPassword(hashedPassword);
        user.setRole(request.role());

        userRepository.save(user);
        return " User successfully has been added to database ! ";
    }


    public LoginResponse logInUser(LoginRequest request){

        Optional<User> possibleUser = userRepository.findUserByEmailAddress(request.email());


        if ( possibleUser.isEmpty() ){
            return new LoginResponse("The email you provided is not registered yet! please try signing up first !", null);
        }
        User user = possibleUser.get();

        String hashedSavedPassword = user.getPassword();
        if (passwordEncoder.matches(request.password(),hashedSavedPassword)){
            return new LoginResponse(" success ! you are logged in ! ", user.getRole().name());
        }else {
            return new LoginResponse(" wrong password please try again ! ", null);
        }
    }
}
