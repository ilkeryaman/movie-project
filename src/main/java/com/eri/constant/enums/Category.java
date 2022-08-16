package com.eri.constant.enums;

public enum Category {
    ACTION("action"),
    ADVENTURE("adventure"),
    CRIME("crime"),
    DRAMA("drama"),
    FANTASY("fantasy"),
    SCI_FI("sci-fi");

    Category(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
