package com.hryszkiewicz.moviebase.movie;

import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.movie.MovieCategory;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public Movie addMovie(Movie movie) {
        if (!StringUtils.hasText(movie.getTitle())) {
            throw new BadRequestException("Movie title is mandatory");
        }
        if (movie.getCategory() == null) {
            throw new BadRequestException("Movie category is mandatory");
        }
        movie.setMovieId(UUID.randomUUID().toString());
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(MovieCategory category, String phrase) {
        List<Movie> movieList;
        if ((category != null) && (phrase != null)) {
            movieList = movieRepository.findByCategoryAndTitleContainingIgnoreCase(category, phrase);
        } else if (category != null) {
            movieList = movieRepository.findByCategory(category);
        } else if (phrase != null) {
            movieList = movieRepository.findByTitleContainingIgnoreCase(phrase);
        } else {
            movieList = movieRepository.findAll();
        }
        return movieList;
    }

    public Movie getMovieByMovieId(String movieId) {
        return movieRepository.findByMovieId(movieId);
    }

    @Transactional
    public Movie editMovie(String id, Movie movie) {
        var movieToEdit = getMovieByMovieId(id);

        if (StringUtils.hasText(movie.getTitle())) {
            movieToEdit.setTitle(movie.getTitle());
        }
        if (movie.getReleaseYear() != 0) {
            movieToEdit.setReleaseYear(movie.getReleaseYear());
        }
        if (StringUtils.hasText(movie.getDescription())) {
            movieToEdit.setDescription(movie.getDescription());
        }
        if (movie.getCategory() != null) {
            movieToEdit.setCategory(movie.getCategory());
        }
        return movieRepository.save(movieToEdit);
    }

    @Transactional
    public void deleteMovie(String id) {

        movieRepository.deleteByMovieId(id);
    }
}
