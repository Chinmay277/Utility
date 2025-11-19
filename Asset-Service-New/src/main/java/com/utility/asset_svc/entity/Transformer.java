package com.utility.asset_svc.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transformer")
@Getter
@Setter
public class Transformer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transformer_id")
    private Long id;

    @Column(nullable = false)
    private String transformerCode;

    @Column(name = "capacity_kva")
    private Integer capacityKVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feeder_id", nullable = false)
    private Feeder feeder;

    @OneToMany(mappedBy = "transformer", fetch = FetchType.LAZY)
    private List<Pole> poles = new ArrayList<>();
}

