package com.utility.workforce_service.req.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	
}
