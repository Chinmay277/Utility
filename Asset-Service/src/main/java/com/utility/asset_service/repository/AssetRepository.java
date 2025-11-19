package com.utility.asset_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utility.asset_service.entity.Asset;
import com.utility.asset_service.model.AssetType;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	// Additional query methods can be defined here if needed
	public Asset findByAssetId(String assetId);
	public boolean existsByAssetId(String assetId);
	public void deleteByAssetId(String assetId);
	public boolean existsById(Long id);
	public void deleteById(Long id);
	public Asset findById(long id);
	public Asset save(Asset asset);
	public List<Asset> findAll();
	public List<Asset> findByParent(Asset parent);
	public List<Asset> findByAssetType(com.utility.asset_service.model.AssetType assetType);
	public List<Asset> findByState(com.utility.asset_service.model.State state);
	public List<Asset> findByStatus(com.utility.asset_service.model.AssetStatus status);
	public List<Asset> findByCity(String city);
	public List<Asset> findByName(String name);
	public List<Asset> findByLatitude(Double latitude);
	public List<Asset> findByLongitude(Double longitude);
	public List<Asset> findByParent_AssetId(String parentAssetId);
	public List<Asset> findByParent_Id(Long parentId);
	public Optional<Asset> findTopByAssetTypeOrderByIdDesc(AssetType assetType);
	

}
