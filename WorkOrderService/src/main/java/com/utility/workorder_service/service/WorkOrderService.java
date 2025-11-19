package com.utility.workorder_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.utility.workorder_service.clients.AssetServiceClient;
import com.utility.workorder_service.entity.WorkOrder;
import com.utility.workorder_service.enums.WoStatus;
import com.utility.workorder_service.events.WorkOrderEventDto;
import com.utility.workorder_service.events.publishers.WorkOrdereventPublisher;
import com.utility.workorder_service.repository.WorkorderRepository;
import com.utility.workorder_service.req.dto.WorkOrderRequestDto;
import com.utility.workorder_service.util.WorkOrderUtil;

@Service
public class WorkOrderService {
	
	@Autowired
	WorkorderRepository workorderRepository;
	
	@Autowired
	AssetServiceClient assetServiceClient;
	
	@Autowired
	WorkOrderUtil workOrderUtils;
	
	@Autowired
	WorkOrdereventPublisher workOrdereventPublisher;

	public ResponseEntity<?> createWorkOrder(WorkOrderRequestDto workorderRequestDto) {
		// TODO Auto-generated method stub
		if(assetServiceClient.ifAssetExists(workorderRequestDto.getAssetId()).getBody() == Boolean.FALSE) {
			return ResponseEntity.status(404).body("Asset with ID " + workorderRequestDto.getAssetId() + " not found.");
		}
		WorkOrder workOrder = new WorkOrder();
		workOrder.setAssetId(workorderRequestDto.getAssetId());
		workOrder.setDescription(workorderRequestDto.getDescription());
		workOrder.setPriority(workorderRequestDto.getPriority());
		workOrder.setType(workorderRequestDto.getType());
		workOrder.setDueDate(workorderRequestDto.getDueDate());
		workOrder.setCreatedDate(LocalDateTime.now());
		workOrder.setWoId(workOrderUtils.generateWorkOrderNumber());
		workOrder.setStatus(WoStatus.INITIATED);
		if(workorderRepository.save(workOrder) != null) {
			WorkOrderEventDto workOrderEventDto = new WorkOrderEventDto();
			workOrderEventDto.populateFromWorkOrder(workOrder);
			workOrdereventPublisher.publishWorkOrderEvent("wo-events", workOrderEventDto);
			return ResponseEntity.status(201).body("Work Order created successfully with ID: " + workOrder.getWoId());
		}
		return ResponseEntity.status(500).body("Failed to create Work Order.");
	}

}
