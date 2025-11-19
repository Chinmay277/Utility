package com.utility.asset_service.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.utility.asset_service.model.AssetStatus;
import com.utility.asset_service.model.AssetType;
import com.utility.asset_service.model.State;

@Entity
@Table(name = "assets")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String assetId; // Unique identifier for the asset
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetType assetType; // Region, Substation, Transformer, Pole

    // Self-referencing relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Asset> children = new ArrayList<>();

    // Location fields
    private String city;
    @Enumerated(EnumType.STRING)
    private State state;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
	private LocalDateTime updatedAt;    
    @Enumerated(EnumType.STRING)
    private AssetStatus status; // ACTIVE / INACTIVE

    // Helper methods for bidirectional relationship
    public void addChild(Asset child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Asset child) {
        children.remove(child);
        child.setParent(null);
    }
}

