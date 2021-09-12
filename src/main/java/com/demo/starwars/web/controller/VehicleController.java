package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Vehicle;
import com.demo.starwars.service.VehicleService;
import com.demo.starwars.web.model.VehicleResponse;
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
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    //Get all vehicles
    @GetMapping("/vehicles")
    public Response<List<VehicleResponse>> getAllVehicles(){
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<VehicleResponse> vehicleResponses = vehicles.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<VehicleResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(vehicleResponses)
                .build();
    }

    //Get vehicles by index
    @GetMapping("/vehicles/{index}")
    public Response<VehicleResponse> getVehicleById(@PathVariable int index){
        Vehicle vehicle = vehicleService.getVehicleById(index);
        return Response.<VehicleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(vehicle))
                .build();
    }
    
    //convert result to response
    private VehicleResponse convertToResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        BeanUtils.copyProperties(vehicle, vehicleResponse);
        return vehicleResponse;
    }
}
