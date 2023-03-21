package com.hryszkiewicz.moviebase.rate;

import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.exception.NotFoundException;
import com.hryszkiewicz.moviebase.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    private final MovieService movieService;

    public Rate addRate(Rate rate, String movieId) {
        if ((rate.getRateValue() < 0) || (rate.getRateValue() > 10)) {
            throw new BadRequestException("Rate value must be in range from 0 to 10.");
        }
        if (!StringUtils.hasText(rate.getAuthor())) {
            throw new BadRequestException("Author is mandatory.");
        }
        rate.setRateId(UUID.randomUUID().toString());
        var movie = movieService.getMovieByMovieId(movieId);
        rate.setMovie(movie);
        return rateRepository.save(rate);
    }

    public void deleteRateById(String rateId) {
        var rateToDelete = rateRepository
                .findByRateId(rateId)
                .orElseThrow(() -> new NotFoundException("Rate with id '" + rateId + "' does not exists."));
        rateRepository.delete(rateToDelete);
    }
}
