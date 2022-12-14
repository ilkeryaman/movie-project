package com.eri.exception.handler;

import com.eri.constant.enums.ErrorMessage;
import com.eri.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCacheNotInitializedExceptions(NullTitleException ex) {
        return new ResponseEntity<>(ErrorMessage.TITLE_IS_REQUIRED.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullCategoryNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCacheNotInitializedExceptions(NullCategoryNameException ex) {
        return new ResponseEntity<>(ErrorMessage.CATEGORY_NAME_IS_REQUIRED.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCacheNotInitializedExceptions(NullNameException ex) {
        return new ResponseEntity<>(ErrorMessage.NAME_IS_REQUIRED.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullSurnameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCacheNotInitializedExceptions(NullSurnameException ex) {
        return new ResponseEntity<>(ErrorMessage.SURNAME_IS_REQUIRED.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleMovieNotFoundExceptions(MovieNotFoundException ex) {
        return new ResponseEntity<>(ErrorMessage.MOVIE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CacheNotInitializedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleCacheNotInitializedExceptions(CacheNotInitializedException ex) {
        return new ResponseEntity<>(ErrorMessage.CACHE_INITIALIZATION_PROBLEM.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
