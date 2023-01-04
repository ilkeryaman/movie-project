package com.eri.dal.service.impl;

import com.eri.dal.entity.MovieEntity;
import com.eri.dal.repository.MovieRepository;
import com.eri.dal.service.IMovieService;
import com.eri.exception.NullTitleException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Resource
    private MovieRepository movieRepository;

    @Override
    public MovieEntity saveMovie(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }

    @Override
    public List<MovieEntity> getMovieList() {
        return (List<MovieEntity>) movieRepository.findAll();
    }

    @Override
    public MovieEntity getMovieById(Long movieId) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(movieId);
        return movieEntity.isPresent() ? movieEntity.get() : null;
    }

    @Override
    public MovieEntity updateMovie(MovieEntity movieEntity, Long movieId) {
        MovieEntity movieAtDB = movieRepository.findById(movieId).get();

        if (Objects.nonNull(movieEntity.getTitle()) && !"".equalsIgnoreCase(movieEntity.getTitle())) {
            movieAtDB.setTitle(movieEntity.getTitle());
        } else {
            throw new NullTitleException();
        }

        movieAtDB.getCategories().clear();
        movieAtDB.getCategories().addAll(movieEntity.getCategories());
        movieAtDB.getDirectors().clear();
        movieAtDB.getDirectors().addAll(movieEntity.getDirectors());
        movieAtDB.getStars().clear();
        movieAtDB.getStars().addAll(movieEntity.getStars());

        return movieRepository.save(movieAtDB);
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
