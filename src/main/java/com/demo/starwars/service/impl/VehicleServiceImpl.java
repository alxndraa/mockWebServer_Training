package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Film;
import com.demo.starwars.entity.Vehicle;
import com.demo.starwars.service.VehicleService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Vehicle> getAllVehicles() {
        SwapiResponse<Vehicle> response = webClient.get()
                .uri("/vehicles")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Vehicle>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Vehicle getVehicleById(int index) {
        Vehicle response = webClient.get()
                .uri("/vehicles/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Vehicle>() {})
                .block();

        return response;
    }
}
