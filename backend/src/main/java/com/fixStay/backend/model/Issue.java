package com.fixStay.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @Column(updatable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    // Relatia cu Guest-ul care a raportat
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    @JsonIgnoreProperties({"password"})
    private User guest;

    // Relatia cu Proprietatea unde a aparut problema
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // Relatia cu Provider-ul care repara (poate fi null la inceput)
    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonIgnoreProperties({"password"})
    private User provider;


    @Column(nullable = true)
    private Integer rating;
}