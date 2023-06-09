package com.hryszkiewicz.moviebase.award;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    Optional<Award> findByAwardId(String id);
}
