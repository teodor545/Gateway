package com.task.currency.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.entity.BaseRequest;
import com.task.currency.gateway.repository.BaseRequestRepository;
import com.task.currency.gateway.util.mapper.CurrencyObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
public class GatewayApplication implements CommandLineRunner {

	private BaseRequestRepository brr;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	//For testing without consuming limited api calls
	@Override
	public void run(String... args) throws Exception {
		//populateOffline();
	}

	private void populateOffline() throws IOException {
		File file = ResourceUtils.getFile("classpath:response_2.json");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		CurrencyRateDto sample = objectMapper.readValue(file, CurrencyRateDto.class);
		BaseRequest baseRequest = CurrencyObjectMapper.mapResponseToBaseRequest(sample);
		brr.save(baseRequest);
	}
}
