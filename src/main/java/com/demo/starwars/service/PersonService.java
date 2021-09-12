package com.demo.starwars.service;

import com.demo.starwars.entity.Person;

import java.util.List;

public interface PersonService {
    //Get all people
    List<Person> getAllPeople();

    //Get person by ID
    Person getPersonById(int index);
}
