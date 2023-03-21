package com.hryszkiewicz.moviebase.mappers;

import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.movie.MovieCategory;
import com.hryszkiewicz.moviebase.movie.MovieDTO;
import com.hryszkiewicz.moviebase.movie.MovieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.hryszkiewicz.moviebase.TestUtils.createMovie;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    public void setup() {
        movieMapper = new MovieMapper();
    }

    @Test
    void shouldMapMovieEntityToDTO() {
        //given
        var title = "title";
        var releaseYear = 2017;
        var category = MovieCategory.FANTASY;
        var description = "desc";
        var movie = createMovie(title, releaseYear, category, description);

        //when
        var movieDTO = movieMapper.toDTO(movie);

        //then
        var expectedResult = createMovieDTO(title, releaseYear, category, description);
        assertEquals(expectedResult, movieDTO);
    }

    @Test
    void shouldThrowExceptionWhenMovieIsNull() {
        //given
        Movie movie = null;

        //when
        var throwable = catchThrowable(() -> movieMapper.toDTO(movie));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Movie entity cannot be null.");
    }

    @Test
    void shouldMapMovieDTOToEntity() {
        //given
        var title = "title";
        var releaseYear = 2017;
        var category = MovieCategory.FANTASY;
        var description = "desc";
        var movieDTO = createMovieDTO(title, releaseYear, category, description);

        //when
        var movie = movieMapper.toEntity(movieDTO);

        //then
        var expectedResult = createMovie(title, releaseYear, category, description);
        assertEquals(expectedResult, movie);
    }

    @Test
    void shouldThrowExceptionWhenMovieDTOIsNull() {
        //given
        MovieDTO movieDTO = null;

        //when
        var throwable = catchThrowable(() -> movieMapper.toEntity(movieDTO));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Movie DTO cannot be null.");
    }

    @Test
    void shouldMapEntityListToDTOS() {
        //given
        var movieList = new ArrayList<Movie>();
        var title = "title";
        var releaseYear = 2017;
        var category = MovieCategory.FANTASY;
        var description = "desc";
        movieList.add(createMovie(title, releaseYear, category, description));

        //when
        var movieDTOList = movieMapper.toDTO(movieList);

        //then
        List<MovieDTO> expectedResult = new ArrayList<>();
        expectedResult.add(createMovieDTO(title, releaseYear, category, description));
        assertEquals(expectedResult, movieDTOList);

    }


    private static MovieDTO createMovieDTO(String title, int releaseYear, MovieCategory category, String description) {
        var movieDTO = new MovieDTO();
        movieDTO.setTitle(title);
        movieDTO.setReleaseYear(releaseYear);
        movieDTO.setCategory(category);
        movieDTO.setDescription(description);
        movieDTO.setAvgRate(0.0);
        return movieDTO;
    }
}