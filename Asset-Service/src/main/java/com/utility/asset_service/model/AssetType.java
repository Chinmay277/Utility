package com.utility.asset_service.model;

import java.util.Arrays;
public enum AssetType {
	
	REGION("Region"),
	SUBSTATION("Substation"),
	TRANSFORMER("Transformer"),
	POLE("Pole"),
	METER("METER");
	
	private String description;

	AssetType(String description) {
		// TODO Auto-generated constructor stub
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static AssetType fromCode(String code) {
		return Arrays.stream(AssetType.values())
			.filter(type -> type.name().equalsIgnoreCase(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
	}

}
