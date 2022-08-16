package com.eri.constant.enums;

public enum ErrorMessage {
    MOVIE_NOT_FOUND("Movie not found!");

    ErrorMessage(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }
}
