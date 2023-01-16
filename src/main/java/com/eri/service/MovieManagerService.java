package com.eri.service;

import com.eri.constant.enums.CacheKey;
import com.eri.constant.enums.Topic;
import com.eri.exception.MovieNotFoundException;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class MovieManagerService implements IMovieManagerService {

    @Resource
    private IMessageService messageService;

    @Resource
    protected ICacheService cacheService;

    @Override
    public List<Movie> listNewComers(){
        List<Movie> newComers = cacheService.findListFromCacheWithKey(CacheKey.NEWCOMERS.getName());
        return newComers == null ? Collections.emptyList() : newComers;
    }

    @Override
    public Movie findMovieById(int id) {
        Optional<Movie> movieOptional = getMovies().stream().filter(movie -> movie.getId() == id).findFirst();
        if(movieOptional.isPresent()){
            return movieOptional.get();
        } else {
            throw new MovieNotFoundException();
        }
    }

    @Override
    public void addMovie(Movie movie) {
        getMovies().add(movie);
        messageService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
    }

    @Override
    public void removeMovieById(int id) {
        getMovies().removeIf(movie -> movie.getId() == id);
    }

    public abstract List<Movie> getMovies();
}
