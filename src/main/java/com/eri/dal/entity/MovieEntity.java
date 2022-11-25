package com.eri.dal.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "movie")
public class MovieEntity extends BaseEntity {
    private String title;

    @ManyToMany
    @JoinTable(
            name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<CategoryEntity> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    Set<DirectorEntity> directors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_star",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "star_id"))
    Set<StarEntity> stars;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public Set<DirectorEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorEntity> directors) {
        this.directors = directors;
    }

    public Set<StarEntity> getStars() {
        return stars;
    }

    public void setStars(Set<StarEntity> stars) {
        this.stars = stars;
    }
}
