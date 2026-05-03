package com.fixStay.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    @JsonIgnoreProperties("currentProperty") // Spunem JSON-ului: "Cand arati Host-ul, nu-mi mai arata ce case are el inchiriate ca guest"
    private User host;

    public String getImageUrl() {
        if (this.pictureFileName != null) {
            // Combine your server address with the file name
            return "http://localhost:8080/uploads/" + this.pictureFileName;
        }
        return null;
    }

}
