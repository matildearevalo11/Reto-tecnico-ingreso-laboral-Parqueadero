package com.prueba.pruebaparqueadero;

import com.prueba.pruebaparqueadero.seeders.Seeder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
@EnableFeignClients
public class PruebaparqueaderoApplication {

	private final Seeder seeder;

	public static void main(String[] args) {
		SpringApplication.run(PruebaparqueaderoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> seeder.seed();
	}

}
