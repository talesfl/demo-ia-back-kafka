package br.com.demo.ia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.ia.domain.Email;
import br.com.demo.ia.service.KafkaProducerService;

@RestController
@RequestMapping("kafkas")
class KafkaProducerController {

	private final KafkaProducerService kafkaService;

	public KafkaProducerController(final KafkaProducerService kafkaService) {
		this.kafkaService = kafkaService;
	}
	
	@PutMapping("publish-admin")
	ResponseEntity<Void> dispatchAdminEmail(@RequestBody final Email email) {
		
		kafkaService.dispatchAdminEmail(email);
		
		return ResponseEntity.noContent().build();
	}

	@PutMapping("publish-user")
	ResponseEntity<Void> dispatchUserEmail(@RequestBody final Email email) {
		
		kafkaService.dispatchUserEmail(email);
		
		return ResponseEntity.noContent().build();
	}
}
