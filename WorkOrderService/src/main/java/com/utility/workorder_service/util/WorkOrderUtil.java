package com.utility.workorder_service.util;

import org.springframework.stereotype.Component;

@Component
public class WorkOrderUtil {
	
	public String generateWorkOrderNumber() {
		// Simple example: WO- followed by current timestamp
		return "WO-" + System.currentTimeMillis();
	}

}
