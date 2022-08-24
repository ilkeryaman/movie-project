package com.eri.configuration.security.enums;

public enum Roles {
    MOVIE_LISTER("movie_lister"),
    MOVIE_CREATOR("movie_creator");
    Roles(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
