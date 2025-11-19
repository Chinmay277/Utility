package com.utility.workorder_service.events.publishers;

public abstract class AbstractKafkaEventPublisher implements WorkOrdereventPublisher {
	
	public abstract void publishWorkOrderEvent(String topic, Object event);
	
	public void logEventPublishing(String topic, Object event) {
		System.out.println("Publishing event to topic: " + topic + " | Event: " + event.toString());
	}
	
	public void handlePublishingError(Exception e) {
		System.err.println("Error publishing event: " + e.getMessage());
	}
	
	public void confirmEventPublished(String topic, Object event) {
		System.out.println("Event published successfully to topic: " + topic);
	}
	
	public void validateEvent(Object event) {
		if (event == null) {
			throw new IllegalArgumentException("Event cannot be null");
		}
	}
	
	
	

}
