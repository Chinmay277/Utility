package com.utility.workorder_service.events.publishers;

public interface WorkOrdereventPublisher {
	
	public void publishWorkOrderEvent(String topic, Object event);

}
