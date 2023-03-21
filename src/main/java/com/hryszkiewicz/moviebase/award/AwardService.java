package com.hryszkiewicz.moviebase.award;

import com.hryszkiewicz.moviebase.exception.BadRequestException;
import com.hryszkiewicz.moviebase.exception.NotFoundException;
import com.hryszkiewicz.moviebase.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    private final MovieService movieService;

    public Award addAward(Award award, String movieId) {
        if (!StringUtils.hasText(award.getName())) {
            throw new BadRequestException("Award name is mandatory.");
        }
        award.setAwardId(UUID.randomUUID().toString());
        var movie = movieService.getMovieByMovieId(movieId);
        award.setMovie(movie);
        return awardRepository.save(award);
    }

    public void deleteAward(String awardId) {
        var awardToDelete = awardRepository
                .findByAwardId(awardId)
                .orElseThrow(() -> new NotFoundException("Award with id '" + awardId + "' does not exists."));
        awardRepository.delete(awardToDelete);
    }

}
