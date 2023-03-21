package com.hryszkiewicz.moviebase;

import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.movie.MovieCategory;

public class TestUtils {


    public static Movie createMovie(String title, int releaseYear, MovieCategory category, String description) {
        var movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setCategory(category);
        movie.setDescription(description);
        return movie;
    }
}
