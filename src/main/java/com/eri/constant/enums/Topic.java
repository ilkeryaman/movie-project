package com.eri.constant.enums;

public enum Topic {
    MOVIEDB_MOVIE_CREATED("moviedb.movie.created");

    Topic(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
