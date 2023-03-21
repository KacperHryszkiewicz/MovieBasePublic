package com.hryszkiewicz.moviebase.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Optional<Rate> findByRateId(String id);
}
