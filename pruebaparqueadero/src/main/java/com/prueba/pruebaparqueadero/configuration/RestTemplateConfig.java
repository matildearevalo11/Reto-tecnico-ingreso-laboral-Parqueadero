package com.prueba.pruebaparqueadero.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

        @Bean
        public RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
                String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
                request.getHeaders().add("Authorization", "Bearer " + token);
                return execution.execute(request, body);
            };
            restTemplate.setInterceptors(Collections.singletonList(interceptor));
            return restTemplate;
        }
    }


