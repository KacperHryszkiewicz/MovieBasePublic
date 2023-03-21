package com.hryszkiewicz.moviebase.award;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AwardController {

    private final AwardService awardService;

    private final AwardMapper awardMapper;

    @PostMapping("/movie/{movieId}/award")
    public ResponseEntity<AwardDTO> addAward(@RequestBody AwardDTO awardDTO, @PathVariable String movieId) {
        log.info("Creating award for movie '{}'.", movieId);
        var award = awardMapper.toEntity(awardDTO);
        return new ResponseEntity<>(awardMapper.toDTO(awardService.addAward(award, movieId)), HttpStatus.CREATED);
    }

    @DeleteMapping("/movie/{movieId}/award/{awardId}")
    public ResponseEntity<Void> deleteAward(@PathVariable String movieId, @PathVariable String awardId) {
        log.info("Deleting award '{}' for movie '{}'.", awardId, movieId);
        awardService.deleteAward(awardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
