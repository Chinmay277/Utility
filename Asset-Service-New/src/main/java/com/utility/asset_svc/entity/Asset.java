package com.utility.asset_svc.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetCode; // Unique identifier
    private String name;
    private String status; // ACTIVE, INACTIVE, FAULTY, MAINTENANCE
    private LocalDate installedDate;
    private String manufacturer;

    // ✅ Geo-location
    private Double latitude;
    private Double longitude;

    // ✅ Lifecycle behavior (soft delete)
    private Boolean isActive = true;

    public void deactivate() {
        this.isActive = false;
        this.status = "DECOMMISSIONED";
    }
}

