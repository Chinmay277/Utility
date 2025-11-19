package com.utility.asset_service.model;

import java.util.Arrays;

public enum State {
	
	MH("Maharashtra"),
	TN("Tamil Nadu"),
	KA("Karnataka"),
	AP("Andhra Pradesh"),
	TS("Telangana"),
	HR("Haryana"),
	GJ("Gujarat"),
	RJ("Rajasthan"),
	MP("Madhya Pradesh"),
	UP("Uttar Pradesh"),
	WB("West Bengal"),
	OR("Odisha"),
	BR("Bihar"),
	GA("Goa"),
	DL("Delhi"),
	PB("Punjab"),
	JK("Jammu and Kashmir"),
	AS("Assam"),
	NL("Nagaland"),
	CT("Chhattisgarh"),
	CH("Chandigarh"),
	HP("Himachal Pradesh"),
	TR("Tripura"),
	MN("Manipur"),
	ML("Meghalaya"),
	AR("Arunachal Pradesh"),
	UT("Uttarakhand"),
	SK("Sikkim"),	
	JH("Jharkhand"),
	AN("Andaman and Nicobar Islands"),
	LD("Lakshadweep"),
	DD("Dadra and Nagar Haveli and Daman and Diu"),
	LK("Ladakh"),
	PY("Puducherry");
	
	private String description;

	State(String description) {
		// TODO Auto-generated constructor stub
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static State fromCode(String code) {
		return Arrays.stream(State.values())
			.filter(state -> state.name().equalsIgnoreCase(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
	}


}
