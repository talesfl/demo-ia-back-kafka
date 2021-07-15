package br.com.demo.ia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.demo.ia.configuration.CommonBeansConfig;
import br.com.demo.ia.configuration.kafka.KafkaProducerConfig;
import br.com.demo.ia.domain.Email;

@Service
class KafkaProducerServiceImpl implements KafkaProducerService {
	
	private final KafkaProducerConfig kafkaProducerConfig;
	
	private final CommonBeansConfig commonBeansConfig;
	
	private final String routingKeyAdmin;
	
	private final String routingKey;

	public KafkaProducerServiceImpl(
			@Value("${demo_ia_back.kafka.topic.routing_key}")
			final String routingKey,
			
			@Value("${demo_ia_back.kafka.topic.routing_key_admin}")
			final String routingKeyAdmin,
			
			final KafkaProducerConfig kafkaProducerConfig,
			final CommonBeansConfig commonBeansConfig
	) {
		this.routingKey = routingKey;
		this.routingKeyAdmin = routingKeyAdmin;
		this.commonBeansConfig = commonBeansConfig;
		this.kafkaProducerConfig = kafkaProducerConfig;
	}

	@Override
	public void dispatchAdminEmail(Email email) { dispatch(email, routingKeyAdmin); }

	@Override
	public void dispatchUserEmail(Email email) { dispatch(email, routingKey); }
	
	private void dispatch(Email email, String routingKey) {
		String data = "";
		
		try {
			data = commonBeansConfig.objectMapper().writeValueAsString(email);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Cannot publish the email. Bad content.");
		}
		
		kafkaProducerConfig.kafkaTemplate()
				.send(routingKey, data).addCallback(
						result -> 
						System.out.println(String.format("Topic [ %s ]. Message sent with success.!", routingKey)), 
						result -> 
						System.out.println(String.format("Topic [ %s ]. Cannot send message. Cause: %s", routingKey, result))
				);
	}

}
