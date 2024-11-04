package com.example.nrl_api.prediction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class PredictionServiceTest {
    @Mock
    private PredictionRepository predictionRepository;

    @InjectMocks
    private PredictionService predictionService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPredictionsBySeasonAndRound_Results(){
        // Set up composite key
        CompositeKey key = new CompositeKey(2024, "Grand Final", "Storm");
        Date date = Date.from(LocalDate.of(2024, 10, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Prediction prediction = new Prediction();
        prediction.setId(key);
        prediction.setAwayTeam("Panthers");
        prediction.setVenue("Accor Stadium");
        prediction.setPrediction(1);
        prediction.setHomeScore(24);
        prediction.setAwayScore(20);
        prediction.setMargin(4);
        prediction.setDate(date);
        List<Prediction> mockPredictions = List.of(prediction);

        // Defining behaviour
        when(predictionRepository.findByIdSeasonAndIdRound(2024, "Grand Final")).thenReturn(mockPredictions);

        // Calling service method
        List<Prediction> result = predictionService.getPredictionsByIdSeasonAndIdRound(prediction.getId().getSeason(), prediction.getId().getRound());

        // Verify results
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId().getSeason()).isEqualTo(2024);
        assertThat(result.getFirst().getId().getRound()).isEqualTo("Grand Final");
        verify(predictionRepository, times(1)).findByIdSeasonAndIdRound(2024, "Grand Final");
    }

    @Test
    public void testGetPredictionsBySeasonAndRound_NoResults(){
        // defining behaviour
        when(predictionRepository.findByIdSeasonAndIdRound(2050, "Round 30")).thenReturn(List.of());

        // Calling service method
        List<Prediction> result = predictionService.getPredictionsByIdSeasonAndIdRound(2050, "Round 30");

        // Verify results
        assertThat(result).isEmpty();
        verify(predictionRepository, times(1)).findByIdSeasonAndIdRound(2050, "Round 30");

    }

    @Test
    public void testGetLatestPredictions(){
        // Set up composite key
        CompositeKey key = new CompositeKey(2024, "Grand Final", "Storm");
        Date date = Date.from(LocalDate.of(2024, 10, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Prediction prediction = new Prediction();
        prediction.setId(key);
        prediction.setAwayTeam("Panthers");
        prediction.setVenue("Accor Stadium");
        prediction.setPrediction(1);
        prediction.setHomeScore(24);
        prediction.setAwayScore(20);
        prediction.setMargin(4);
        prediction.setDate(date);

        // Mock the latest entry and a list of predictions for that season/round
        when(predictionRepository.findTopByOrderBySeasonAndRoundDesc()).thenReturn(List.of(prediction));
        when(predictionRepository.findByIdSeasonAndIdRound(2024, "Grand Final"))
                .thenReturn(List.of(prediction));

        // Calling service method
        List<Prediction> result = predictionService.getLatestPredictions();

        // Verify result
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId().getSeason()).isEqualTo(2024);
        assertThat(result.getFirst().getId().getRound()).isEqualTo("Grand Final");
        verify(predictionRepository, times(1)).findTopByOrderBySeasonAndRoundDesc();
        verify(predictionRepository, times(1)).findByIdSeasonAndIdRound(2024, "Grand Final");

    }
}
