package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Planet;
import com.demo.starwars.service.PlanetService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Planet> getAllPlanets() {
        SwapiResponse<Planet> response = webClient.get()
                .uri("/planets")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Planet>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Planet getPlanetById(int index) {
        Planet response = webClient.get()
                .uri("/planets/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Planet>() {})
                .block();

        return response;
    }
}
