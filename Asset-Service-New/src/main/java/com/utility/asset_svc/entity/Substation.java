package com.utility.asset_svc.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "substation")
@Getter
@Setter
public class Substation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substation_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "capacity_mva")
    private Double capacityMVA;

    @OneToMany(mappedBy = "substation", fetch = FetchType.LAZY)
    private List<Feeder> feeders = new ArrayList<>();
}

