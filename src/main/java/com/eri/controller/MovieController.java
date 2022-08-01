package com.eri.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie-api")
public class MovieController {

    @GetMapping("/movies")
    public String listMovies(@RequestParam(required = false) String id){
        if(id == null){
            return "Hello";
        } else {
            return "Hello" + id;
        }
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
