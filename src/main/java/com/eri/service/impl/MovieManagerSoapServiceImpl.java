package com.eri.service.impl;

import com.eri.converter.mapstruct.IMovieMapper;
import com.eri.exception.MovieNotFoundException;
import com.eri.generated.movieapi.stub.*;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.soap.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service("movieManagerSoapService")
public class MovieManagerSoapServiceImpl implements IMovieManagerService {

    @Autowired
    private SoapClient soapClient;

    @Value("${movie-api.soap.default-uri}")
    private String url;

    @Autowired
    IMovieMapper movieMapper;

    @Override
    public List<Movie> getMovies() {
        return listMovies();
    }

    @Override
    public Movie findMovieById(int id) {
        Movie movie = getMovieById(id).stream().findFirst().orElse(null);
        if(movie == null){
            throw new MovieNotFoundException();
        }
        return movie;
    }

    @Override
    public void addMovie(Movie movie) {
        AddMovieRequest addMovieRequest = new AddMovieRequest();
        addMovieRequest.setMovie(movieMapper.modelToGenerated(movie));
        soapClient.addMovie(url, addMovieRequest);
    }

    @Override
    public void removeMovieById(int id) {
        DeleteMovieRequest deleteMovieRequest = new DeleteMovieRequest();
        deleteMovieRequest.setId(BigInteger.valueOf(id));
        soapClient.addMovie(url, deleteMovieRequest);
    }

    private List<Movie> listMovies(){
        return getMovieById(null);
    }

    private List<Movie> getMovieById(Integer id){
        List<Movie> movies = new ArrayList<>();
        ListMoviesRequest request = new ListMoviesRequest();
        request.setId(id == null ? null : BigInteger.valueOf(id));
        ListMoviesResponse response = soapClient.listMovies(url, request);
        for(com.eri.generated.movieapi.stub.Movie movie : response.getMovies()){
            movies.add(movieMapper.generatedToModel(movie));
        }
        return movies;
    }
}
