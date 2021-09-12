package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Starship;
import com.demo.starwars.service.StarshipService;
import com.demo.starwars.web.model.StarshipResponse;
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
public class StarshipController {

    @Autowired
    private StarshipService starshipService;

    //Get all starships
    @GetMapping("/starships")
    public Response<List<StarshipResponse>> getAllStarships(){
        List<Starship> starships = starshipService.getAllStarships();
        List<StarshipResponse> starshipResponses = starships.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<StarshipResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(starshipResponses)
                .build();
    }

    //Get starships by index
    @GetMapping("/starships/{index}")
    public Response<StarshipResponse> getStarshipById(@PathVariable int index){
        Starship starship = starshipService.getStarshipById(index);
        return Response.<StarshipResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(starship))
                .build();
    }

    //convert result to response
    private StarshipResponse convertToResponse(Starship starship) {
        StarshipResponse starshipResponse = new StarshipResponse();
        BeanUtils.copyProperties(starship, starshipResponse);
        return starshipResponse;
    }
}
