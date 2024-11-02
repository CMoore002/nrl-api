package com.example.nrl_api.prediction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PredictionRepositoryTest {

    @Autowired PredictionRepository predictionRepository;

    @Test
    public void testFindByIdSeasonAndIdRound(){
        // Creating mock composite key
        CompositeKey key = new CompositeKey(2024, "Round 5", "Sharks");
        Prediction prediction = new Prediction();
        prediction.setId(key);

        // Retrieving results
        List<Prediction> result = predictionRepository.findByIdSeasonAndIdRound(prediction.getId().getSeason(), prediction.getId().getRound());

        // Verifying
        assertThat(result).hasSize(8);
        assertThat(result.getFirst().getId().getSeason()).isEqualTo(2024);
        assertThat(result.getFirst().getId().getRound()).isEqualTo("Round 5");
    }

    @Test
    public void testFindByIdSeasonAndIdRound_NoResults(){
        // Creating mock composite key
        CompositeKey key = new CompositeKey(2050, "Round 5", "Sharks");
        Prediction prediction = new Prediction();
        prediction.setId(key);

        // Retrieving results
        List<Prediction> result = predictionRepository.findByIdSeasonAndIdRound(prediction.getId().getSeason(), prediction.getId().getRound());

        // Verifying
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindTopByOrderBySeasonAndRoundDesc(){
        // This should always return a non-empty list
        List<Prediction> result = predictionRepository.findTopByOrderBySeasonAndRoundDesc();

        // Verifying
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindByIdSeason(){
        // Retrieving results
        List<Prediction> result = predictionRepository.findByIdSeason(2024);

        // Verifying
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(prediction -> prediction.getId().getSeason().equals(2024));
    }

    @Test
    public void testFindByIdSeason_NoResults(){
        // Retrieving results
        List<Prediction> result = predictionRepository.findByIdSeason(2050);

        // Verifying
        assertThat(result).isEmpty();
    }

    // TODO: add tests for home team and away team endpoints
}
