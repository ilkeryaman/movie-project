package com.eri.service.impl;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service("movieManagerRestTemplateService")
public class MovieManagerRestServiceRestTemplateImpl implements IMovieManagerService {

    @Resource(name = "movieRestTemplate")
    private RestTemplate movieRestTemplate;

    private String remoteApiUri;

    private String getRemoteApiUri() {
        return remoteApiUri;
    }

    @Value("${movie-api.rest.uri}")
    private void setRemoteApiUri(String remoteApiUri) {
        this.remoteApiUri = remoteApiUri;
    }

    @Override
    public List<Movie> getMovies() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<com.eri.swagger.movie_api.model.Movie[]> movies =
                movieRestTemplate.exchange(getRemoteApiUri(), HttpMethod.GET, entity, com.eri.swagger.movie_api.model.Movie[].class);
        return null;
    }

    @Override
    public Movie findMovieById(int id) {
        return null;
    }

    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void removeMovieById(int id) {

    }
}
