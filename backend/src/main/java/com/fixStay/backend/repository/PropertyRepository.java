package com.fixStay.backend.repository;

import com.fixStay.backend.model.Property;
import com.fixStay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByHost_EmailAddress(String email);

    String host(User host);
}
