package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Film;
import com.demo.starwars.entity.Starship;
import com.demo.starwars.service.StarshipService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class StarshipServiceImpl implements StarshipService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Starship> getAllStarships() {
        SwapiResponse<Starship> response = webClient.get()
                .uri("/starships")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Starship>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Starship getStarshipById(int index) {
        Starship response = webClient.get()
                .uri("/starships/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Starship>() {})
                .block();

        return response;
    }
}
