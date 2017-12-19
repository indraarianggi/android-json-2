package com.indraarianggi.moviedb.model;

/**
 * Created by indraarianggi on 20/12/17.
 */

public class Movie {

    private String posterPath;
    private String title;
    private String releaseDate;

    public Movie(String posterPath, String title, String releaseDate) {
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
