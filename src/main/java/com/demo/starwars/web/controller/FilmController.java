package com.demo.starwars.web.controller;

import com.demo.starwars.entity.Film;
import com.demo.starwars.service.FilmService;
import com.demo.starwars.web.model.FilmResponse;
import com.demo.starwars.web.model.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FilmController {

    @Autowired
    private FilmService filmService;

    //Get all films
    @GetMapping("/films")
    public Response<List<FilmResponse>> getAllFilms(){
        List<Film> films = filmService.getAllFilms();
        List<FilmResponse> filmResponses = films.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<FilmResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(filmResponses)
                .build();
    }

    //Get films by index
    @GetMapping("/films/{index}")
    public Response<FilmResponse> getFilmById(@PathVariable int index){
        Film film = filmService.getFilmById(index);
        return Response.<FilmResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(film))
                .build();
    }

    //Save new a film
    @PostMapping("/films")
    public boolean saveFilms(@RequestBody Film film){
        return filmService.saveFilm(film);
    }

    //Delete all films
    @DeleteMapping("/films")
    public boolean deleteFilms(){
        return filmService.deleteFilms();
    }

    //convert result to response
    private FilmResponse convertToResponse(Film film) {
        FilmResponse filmResponse = new FilmResponse();
        BeanUtils.copyProperties(film, filmResponse);
        return filmResponse;
    }
}
