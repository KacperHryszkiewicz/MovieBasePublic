package com.hryszkiewicz.moviebase.movie;

import com.hryszkiewicz.moviebase.award.Award;
import com.hryszkiewicz.moviebase.rate.Rate;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int releaseYear;

    @Column(nullable = false)
    private MovieCategory category;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private List<Rate> rates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private List<Award> awards = new ArrayList<>();

}