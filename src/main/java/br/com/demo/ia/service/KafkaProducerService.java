package br.com.demo.ia.service;

import br.com.demo.ia.domain.Email;

public interface KafkaProducerService {

	void dispatchAdminEmail(Email email);

	void dispatchUserEmail(Email email);

}
