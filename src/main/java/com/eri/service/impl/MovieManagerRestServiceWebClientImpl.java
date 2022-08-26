package com.eri.service.impl;

import com.eri.converter.mapstruct.IMovieRestMapper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("movieManagerWebClientService")
public class MovieManagerRestServiceWebClientImpl implements IMovieManagerService {
    @Resource
    IMovieRestMapper mapper;

    private String remoteApiUri;

    private String getRemoteApiUri() {
        return remoteApiUri;
    }

    @Value("${movie-api.rest.uri}")
    private void setRemoteApiUri(String remoteApiUri) {
        this.remoteApiUri = remoteApiUri;
    }

    @Resource
    WebClient webClient;

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        Mono<com.eri.swagger.movie_api.model.Movie[]> movieResponse =
                webClient.get().uri(getRemoteApiUri()).retrieve().bodyToMono(com.eri.swagger.movie_api.model.Movie[].class);
        Arrays.asList(movieResponse.block()).forEach(movie -> movies.add(mapper.generatedToModel(movie)));
        return movies;
    }

    @Override
    public Movie findMovieById(int id) {
        Mono<com.eri.swagger.movie_api.model.Movie> movieResponse =
                webClient.get().uri(getRemoteApiUri() + "/" + id).retrieve().bodyToMono(com.eri.swagger.movie_api.model.Movie.class);
        return mapper.generatedToModel(movieResponse.block());
    }

    @Override
    public void addMovie(Movie movie) {
        webClient.post()
                .uri(getRemoteApiUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(mapper.modelToGenerated(movie)), com.eri.swagger.movie_api.model.Movie.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void removeMovieById(int id) {
        webClient.delete()
                .uri(getRemoteApiUri() + "/" +id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
