package com.fixStay.backend.repository;

import com.fixStay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmailAddress(String email);

    Boolean existsByEmailAddress(String email);
}
