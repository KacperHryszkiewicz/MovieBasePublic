package com.hryszkiewicz.moviebase.mappers;

import com.hryszkiewicz.moviebase.rate.RateDTO;
import com.hryszkiewicz.moviebase.rate.Rate;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.rate.RateMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.hryszkiewicz.moviebase.rate.Rate.createRate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class RateMapperTest {

    private RateMapper rateMapper;

    @BeforeEach
    public void setup() {
        rateMapper = new RateMapper();
    }

    @Test
    void shouldMapRateEntityToDTO() {
        //given
        var rateValue = 7;
        var author = "author";
        var comment = "comment";
        var rate = createRate(7, "author", "comment");

        //when
        var rateDTO = rateMapper.toDTO(rate);

        //then
        var expectedResult = createRateDTO(rateValue, author, comment);
        Assertions.assertEquals(expectedResult, rateDTO);
    }

    @Test
    void shouldThrowExceptionWhenRateIsNull() {
        //given
        Rate rate = null;

        //when
        var throwable = catchThrowable(() -> rateMapper.toDTO(rate));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Rate entity cannot be null.");
    }

    @Test
    void shouldMapRateDTOToEntity() {
        //given
        var rateValue = 7;
        var author = "author";
        var comment = "comment";
        var rateDTO = createRateDTO(7, "author", "comment");

        //when
        var rate = rateMapper.toEntity(rateDTO);

        //then
        var expectedResult = createRate(rateValue, author, comment);
        Assertions.assertEquals(expectedResult, rate);
    }

    @Test
    void shouldThrowExceptionWhenRateDTOIsNull() {
        //given
        RateDTO rateDTO = null;

        //when
        var throwable = catchThrowable(() -> rateMapper.toEntity(rateDTO));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Rate DTO cannot be null.");
    }

    private RateDTO createRateDTO(int rateValue, String author, String comment) {
        var rate = new RateDTO();
        rate.setRateValue(rateValue);
        rate.setAuthor(author);
        rate.setComment(comment);
        return rate;
    }

}