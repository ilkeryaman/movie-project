package com.eri.service.impl;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("movieManagerFileService")
public class MovieManagerFileServiceImpl implements IMovieManagerService {
    private List<Movie> movies;

    @Value("${movie.list.file.url}")
    String movieFileUrl;

    @Autowired
    ObjectMapper objectMapper;

    public MovieManagerFileServiceImpl(){
        movies = new ArrayList<>();
    }

    @PostConstruct
    public void afterInitialize(){
        try {
            movies = objectMapper.readValue(new File(movieFileUrl), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> getMovies(){
        return movies;
    }
}
