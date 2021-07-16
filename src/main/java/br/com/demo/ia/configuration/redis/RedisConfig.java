package br.com.demo.ia.configuration.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {
	
	@Bean
	public RedisTemplate<?, ?> redisTemplate(final RedisConnectionFactory redisConnectionFactory){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
		stringRedisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer());
		
		return stringRedisTemplate;
	}

	@Bean
	public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer(objectMapper());
	}
	
	private ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		return objectMapper;
	}
	
}
