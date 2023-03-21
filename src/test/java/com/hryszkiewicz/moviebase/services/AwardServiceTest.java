package com.hryszkiewicz.moviebase.services;

import com.hryszkiewicz.moviebase.award.Award;
import com.hryszkiewicz.moviebase.award.AwardService;
import com.hryszkiewicz.moviebase.exception.NotFoundException;
import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.award.AwardRepository;
import com.hryszkiewicz.moviebase.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {

    private AwardService service;

    @Mock
    private MovieService movieService;

    @Mock
    private AwardRepository awardRepository;


    @BeforeEach
    public void setup() {
        service = new AwardService(awardRepository, movieService);
    }

    @Test
    void shouldAddAward() {
        //given
        var award = new Award("name");
        var movieId = "test";
        var movie = new Movie();

        when(movieService.getMovieByMovieId(movieId)).thenReturn(movie);
        when(awardRepository.save(award)).thenReturn(award);

        //when
        var createdAward = service.addAward(award, movieId);

        //then
        assertThat(createdAward).isNotNull();
        verify(awardRepository).save(award);
    }

    @Test
    void shouldThrowsExceptionWhenNameIsEmpty() {
        //given
        var award = new Award("");
        var id = "id";

        //when
        var throwable = catchThrowable(() -> service.addAward(award, id));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Award name is mandatory.");
    }

    @Test
    void shouldDeleteAward() {
        //given
        var id = "id";
        var awardToDelete = new Award("Name");
        when(awardRepository.findByAwardId(id)).thenReturn(of(awardToDelete));

        //when
        service.deleteAward(id);

        //then
        verify(awardRepository).findByAwardId(id);
        verify(awardRepository).delete(awardToDelete);
    }

    @Test
    void shouldThrowsExceptionWhenAwardWithIdDoesNotExist() {
        //given
        var id = "id";
        var awardToDelete = new Award("Name");
        when(awardRepository.findByAwardId(id)).thenReturn(empty());

        //when
        var throwable = catchThrowable(() -> service.deleteAward(id));

        //then
        assertThat(throwable).isNotNull();

        var ex = (NotFoundException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Award with id '" + id + "' does not exists.");
        verify(awardRepository, never()).delete(any());
    }
}