package br.com.demo.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
			"br.com.demo.ia.repository", 
			"br.com.demo.ia.domain"
	})
public class DemoIaBackKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoIaBackKafkaApplication.class, args);
	}

}
