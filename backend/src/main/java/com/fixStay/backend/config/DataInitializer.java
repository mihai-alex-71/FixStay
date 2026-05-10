package com.fixStay.backend.config;

import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.Status;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> {

            // CHECK FIRST
            if (userRepository.findUserByEmailAddress("admin@mail.com").isEmpty()) {

                User admin = new User();

                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setEmailAddress("admin@mail.com");
                admin.setPassword("12345678");
                admin.setRole(Role.ADMIN);
                admin.setStatus(Status.APPROVED);

                userRepository.save(admin);

                System.out.println("Admin account created!");
            }
            else {
                System.out.println("Admin already exists.");
            }
        };
    }
}