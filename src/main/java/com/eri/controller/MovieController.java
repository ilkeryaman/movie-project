package com.eri.controller;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movie-api")
public class MovieController {

    @Resource(name="movieManagerFileService")
    IMovieManagerService movieManagerService;

    @GetMapping("/movies")
    public List<Movie> listMovies(@RequestParam(required = false) Integer id){
        if(id != null){
            Movie movie = movieManagerService.findMovieById(id);
            return movie == null ? Collections.emptyList() : Arrays.asList(movie);
        }
        return movieManagerService.getMovies();
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable("id") Integer id){
        return movieManagerService.findMovieById(id);
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@Valid @RequestBody Movie movie){
        movieManagerService.addMovie(movie);
    }

    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") Integer id){
        movieManagerService.removeMovieById(id);
    }
}
