package com.fmo.fmanager.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PositionsService {
    private final RestTemplate restTemplate;

    @Value("${spring.services.positions}")
    private String url;

    public PositionsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPositionsPlainJSON() {
        return this.restTemplate.getForObject(url, String.class);
    }
}
