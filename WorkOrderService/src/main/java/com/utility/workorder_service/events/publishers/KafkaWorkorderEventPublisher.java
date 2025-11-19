package com.utility.workorder_service.events.publishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaWorkorderEventPublisher extends AbstractKafkaEventPublisher {

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void publishWorkOrderEvent(String topic, Object event) {
		try {
			validateEvent(event);
			logEventPublishing(topic, event);
			// Simulate publishing to Kafka
			kafkaTemplate.send(topic, event);
			// In a real implementation, you would use a KafkaTemplate or similar here
			System.out.println("Simulated publishing to Kafka topic: " + topic + " | Event: " + event.toString());
			confirmEventPublished(topic, event);
		} catch (Exception e) {
			handlePublishingError(e);
		}
	}
}
