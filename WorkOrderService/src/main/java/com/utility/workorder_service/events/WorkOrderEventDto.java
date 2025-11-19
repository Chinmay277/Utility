package com.utility.workorder_service.events;

import com.utility.workorder_service.entity.WorkOrder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class WorkOrderEventDto {
	
	private String workOrderId;
	private String assetId;
	private String status;
	public void populateFromWorkOrder(WorkOrder workOrder) {
		// TODO Auto-generated method stub
		this.workOrderId = workOrder.getWoId();
		this.assetId = workOrder.getAssetId();
		this.status = workOrder.getStatus().name();
		
	}

}
