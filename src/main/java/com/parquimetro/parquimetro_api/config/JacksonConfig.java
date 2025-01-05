package com.parquimetro.parquimetro_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configura o timezone para Brasília
        objectMapper.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        // Desabilita timestamps em datas
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Registra o módulo de tempo do Java 8+
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
}