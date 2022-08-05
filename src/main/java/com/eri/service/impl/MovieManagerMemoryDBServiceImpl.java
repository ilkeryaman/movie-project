package com.eri.service.impl;

import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import com.eri.service.memorydb.IMovieMemoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("movieManagerMemoryDBService")
public class MovieManagerMemoryDBServiceImpl extends MovieManagerService {

    @Autowired
    IMovieMemoryDBService movieMemoryDBService;

    @Override
    public List<Movie> getMovies() {
        return movieMemoryDBService.getMovies();
    }

}
