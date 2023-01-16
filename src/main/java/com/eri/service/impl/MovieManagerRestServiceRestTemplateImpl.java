package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.constant.enums.Topic;
import com.eri.converter.mapstruct.IMovieRestMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;
import com.eri.util.CacheUtil;
import com.eri.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("movieManagerRestTemplateService")
public class MovieManagerRestServiceRestTemplateImpl implements IMovieManagerService {
    @Resource
    private ICacheService cacheService;

    @Resource
    private IMovieRestMapper mapper;

    @Resource(name = "movieRestTemplate")
    private RestTemplate movieRestTemplate;

    @Resource
    private IMessageService messageService;

    private String remoteApiUri;

    private String getRemoteApiUri() {
        return remoteApiUri;
    }

    @Value("${movie-api.rest.uri}")
    private void setRemoteApiUri(String remoteApiUri) {
        this.remoteApiUri = remoteApiUri;
    }

    @Override
    public List<Movie> getMovies(boolean fromCache) {
        if(fromCache){
            List<Movie> cachedMovies = cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName());
            if(cachedMovies == null){
                throw new CacheNotInitializedException();
            }
            return cachedMovies;
        } else {
            List<Movie> movies = new ArrayList<>();
            ResponseEntity<com.eri.swagger.movie_api.model.Movie[]> movieResponse =
                    movieRestTemplate.exchange(getRemoteApiUri(), HttpMethod.GET, HttpUtil.getHttpEntity(), com.eri.swagger.movie_api.model.Movie[].class);
            Arrays.asList(movieResponse.getBody()).forEach(movie -> movies.add(mapper.generatedToModel(movie)));
            CacheUtil.cacheIfNeeded(cacheService, movies);
            return movies;
        }
    }

    @Override
    public List<Movie> listNewComers(){
        List<Movie> newComers = cacheService.findListFromCacheWithKey(CacheKey.NEWCOMERS.getName());
        return newComers == null ? Collections.emptyList() : newComers;
    }

    @Override
    public Movie findMovieById(int id) {
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> movieResponse =
                movieRestTemplate.exchange(getRemoteApiUri() + "/{id}", HttpMethod.GET, HttpUtil.getHttpEntity(), com.eri.swagger.movie_api.model.Movie.class, id);
        return mapper.generatedToModel(movieResponse.getBody());
    }

    @Override
    public void addMovie(Movie movie) {
        HttpEntity<com.eri.swagger.movie_api.model.Movie> entity = new HttpEntity<>(mapper.modelToGenerated(movie), HttpUtil.getDefaultHttpHeaders());
        ResponseEntity responseEntity = movieRestTemplate.exchange(getRemoteApiUri(), HttpMethod.POST, entity, com.eri.swagger.movie_api.model.Movie.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.CREATED)){
            messageService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
        }
    }

    @Override
    public void removeMovieById(int id) {
        movieRestTemplate.exchange(getRemoteApiUri() + "/{id}", HttpMethod.DELETE, HttpUtil.getHttpEntity(), Void.class, id);
    }
}
