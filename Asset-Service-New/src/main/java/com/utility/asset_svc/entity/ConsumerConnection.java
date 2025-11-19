package com.utility.asset_svc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consumer_connection")
@Getter
@Setter
public class ConsumerConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connection_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String consumerNumber;

    private String consumerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pole_id", nullable = false)
    private Pole pole;
}

