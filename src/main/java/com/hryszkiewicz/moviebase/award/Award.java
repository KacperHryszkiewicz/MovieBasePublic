package com.hryszkiewicz.moviebase.award;

import com.hryszkiewicz.moviebase.movie.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "awards")
@Data
@NoArgsConstructor
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String awardId;

    @Column(nullable = false)
    private String name;

    public Award(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Movie.class)
    private Movie movie;
}
