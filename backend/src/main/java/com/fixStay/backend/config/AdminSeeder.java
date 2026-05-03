package com.fixStay.backend.config;

import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.Status;
import com.fixStay.backend.model.User;
import com.fixStay.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (userRepository.findUserByEmailAddress("admin@fixstay.com").isEmpty()){
                User admin = new User();
                admin.setEmailAddress("admin@fixstay.com"); // The username for logging in
                admin.setFirstName("Super");
                admin.setLastName("Admin");
                admin.setRole(Role.ADMIN); // The crucial security role
                admin.setStatus(Status.APPROVED); // Your friend's new column!

                // 3. Hash the password "admin" so the Bouncer can read it
                admin.setPassword(passwordEncoder.encode("admin"));


                userRepository.save(admin);
                System.out.println("DEBUG : hardcoded admin loaded");
            }
        };
    }
}
