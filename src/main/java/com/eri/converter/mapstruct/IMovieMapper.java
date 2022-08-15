package com.eri.converter.mapstruct;

import com.eri.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMovieMapper {
    Movie generatedToModel(com.eri.generated.movieapi.stub.Movie generated);
    com.eri.generated.movieapi.stub.Movie modelToGenerated(Movie model);
}
