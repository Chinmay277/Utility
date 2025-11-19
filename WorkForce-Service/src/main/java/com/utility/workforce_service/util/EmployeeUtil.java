package com.utility.workforce_service.util;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EmployeeUtil {
	
	public String createEmpId() {
		return "EMP"+ LocalDate.now();
	}

}
