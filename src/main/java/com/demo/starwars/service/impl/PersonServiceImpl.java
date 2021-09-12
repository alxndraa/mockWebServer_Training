package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Person;
import com.demo.starwars.service.PersonService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Person> getAllPeople() {
        SwapiResponse<Person> response = webClient.get()
                .uri("/people")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Person>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Person getPersonById(int index) {
        Person response = webClient.get()
                .uri("/people/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Person>() {})
                .block();

        return response;
    }
}
