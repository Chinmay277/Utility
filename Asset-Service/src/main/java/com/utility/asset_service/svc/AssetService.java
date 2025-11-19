package com.utility.asset_service.svc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.utility.asset_service.dto.req.AssetReqDto;
import com.utility.asset_service.dto.resp.AssetByTypeRespDto;
import com.utility.asset_service.dto.resp.AssetRespDto;
import com.utility.asset_service.entity.Asset;
import com.utility.asset_service.model.AssetType;
import com.utility.asset_service.repository.AssetRepository;

@Service
public class AssetService {
    private final ModelMapper modelMapper;
    private final JdbcTemplate jdbcTemplate;
    private final AssetRepository assetRepository;

    @Autowired
    public AssetService(ModelMapper modelMapper, JdbcTemplate jdbcTemplate, AssetRepository assetRepository) {
        this.modelMapper = modelMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.assetRepository = assetRepository;
    }
	
	public String getAssetId(AssetType assetType) {
		String prefix = "";
		long seq = 1;
		switch (assetType) {
			case REGION:
				prefix = "REG";
				seq = jdbcTemplate.queryForObject("SELECT nextval('region_seq')", Long.class);
				break;
			case SUBSTATION:
				prefix = "SUB";
				seq = jdbcTemplate.queryForObject("SELECT nextval('substation_seq')", Long.class);
				break;
			case TRANSFORMER:
				prefix = "TRF";
				seq = jdbcTemplate.queryForObject("SELECT nextval('transformer_seq')", Long.class);
				break;
			case POLE:
				prefix = "POL";
				seq = jdbcTemplate.queryForObject("SELECT nextval('pole_seq')", Long.class);
				break;
			default:
				throw new IllegalArgumentException("Invalid asset type: " + assetType);
		}
		
		
		
		String assetId = String.format("%s-%0"+Math.max(4, String.valueOf(seq).length()) + "d", prefix, seq);
			
		return assetId;
		
	}

	public ResponseEntity<AssetRespDto> createAsset(AssetReqDto reqDto) {
		Asset asset = modelMapper.map(reqDto, Asset.class);
		asset.setAssetId(getAssetId(reqDto.getAssetType()));
		asset.setCreatedAt(LocalDateTime.now());
		asset.setUpdatedAt(LocalDateTime.now());

		String parentAssetKey = reqDto.getParentAssetId();
        if ((parentAssetKey == null || parentAssetKey.isEmpty()) && reqDto.getParentId() != null && !reqDto.getParentId().isEmpty()) {
            parentAssetKey = reqDto.getParentId();
        }
        System.out.println("Parent asset key from request: " + parentAssetKey);
        if (parentAssetKey != null && !parentAssetKey.isEmpty()) {
            Asset parent = assetRepository.findByAssetId(parentAssetKey);
            System.out.println("Parent asset found: " + (parent != null ? parent.getAssetId() : "null"));
            if (parent != null) {
                asset.setParent(parent);
            } else {
                System.out.println("Parent asset not found for assetId: " + parentAssetKey);
            }
        }
        System.out.println("Asset to be saved: " + asset + ", parent: " + (asset.getParent() != null ? asset.getParent().getAssetId() : "null"));
		return assetRepository.save(asset) != null
				? ResponseEntity.ok(modelMapper.map(asset, AssetRespDto.class))
				: ResponseEntity.status(500).build();
	}
	
	public ResponseEntity<List<AssetRespDto>> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        List<AssetRespDto> respList = assets.stream()
            .map(asset -> modelMapper.map(asset, AssetRespDto.class))
            .toList();
        return ResponseEntity.ok(respList);
    }

    public ResponseEntity<AssetRespDto> getAssetById(String assetId) {
        Asset asset = assetRepository.findByAssetId(assetId);
        if (asset == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(asset, AssetRespDto.class));
    }

    public ResponseEntity<AssetRespDto> updateAsset(String assetId, AssetReqDto reqDto) {
        Asset existing = assetRepository.findByAssetId(assetId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        // Update fields (except id, assetId, createdAt)
        existing.setName(reqDto.getName());
        existing.setAssetType(reqDto.getAssetType());
        existing.setCity(reqDto.getCity());
        existing.setState(reqDto.getState());
        existing.setLatitude(reqDto.getLatitude());
        existing.setLongitude(reqDto.getLongitude());
        existing.setStatus(reqDto.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        // Handle parent
        String parentAssetKey = reqDto.getParentAssetId();
        if ((parentAssetKey == null || parentAssetKey.isEmpty()) && reqDto.getParentId() != null && !reqDto.getParentId().isEmpty()) {
            parentAssetKey = reqDto.getParentId();
        }
        if (parentAssetKey != null && !parentAssetKey.isEmpty()) {
            Asset parent = assetRepository.findByAssetId(parentAssetKey);
            if (parent != null) {
                existing.setParent(parent);
            }
        } else {
            existing.setParent(null);
        }
        Asset saved = assetRepository.save(existing);
        return ResponseEntity.ok(modelMapper.map(saved, AssetRespDto.class));
    }

    public ResponseEntity<Void> deleteAsset(String assetId) {
        Asset asset = assetRepository.findByAssetId(assetId);
        if (asset == null) {
            return ResponseEntity.notFound().build();
        }
        assetRepository.delete(asset);
        return ResponseEntity.noContent().build();
    }

	public ResponseEntity<?> getAssetGroupbyType() {
		List<Asset> allAssets = assetRepository.findAll();
		Map<AssetType, List<AssetRespDto>> groupedByTypeSorted = allAssets.stream()
			.sorted(Comparator.comparing(Asset::getAssetType).thenComparing(Asset::getName, String.CASE_INSENSITIVE_ORDER))
			.collect(Collectors.groupingBy(
				Asset::getAssetType,
				Collectors.mapping(asset -> modelMapper.map(asset, AssetRespDto.class), Collectors.toList())
			));
		return ResponseEntity.ok(groupedByTypeSorted);
	}

	public ResponseEntity<Boolean> ifAssetExists(String assetId) {
		boolean exists = assetRepository.existsByAssetId(assetId);
		return ResponseEntity.ok(exists);
	}
	
	
}