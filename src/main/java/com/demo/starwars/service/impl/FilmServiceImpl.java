package com.demo.starwars.service.impl;

import com.demo.starwars.entity.Film;
import com.demo.starwars.service.FilmService;
import com.demo.starwars.web.model.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient webClient;

    @Override
    public List<Film> getAllFilms() {
        SwapiResponse<Film> response = webClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Film>>() {
                })
                .block();

        return response.getResults();
    }

    @Override
    public Film getFilmById(int index) {
        Film response = webClient.get()
                .uri("/films/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Film>() {})
                .block();

        return response;
    }

    @Override
    public boolean saveFilm(Film film) {
        //films.add(film);
        return true;
    }

    @Override
    public boolean deleteFilms() {
        //films.clear();
        return true;
    }

}
