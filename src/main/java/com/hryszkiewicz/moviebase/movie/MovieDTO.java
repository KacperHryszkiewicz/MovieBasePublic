package com.hryszkiewicz.moviebase.movie;

import com.hryszkiewicz.moviebase.award.AwardDTO;
import com.hryszkiewicz.moviebase.rate.RateDTO;
import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {
    private String movieId;

    private String title;

    private int releaseYear;

    private MovieCategory category;

    private String description;

    private List<RateDTO> rates;

    private Double avgRate;

    private List<AwardDTO> awards;
}
