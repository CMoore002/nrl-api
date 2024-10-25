package com.example.nrl_api.prediction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, CompositeKey> {

    List<Prediction> findByVenue(String venue);
    List<Prediction> findByDate(Date date);
    List<Prediction> findByAwayTeam(String awayTeam);
    List<Prediction> findByIdHomeTeam(String homeTeam);
    List<Prediction> findByIdRound(String round);
    List<Prediction> findByIdSeason(Integer season);

}
