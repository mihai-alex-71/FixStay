package com.fixStay.backend.repository;

import com.fixStay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//s1

import com.fixStay.backend.model.Role;
import com.fixStay.backend.model.Status;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmailAddress(String email);

    Boolean existsByEmailAddress(String email);

    //s1
    List<User> findByRoleAndStatus(Role role, Status status);
}
