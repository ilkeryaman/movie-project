package com.eri.constant.enums;

public enum ErrorMessage {
    MOVIE_NOT_FOUND("Movie not found!"),
    CACHE_INITIALIZATION_PROBLEM("Cache initialization problem!"),

    TITLE_IS_REQUIRED("Title is required!"),
    CATEGORY_NAME_IS_REQUIRED("Name of category is required!"),
    NAME_IS_REQUIRED("Name is required!"),
    SURNAME_IS_REQUIRED("Surname is required!"),
    INTERNAL_SERVER_ERROR("Internal server error!");

    ErrorMessage(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }
}
