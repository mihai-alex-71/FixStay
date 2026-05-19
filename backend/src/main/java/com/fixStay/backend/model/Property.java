package com.fixStay.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
@Getter
@Setter
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Double pricePerNight;

    @Column(nullable = true)
    private String pictureFileName;

    @Enumerated(EnumType.STRING)
    private PropertyStatus approvalStatus;

    @Column(nullable = false)
    private boolean isListed;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    @JsonIgnoreProperties("currentProperty")
    private User host;

    public String getImageUrl() {
        if (this.pictureFileName != null) {
            return "http://localhost:8080/uploads/" + this.pictureFileName;
        }
        return null;
    }

    // MAGIA care trimite email-ul gazdei către Frontend
    @JsonProperty("hostEmailAddress")
    public String getHostEmailAddress() {
        if (this.host != null) {
            return this.host.getEmailAddress();
        }
        return null;
    }
}