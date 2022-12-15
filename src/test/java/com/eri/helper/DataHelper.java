package com.eri.helper;

import java.util.List;

public abstract class DataHelper<T> {

    protected String pathName;
    protected List<T> movies;

    public DataHelper(){
        pathName = "src/test/resources/data/movies.json";
        setMovies();
    }

    public T getMovie(){
        return movies.get(0);
    }

    public List<T> getMovieList(){
        return movies;
    }

    abstract void setMovies();
}
