package com.eri.service;

import com.eri.data.MovieMemoryDB;
import com.eri.model.Movie;
import com.eri.service.memorydb.MovieMemoryDBService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieManagerService {

    @Value("${movie.list.file.url}")
    String movieFileUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieMemoryDB movieMemoryDB;

    public List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();
        /*try {
            movies = objectMapper.readValue(new File(movieFileUrl), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        movies = movieMemoryDB.getMovies();
        return movies;
    }
}
