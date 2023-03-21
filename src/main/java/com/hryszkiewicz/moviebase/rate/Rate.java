package com.hryszkiewicz.moviebase.rate;

import com.hryszkiewicz.moviebase.movie.Movie;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String rateId;

    @Column(nullable = false)
    private int rateValue;

    private String author;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Movie.class)
    private Movie movie;

    public static Rate createRate(int rateValue, String author, String comment) {
        var rate = new Rate();
        rate.setRateValue(rateValue);
        rate.setAuthor(author);
        rate.setComment(comment);
        return rate;
    }
}
