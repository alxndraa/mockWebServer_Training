package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Species;
import com.demo.starwars.service.SpeciesService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Species> getAllSpecies() {
        SwapiResponse<Species> response = webClient.get()
                .uri("/species")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Species>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Species getSpeciesById(int index) {
        Species response = webClient.get()
                .uri("/species/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Species>() {})
                .block();

        return response;
    }
}
