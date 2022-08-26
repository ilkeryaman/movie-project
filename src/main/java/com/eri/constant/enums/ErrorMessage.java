package com.eri.constant.enums;

public enum ErrorMessage {
    MOVIE_NOT_FOUND("Movie not found!"),
    CACHE_INITIALIZATION_PROBLEM("Cache initialization problem!"),
    INTERNAL_SERVER_ERROR("Internal server error!");

    ErrorMessage(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }
}
