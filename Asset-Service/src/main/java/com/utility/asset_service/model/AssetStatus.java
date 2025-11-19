package com.utility.asset_service.model;

import java.util.Arrays;

public enum AssetStatus {
	
	ACTIVE("A","Active"),
	INACTIVE("I","Inactive"),
	DECOMMISSIONED("D","Decommissioned"),
	MAINTENANCE("M","Maintenance"),
	INSPECTION("INS","Inspection"),
	COMPLETED("C","Completed");
	
	private String code;
	private String description;	
	AssetStatus(String code, String description) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static AssetStatus fromCode(String code) {
		return Arrays.stream(AssetStatus.values())
			.filter(status -> status.getCode().equalsIgnoreCase(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
	}

}
