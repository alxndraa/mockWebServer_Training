package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Film;
import com.demo.starwars.entity.Person;
import com.demo.starwars.service.PersonService;
import com.demo.starwars.web.model.FilmResponse;
import com.demo.starwars.web.model.PersonResponse;
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
public class PersonController {

    @Autowired
    private PersonService personService;

    //Get all people
    @GetMapping("/people")
    public Response<List<PersonResponse>> getAllPeople() {
        List<Person> people = personService.getAllPeople();
        List<PersonResponse> personResponses = people.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<PersonResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(personResponses)
                .build();
    }

    //Get person by index
    @GetMapping("/people/{index}")
    public Response<PersonResponse> getPersonById(@PathVariable int index){
        Person person = personService.getPersonById(index);
        return Response.<PersonResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(person))
                .build();
    }

    //convert result to response
    private PersonResponse convertToResponse(Person person) {
        PersonResponse personResponse = new PersonResponse();
        BeanUtils.copyProperties(person, personResponse);
        return personResponse;
    }
}
