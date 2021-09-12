package com.demo.starwars.service;

import com.demo.starwars.entity.Planet;

import java.util.List;

public interface PlanetService {
    //Get all planets
    List<Planet> getAllPlanets();

    //Get planet by index
    Planet getPlanetById(int index);
}
