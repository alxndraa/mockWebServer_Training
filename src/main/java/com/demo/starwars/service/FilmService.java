package com.demo.starwars.service;

import com.demo.starwars.entity.Film;

import java.util.List;

public interface FilmService {
    //Get all films
    List<Film> getAllFilms();

    //Get film by index
    Film getFilmById(int index);

    //Save new a film
    boolean saveFilm(Film films);

    //Delete all films
    boolean deleteFilms();
}
