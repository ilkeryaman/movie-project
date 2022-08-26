package com.eri.controller;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movie-api")
public class MovieController {

    @Resource(name="movieManagerWebClientService")
    IMovieManagerService movieManagerService;

    @Operation(summary = "Lists all movies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/movies")
    public List<Movie> listMovies(@RequestParam(required = false) Integer id, @RequestParam(required = false) boolean fromCache){
        if(id != null){
            Movie movie = movieManagerService.findMovieById(id);
            return movie == null ? Collections.emptyList() : Arrays.asList(movie);
        }
        return movieManagerService.getMovies(fromCache);
    }

    @Operation(summary = "Gets movie by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable("id") Integer id){
        return movieManagerService.findMovieById(id);
    }

    @Operation(summary = "Adds a new movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@Valid @RequestBody Movie movie){
        movieManagerService.addMovie(movie);
    }

    @Operation(summary = "Deletes a movie by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") Integer id){
        movieManagerService.removeMovieById(id);
    }
}
