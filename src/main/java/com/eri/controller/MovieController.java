package com.eri.controller;


import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie-api")
public class MovieController {

    @Autowired
    MovieManagerService movieManagerService;

    @GetMapping("/movies")
    public List<Movie> listMovies(@RequestParam(required = false) String id){
        return movieManagerService.getMovies();
    }

    @GetMapping("/movies/{id}")
    public String getMovie(@PathVariable("id") String id){
        return "Hello" + id;
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMovie(@RequestBody String movieName){
        return "Hello";
    }

    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") String id){
    }
}
