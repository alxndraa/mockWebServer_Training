package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Species;
import com.demo.starwars.service.SpeciesService;
import com.demo.starwars.web.model.Response;
import com.demo.starwars.web.model.SpeciesResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpeciesController {
    @Autowired
    private SpeciesService speciesService;

    //Get all species
    @GetMapping("/species")
    public Response<List<SpeciesResponse>> getAllSpecies(){
        List<Species> species = speciesService.getAllSpecies();
        List<SpeciesResponse> speciesResponses = species.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<SpeciesResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(speciesResponses)
                .build();
    }

    //Get species by index
    @GetMapping("/species/{index}")
    public Response<SpeciesResponse> getSpeciesById(@PathVariable int index){
        Species species = speciesService.getSpeciesById(index);
        return Response.<SpeciesResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(species))
                .build();
    }

    //convert result to response
    private SpeciesResponse convertToResponse(Species species) {
        SpeciesResponse speciesResponse = new SpeciesResponse();
        BeanUtils.copyProperties(species, speciesResponse);
        return speciesResponse;
    }
}
