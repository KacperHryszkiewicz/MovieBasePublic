package com.hryszkiewicz.moviebase.movie;

import com.hryszkiewicz.moviebase.award.AwardDTO;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.rate.Rate;
import com.hryszkiewicz.moviebase.rate.RateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    public MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            throw new BadRequestException("Movie entity cannot be null.");
        }
        var movieDTO = new MovieDTO();
        BeanUtils.copyProperties(movie, movieDTO);

        convertRatesToDTOs(movie, movieDTO);
        movieDTO.setAvgRate(calculateAverageRate(movie.getRates()));
        convertAwardsToDTOs(movie, movieDTO);
        return movieDTO;
    }


    public Movie toEntity(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new BadRequestException("Movie DTO cannot be null.");
        }
        var movie = new Movie();
        BeanUtils.copyProperties(movieDTO, movie);
        return movie;
    }

    public List<MovieDTO> toDTO(List<Movie> movies) {
        return movies
                .stream()
                .map(movie -> toDTO(movie))
                .toList();
    }

    private void convertAwardsToDTOs(Movie movie, MovieDTO movieDTO) {
        var awards = movie.getAwards();
        if (!CollectionUtils.isEmpty(awards)) {
            var awardDTOs =
                    awards
                            .stream()
                            .map(award -> new AwardDTO(award.getAwardId(), award.getName()))
                            .toList();
            movieDTO.setAwards(awardDTOs);
        }
    }

    private void convertRatesToDTOs(Movie movie, MovieDTO movieDTO) {
        if (!CollectionUtils.isEmpty(movie.getRates())) {
            var rateDTOs =
                    movie.getRates()
                            .stream()
                            .map(rate -> new RateDTO(rate.getRateId(), rate.getRateValue(), rate.getAuthor(), rate.getComment()))
                            .toList();
            movieDTO.setRates(rateDTOs);
        }
    }

    private Double calculateAverageRate(List<Rate> rates) {

        if (rates == null) {
            return 0.0;
        }
        return rates
                .stream()
                .mapToDouble((Rate::getRateValue))
                .average().orElse(0.0);

    }

}
