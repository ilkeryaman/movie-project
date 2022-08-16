package com.eri.model;

import com.eri.validation.CategoryValidation;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Movie {

    @Min(value = 1, message = "id is mandatory and must be bigger than 0!")
    private int id;

    @NotBlank(message = "title is mandatory!")
    private String title;

    @Valid
    @NotEmpty(message = "at least one category is required!")
    @CategoryValidation
    private List<String> categories;

    @Valid
    @NotEmpty(message = "at least one director is required!")
    private List<Director> directors;

    @Valid
    @NotEmpty(message = "at least one star is required!")
    private List<Star> stars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }
}
