package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.constant.enums.Topic;
import com.eri.converter.mapstruct.MovieEntityToMovieMapper;
import com.eri.converter.mapstruct.MovieToMovieEntityMapper;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.service.IMovieService;
import com.eri.exception.CacheNotInitializedException;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;
import com.eri.util.CacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("movieManagerDBService")
public class MovieManagerDBServiceImpl implements IMovieManagerService {

    @Resource
    private IMovieService movieService;

    @Resource
    private ICacheService cacheService;

    @Resource
    private MovieEntityToMovieMapper movieEntityToMovieMapper;

    @Resource
    private MovieToMovieEntityMapper movieToMovieEntityMapper;

    @Resource
    private IMessageService messageService;

    @Override
    public List<Movie> getMovies(boolean fromCache) {
        if(fromCache){
            List<Movie> cachedMovies = cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName());
            if(cachedMovies == null){
                throw new CacheNotInitializedException();
            }
            return cachedMovies;
        }

        List<Movie> movies = new ArrayList<>();
        List<MovieEntity> movieEntityList = movieService.getMovieList();
        if(movieEntityList != null){
            movieEntityList.forEach(movieEntity -> movies.add(movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity)));
        }
        CacheUtil.cacheIfNeeded(cacheService, movies);
        return movies;
    }

    @Override
    public List<Movie> listNewComers(){
        List<Movie> newComers = cacheService.findListFromCacheWithKey(CacheKey.NEWCOMERS.getName());
        return newComers == null ? Collections.emptyList() : newComers;
    }

    @Override
    public Movie findMovieById(int id) {
        Movie movie = null;
        MovieEntity movieEntity = movieService.getMovieById(Long.valueOf(id));
        if(movieEntity != null){
            movie = movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity);
        }
        return movie;
    }

    @Override
    public void addMovie(Movie movie) {
        MovieEntity movieEntity = movieToMovieEntityMapper.mapMovieToMovieEntity(movie);
        movieService.saveMovie(movieEntity);
        messageService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
    }

    @Override
    public void removeMovieById(int id) {
        movieService.deleteMovie(Long.valueOf(id));
    }
}
