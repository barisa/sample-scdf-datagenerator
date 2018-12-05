package com.example.datagenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(Source.class)
@EnableScheduling
@SpringBootApplication
public class DatageneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatageneratorApplication.class, args);
	}

	private final Source source;
	private final Random random = new Random();

	@Scheduled(fixedRate = 1000)
	public void randomNumber() {
		String jsonString = "{\"A\": \"value-a\", \"b\": "+random.nextInt()+"}";
		source.output().send(MessageBuilder.withPayload(jsonString).build());
		log.info("Send random json message from demo source app {}", jsonString);
	}
}
