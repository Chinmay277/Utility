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
@Table(name = "pole")
@Getter
@Setter
public class Pole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pole_id")
    private Long id;

    @Column(nullable = false)
    private String poleNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer_id", nullable = false)
    private Transformer transformer;

    @OneToMany(mappedBy = "pole", fetch = FetchType.LAZY)
    private List<ConsumerConnection> consumers = new ArrayList();
}

