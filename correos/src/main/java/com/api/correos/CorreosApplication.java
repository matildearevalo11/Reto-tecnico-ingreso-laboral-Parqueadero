package com.api.correos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CorreosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorreosApplication.class, args);
	}

}
