package com.prueba.pruebaparqueadero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PruebaparqueaderoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaparqueaderoApplication.class, args);
	}

}
