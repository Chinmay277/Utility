package com.utility.workorder_service.events;

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

}
