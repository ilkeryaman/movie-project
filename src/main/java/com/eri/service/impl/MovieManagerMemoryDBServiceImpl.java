package com.eri.service.impl;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.memorydb.IMovieMemoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("movieManagerMemoryDBService")
public class MovieManagerMemoryDBServiceImpl implements IMovieManagerService {

    @Autowired
    IMovieMemoryDBService movieMemoryDBService;

    @Override
    public List<Movie> getMovies() {
        return movieMemoryDBService.getMovies();
    }
}
