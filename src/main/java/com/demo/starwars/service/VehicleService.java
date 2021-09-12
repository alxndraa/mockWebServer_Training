package com.demo.starwars.service;

import com.demo.starwars.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    //Get all vehicles
    List<Vehicle> getAllVehicles();

    //Get vehicle by index
    Vehicle getVehicleById(int index);
}
