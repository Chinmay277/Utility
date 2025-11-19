package com.utility.workorder_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utility.workorder_service.req.dto.WorkOrderRequestDto;
import com.utility.workorder_service.service.WorkOrderService;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {
	
	@Autowired
	WorkOrderService workOrderService;
	
	@PostMapping
	public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrderRequestDto workorderRequestDto) {
		return workOrderService.createWorkOrder(workorderRequestDto);
	    // Logic to create a new work order
	}

}
