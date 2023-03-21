package com.hryszkiewicz.moviebase.rate;

import com.hryszkiewicz.moviebase.rate.RateDTO;
import com.hryszkiewicz.moviebase.rate.RateMapper;
import com.hryszkiewicz.moviebase.rate.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class RateController {

    private final RateService rateService;

    private final RateMapper rateMapper;

    @PostMapping("/movie/{movieId}/rate")
    public ResponseEntity<RateDTO> addRate(@RequestBody RateDTO rateDTO, @PathVariable String movieId) {
        log.info("Creating rate for movie '{}'.", movieId);
        var rate = rateMapper.toEntity(rateDTO);
        return new ResponseEntity<>(rateMapper.toDTO(rateService.addRate(rate, movieId)), HttpStatus.CREATED);
    }

    @DeleteMapping("/movie/{movieId}/rate/{rateId}")
    public ResponseEntity<Void> deleteRate(@PathVariable String movieId, @PathVariable String rateId) {
        log.info("Deleting rate '{}' for movie '{}'.", rateId, movieId);
        rateService.deleteRateById(rateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
