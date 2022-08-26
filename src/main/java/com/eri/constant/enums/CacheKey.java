package com.eri.constant.enums;

public enum CacheKey {
    MOVIES("movies");

    CacheKey(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
