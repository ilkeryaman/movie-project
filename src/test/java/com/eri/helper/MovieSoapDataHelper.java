package com.eri.helper;

import com.eri.generated.movieapi.stub.ListMoviesResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovieSoapDataHelper extends DataHelper<com.eri.generated.movieapi.stub.Movie> {

    void setMovies(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            movies = objectMapper.readValue(new File(pathName), new TypeReference<List<com.eri.generated.movieapi.stub.Movie>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListMoviesResponse getMovieResponse(){
        ListMoviesResponse response = new ListMoviesResponse();
        response.getMovies().add(getMovie());
        return response;
    }

    public ListMoviesResponse getMovieListResponse(){
        ListMoviesResponse response = new ListMoviesResponse();
        response.getMovies().addAll(getMovieList());
        return response;
    }
}
