package com.hryszkiewicz.moviebase.movie;

import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.movie.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String phrase);

    List<Movie> findByCategory(MovieCategory category);

    List<Movie> findByCategoryAndTitleContainingIgnoreCase(MovieCategory category, String phrase);

    Movie findByMovieId(String id);

    void deleteByMovieId(String id);
}
