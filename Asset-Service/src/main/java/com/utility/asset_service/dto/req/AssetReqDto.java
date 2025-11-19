package com.utility.asset_service.dto.req;

import com.utility.asset_service.model.AssetStatus;
import com.utility.asset_service.model.AssetType;
import com.utility.asset_service.model.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetReqDto {

    private String assetId;      // business key
    private String name;
    private AssetType assetType; // Enum
    private String city;
    private State state;         // Enum
    private Double latitude;
    private Double longitude;
    private AssetStatus status;  // Enum
    private String parentAssetId; // For linking parent asset (if any)
    private String parentId; // Also allow parentId for flexibility
}