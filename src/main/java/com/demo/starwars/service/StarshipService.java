package com.demo.starwars.service;

import com.demo.starwars.entity.Starship;

import java.util.List;

public interface StarshipService {
    //Get all starships
    List<Starship> getAllStarships();

    //Get starship by index
    Starship getStarshipById(int index);
}
