package com.utility.workforce_service.req.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReqDto {
	String name;
	Set<String> roles;
	AddressDto address;
}
