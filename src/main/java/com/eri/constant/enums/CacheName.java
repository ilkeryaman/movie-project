package com.eri.constant.enums;

public enum CacheName {
    MOVIE_CACHE("movieCache");

    CacheName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}