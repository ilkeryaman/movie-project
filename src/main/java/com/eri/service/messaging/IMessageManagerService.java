package com.eri.service.messaging;

import com.eri.model.Movie;

public interface IMessageManagerService {
    void addMovieToNewcomersCache(Movie movie);
}
