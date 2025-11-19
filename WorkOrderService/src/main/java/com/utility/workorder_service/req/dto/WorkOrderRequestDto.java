package com.utility.workorder_service.req.dto;

import java.time.LocalDateTime;

import com.utility.workorder_service.enums.WoPriority;
import com.utility.workorder_service.enums.WoStatus;
import com.utility.workorder_service.enums.WoType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderRequestDto {

	private String assetId;
	private String description;
	private WoPriority priority;
	private WoType type;
	private LocalDateTime dueDate;
	
}
