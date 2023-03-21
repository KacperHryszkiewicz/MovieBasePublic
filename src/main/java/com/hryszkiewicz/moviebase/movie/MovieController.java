package com.hryszkiewicz.moviebase.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        log.info("Creating new movie.");
        var movie = movieMapper.toEntity(movieDTO);
        return new ResponseEntity<>(movieMapper.toDTO(movieService.addMovie(movie)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies(@RequestParam(required = false) MovieCategory category, @RequestParam(required = false) String phrase) {
        log.info("Getting all movies.");
        var movies = movieService.getAllMovies(category, phrase);
        return new ResponseEntity<>(movieMapper.toDTO(movies), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String id) {
        log.info("Getting movie by id '{}'.", id);
        var movie = movieService.getMovieByMovieId(id);
        return new ResponseEntity<>(movieMapper.toDTO(movie), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> editMovie(@PathVariable String id, @RequestBody MovieDTO movie) {
        log.info("Editing movie by id '{}'.", id);
        var updatedMovie = movieService.editMovie(id, movieMapper.toEntity(movie));
        return new ResponseEntity<>(movieMapper.toDTO(updatedMovie), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        log.info("Deleting movie by id '{}'.", id);
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
