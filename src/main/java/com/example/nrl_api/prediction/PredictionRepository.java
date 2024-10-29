package com.example.nrl_api.prediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, CompositeKey> {


    @Query("SELECT p FROM Prediction p WHERE p.id.season = " +
            "(SELECT MAX(p2.id.season) FROM Prediction p2)")
    List<Prediction> findTopByOrderBySeasonAndRoundDesc();
    List<Prediction> findByAwayTeam(String awayTeam);
    List<Prediction> findByIdHomeTeam(String homeTeam);
    List<Prediction> findByIdSeasonAndIdRound(Integer season, String round);
    List<Prediction> findByIdSeason(Integer season);

}
