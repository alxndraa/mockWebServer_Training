package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Film;
import com.demo.starwars.entity.Planet;
import com.demo.starwars.service.PlanetService;
import com.demo.starwars.web.model.FilmResponse;
import com.demo.starwars.web.model.PlanetResponse;
import com.demo.starwars.web.model.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    //Get all planet
    @GetMapping("/planets")
    public Response<List<PlanetResponse>> getAllPlanets(){
        List<Planet> planets = planetService.getAllPlanets();
        List<PlanetResponse> planetResponses = planets.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<PlanetResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(planetResponses)
                .build();
    }

    //Get planets by index
    @GetMapping("/planets/{index}")
    public Response<PlanetResponse> getPlanetById(@PathVariable int index){
        Planet planet = planetService.getPlanetById(index);
        return Response.<PlanetResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(planet))
                .build();
    }

    //convert result to response
    private PlanetResponse convertToResponse(Planet planet) {
        PlanetResponse planetResponse = new PlanetResponse();
        BeanUtils.copyProperties(planet, planetResponse);
        return planetResponse;
    }
}
