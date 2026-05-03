package com.fixStay.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //s1
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // pentru guest -> daca are sau nu inchiriata o proprietate
    @JsonIgnoreProperties("host")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_property_id")
    private Property currentProperty; // Daca e null, guest-ul nu a inchiriat nimic

}

