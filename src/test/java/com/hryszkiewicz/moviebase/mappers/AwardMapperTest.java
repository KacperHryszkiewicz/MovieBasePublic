package com.hryszkiewicz.moviebase.mappers;

import com.hryszkiewicz.moviebase.award.AwardDTO;
import com.hryszkiewicz.moviebase.award.Award;
import com.hryszkiewicz.moviebase.award.AwardMapper;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AwardMapperTest {

    private AwardMapper awardMapper;

    @BeforeEach
    public void setup() {
        awardMapper = new AwardMapper();
    }

    @Test
    void shouldMapAwardEntityToDTO() {
        //given
        var name = "name";
        var award = new Award(name);

        //when
        var awardDTO = awardMapper.toDTO(award);

        //then
        var expectedResult = new AwardDTO(name);
        assertEquals(expectedResult, awardDTO);
    }

    @Test
    void shouldThrowExceptionWhenAwardIsNull() {
        //given
        Award award = null;

        //when
        var throwable = catchThrowable(() -> awardMapper.toDTO(award));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Award entity cannot be null.");
    }

    @Test
    void shouldMapAwardDTOToEntity() {
        //given
        var name = "name";
        var awardDTO = new AwardDTO(name);

        //when
        var award = awardMapper.toEntity(awardDTO);

        //then
        var expectedResult = new Award(name);
        assertEquals(expectedResult, award);
    }

    @Test
    void shouldThrowExceptionWhenAwardDTOIsNull() {
        //given
        AwardDTO awardDTO = null;

        //when
        var throwable = catchThrowable(() -> awardMapper.toEntity(awardDTO));

        //then
        assertThat(throwable).isNotNull();

        var ex = (BadRequestException) throwable;

        assertThat(ex.getMessage()).isEqualTo("Award DTO cannot be null.");
    }

}