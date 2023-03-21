package com.hryszkiewicz.moviebase.services;

import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.movie.MovieCategory;
import com.hryszkiewicz.moviebase.movie.MovieRepository;
import com.hryszkiewicz.moviebase.movie.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.hryszkiewicz.moviebase.TestUtils.createMovie;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private MovieService service;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        service = new MovieService(movieRepository);
    }


    @Test
    void shouldAddMovie() {
        //given
        var movie = createMovie("title", 2020, MovieCategory.DRAMA, "desc");
        when(movieRepository.save(movie)).thenReturn(movie);

        //when
        var createdMovie = service.addMovie(movie);

        //then
        assertThat(createdMovie).isNotNull();
        verify(movieRepository).save(movie);
    }

    @Test
    void shouldThrowsExceptionWhenTitleIsNull() {
        //given
        var movie = createMovie(null, 2020, MovieCategory.DRAMA, "desc");

        //when
        var throwable = catchThrowable(() -> service.addMovie(movie));

        //then
        assertThat(throwable).isNotNull();
        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Movie title is mandatory");
    }

    @Test
    void shouldThrowsExceptionWhenTitleIsEmpty() {
        //given
        var movie = createMovie("   ", 2020, MovieCategory.DRAMA, "desc");

        //when
        var throwable = catchThrowable(() -> service.addMovie(movie));

        //then
        assertThat(throwable).isNotNull();
        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Movie title is mandatory");
    }

    @Test
    void shouldThrowsExceptionWhenCategoryIsEmpty() {
        //given
        var movie = createMovie("title", 2020, null, "desc");

        //when
        var throwable = catchThrowable(() -> service.addMovie(movie));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Movie category is mandatory");
    }

    @Test
    void shouldGetMovieByPhrase() {
        //given
        var movie = createMovie("title", 2020, null, "desc");
        var movieList = new ArrayList<Movie>();
        movieList.add(movie);
        var phrase = "title";

        when(movieRepository.findByTitleContainingIgnoreCase(phrase)).thenReturn(movieList);

        //when
        var foundMovieList = service.getAllMovies(null, phrase);

        //then
        assertThat(foundMovieList).isNotNull();
        verify(movieRepository).findByTitleContainingIgnoreCase(phrase);
    }

    @Test
    void shouldGetMovieByCategory() {
        //given
        var movie = createMovie("title", 2020, MovieCategory.DRAMA, "desc");
        var movieList = new ArrayList<Movie>();
        movieList.add(movie);
        var category = MovieCategory.DRAMA;

        when(movieRepository.findByCategory(category)).thenReturn(movieList);

        //when
        var foundMovieList = service.getAllMovies(category, null);

        //then
        assertThat(foundMovieList).isNotNull();
        verify(movieRepository).findByCategory(category);
    }

    @Test
    void shouldGetMovieByCategoryAndPhrase() {
        //given
        var movie = createMovie("title", 2020, MovieCategory.DRAMA, "desc");
        var movieList = new ArrayList<Movie>();
        movieList.add(movie);
        var category = MovieCategory.DRAMA;
        var phrase = "tit";

        when(movieRepository.findByCategoryAndTitleContainingIgnoreCase(category, phrase)).thenReturn(movieList);

        //when
        var foundMovieList = service.getAllMovies(category, phrase);

        //then
        assertThat(foundMovieList).isNotNull();
        verify(movieRepository).findByCategoryAndTitleContainingIgnoreCase(category, phrase);
    }

    @Test
    void shouldGetAllMovies() {
        //given
        var movie = createMovie("title", 2020, MovieCategory.DRAMA, "desc");
        var movieList = new ArrayList<Movie>();
        movieList.add(movie);

        when(movieRepository.findAll()).thenReturn(movieList);

        //when
        var foundListMovies = service.getAllMovies(null, null);

        //then
        assertThat(foundListMovies).isNotNull();
        verify(movieRepository).findAll();
    }


    @Test
    void shouldGetMovieById() {
        //given
        var movie = createMovie("title", 2020, null, "desc");
        var movieId = "id";
        when(movieRepository.findByMovieId(movieId)).thenReturn(movie);

        //when
        var foundMovie = service.getMovieByMovieId(movieId);

        //then
        assertThat(foundMovie).isNotNull();
        verify(movieRepository).findByMovieId(movieId);
    }

    @Test
    void shouldEditMovie() {
        //given
        var movieBeforeEdit = createMovie("title", 2020, MovieCategory.DRAMA, "desc");
        var movieAfterEdit = createMovie("titleAfter", 2021, MovieCategory.DRAMA, "after");
        var movieId = "id";
        when(movieRepository.save(movieBeforeEdit)).thenReturn(movieAfterEdit);
        when(movieRepository.findByMovieId(movieId)).thenReturn(movieBeforeEdit);

        //when
        var movieToEdit = service.editMovie(movieId, movieAfterEdit);

        //then
        Assertions.assertEquals(movieAfterEdit, movieToEdit );
        assertThat(movieToEdit).isNotNull();
        verify(movieRepository).save(movieBeforeEdit);
    }

    @Test
    void shouldDeleteMovie() {
        //given
        var id = "id";

        //then
        service.deleteMovie(id);

        //then
        verify(movieRepository).deleteByMovieId(id);
    }
}