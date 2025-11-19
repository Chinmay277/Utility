package com.utility.workforce_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utility.workforce_service.req.dto.EmployeeReqDto;
import com.utility.workforce_service.service.WorkForceService;

@RestController
@RequestMapping("/api/v1/workforce")
public class WorkForceController {
	
	@Autowired
	WorkForceService workForceService;
	
	@PostMapping
	public ResponseEntity<?> addEmployee(@RequestBody EmployeeReqDto empReqDto){
		return workForceService.addEmployee(empReqDto);
	}
	

}
