package com.utility.asset_service.dto.resp;

public class AssetRespDto {
	
	private String assetId;      // business key
	private String name;
	private String assetType; // Enum
	private String city;
	private String state;         // Enum
	private Double latitude;
	private Double longitude;
	private String status;  // Enum
	private String parentAssetId; // For linking parent asset (if any)

	public String getAssetId() { return assetId; }
	public void setAssetId(String assetId) { this.assetId = assetId; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getAssetType() { return assetType; }
	public void setAssetType(String assetType) { this.assetType = assetType; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }
	public Double getLatitude() { return latitude; }
	public void setLatitude(Double latitude) { this.latitude = latitude; }
	public Double getLongitude() { return longitude; }
	public void setLongitude(Double longitude) { this.longitude = longitude; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getParentAssetId() { return parentAssetId; }
	public void setParentAssetId(String parentAssetId) { this.parentAssetId = parentAssetId; }
}