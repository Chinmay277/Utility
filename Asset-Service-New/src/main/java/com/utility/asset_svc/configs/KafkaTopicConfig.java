package com.utility.asset_svc.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
	
	@Value("${spring.kafka.template.default-topic}")
	String defaultTopic;
	
	@Bean
	NewTopic myTopic() {
		return TopicBuilder.name(defaultTopic).build();
	}

}
