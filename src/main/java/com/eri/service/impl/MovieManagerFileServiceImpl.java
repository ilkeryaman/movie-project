package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import com.eri.util.CacheUtil;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class MovieManagerFileServiceImpl extends MovieManagerService {
    private List<Movie> movies;

    @Value("${movie.list.file.url}")
    private String movieFileUrl;

    @Autowired
    private ObjectMapper objectMapper;

    public MovieManagerFileServiceImpl(){
        movies = new ArrayList<>();
    }

    @PostConstruct
    private void afterInitialize(){
        try {
            movies = objectMapper.readValue(new File(movieFileUrl), new TypeReference<List<Movie>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Movie> getMovies(boolean fromCache){
        return getMovies(fromCache, true);
    }

    @Override
    public List<Movie> getMovies() {
        return getMovies(false, false);
    }

    public List<Movie> getMovies(boolean fromCache, boolean enableCache){
        if(fromCache){
            List<Movie> cachedMovies = cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName());
            if(cachedMovies == null){
                throw new CacheNotInitializedException();
            }
            return cachedMovies;
        } else {
            if(enableCache){
                CacheUtil.cacheIfNeeded(cacheService, new ArrayList<>(movies));
            }
            return movies;
        }
    }
}
