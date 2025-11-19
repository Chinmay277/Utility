package com.utility.notification_service.events.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.utility.notification_service.events.WorkOrderEventDto;

@Component
public class KafkaWorkordereventConsumer {
	
	@KafkaListener(topics = "wo-events", groupId = "notification-service-group")
	public void consumeWorkOrderEvent(Object woEvent) {
		System.out.println("Consumed Work Order Event Message: " + woEvent.toString());
	}

}
