package com.eri.constants.enums;

public enum TestData {
    MOVIE_NAME("The Test"),
    DIRECTOR_NAME("Name of director"),
    DIRECTOR_SURNAME("Surname of director"),
    STAR_NAME("Name of star"),
    STAR_SURNAME("Surname of star");

    TestData(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }
}
