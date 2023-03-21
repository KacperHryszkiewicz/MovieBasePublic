package com.hryszkiewicz.moviebase.services;

import com.hryszkiewicz.moviebase.award.Award;
import com.hryszkiewicz.moviebase.exception.NotFoundException;
import com.hryszkiewicz.moviebase.movie.Movie;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.movie.MovieService;
import com.hryszkiewicz.moviebase.rate.RateService;
import com.hryszkiewicz.moviebase.rate.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hryszkiewicz.moviebase.rate.Rate.createRate;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    private RateService rateService;

    @Mock
    private RateRepository rateRepository;

    @Mock
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        rateService = new RateService(rateRepository, movieService);
    }

    @Test
    void shouldAddRate() {
        //given
        var rate = createRate(7, "author", "comment");
        when(rateRepository.save(rate)).thenReturn(rate);
        var movieId = "id";
        var movie = new Movie();
        when(movieService.getMovieByMovieId(movieId)).thenReturn(movie);

        //when
        var createdRate = rateService.addRate(rate, movieId);

        //then
        assertThat(createdRate).isNotNull();
        verify(rateRepository).save(rate);
    }

    @Test
    void shouldThrowsExceptionWhenRateValueIsNotInRange() {
        //given
        var rate = createRate(13, "author", "comment");
        var id = "id";

        //when
        var throwable = catchThrowable(() -> rateService.addRate(rate,id));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Rate value must be in range from 0 to 10.");
    }

    @Test
    void shouldThrowsExceptionWhenAuthorIsEmpty() {
        //given
        var rate = createRate(3, "", "comment");
        var id = "id";

        //when
        var throwable = catchThrowable(() -> rateService.addRate(rate,id));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Author is mandatory.");
    }


    @Test
    void shouldDeleteRate(){
        //given
        var serviceId = "id";
        var rateToDelete = createRate(7, "author", "comment");
        when(rateRepository.findByRateId(serviceId)).thenReturn(java.util.Optional.of(rateToDelete));

        //when
        rateService.deleteRateById(serviceId);

        //then
        verify(rateRepository).findByRateId(serviceId);
        verify(rateRepository).delete(rateToDelete);
    }

    @Test
    void shouldThrowsExceptionWhenRatedWithIdDoesNotExist() {
        //given
        var id = "id";
        var awardToDelete = new Award("Name");
        when(rateRepository.findByRateId(id)).thenReturn(empty());

        //when
        var throwable = catchThrowable(() -> rateService.deleteRateById(id));

        //then
        assertThat(throwable).isNotNull();

        var ex = (NotFoundException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Rate with id '" + id + "' does not exists.");
        verify(rateRepository, never()).delete(any());
    }

}