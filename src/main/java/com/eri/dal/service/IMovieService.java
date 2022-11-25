package com.eri.dal.service;

import com.eri.dal.entity.MovieEntity;

import java.util.List;

public interface IMovieService {
    MovieEntity saveMovie(MovieEntity movieEntity);

    List<MovieEntity> getMovieList();

    MovieEntity getMovieById(Long movieId);

    MovieEntity updateMovie(MovieEntity movieEntity, Long movieId);

    void deleteMovie(Long movieId);
}
