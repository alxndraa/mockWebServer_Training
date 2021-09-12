package com.demo.starwars.service;

import com.demo.starwars.entity.Species;

import java.util.List;

public interface SpeciesService {
    //Get all species
    List<Species> getAllSpecies();

    //Get species by index
    Species getSpeciesById(int index);
}
