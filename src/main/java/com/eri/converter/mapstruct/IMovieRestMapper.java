package com.eri.converter.mapstruct;

import com.eri.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMovieRestMapper {
    Movie generatedToModel(com.eri.swagger.movie_api.model.Movie generated);
    com.eri.swagger.movie_api.model.Movie modelToGenerated(Movie model);
}
