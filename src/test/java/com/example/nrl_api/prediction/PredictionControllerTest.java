package com.example.nrl_api.prediction;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebMvcTest(PredictionController.class)
public class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PredictionService predictionService;

    private List<Prediction> samplePredictions;

    @BeforeEach
    public void setUp() throws Exception {
        // Date formatter for parsing date strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Creating sample predictions with different rounds and teams
        samplePredictions = Arrays.asList(
                createPrediction(2024, "Round 1", "Allegiant Stadium", dateFormat.parse("2024-03-03"), "Sea Eagles", "Rabbitohs", 1, 24, 20, 4),
                createPrediction(2024, "Round 1", "Allegiant Stadium", dateFormat.parse("2024-03-03"), "Roosters", "Broncos", 1, 20, 18, 2),
                createPrediction(2024, "Round 2", "Suncorp Stadium", dateFormat.parse("2024-03-14"), "Broncos", "Rabbitohs", 1, 26, 18, 8),
                createPrediction(2024, "Round 5", "4 Pines Park", dateFormat.parse("2024-04-06"), "Sea Eagles", "Panthers", 1, 23, 22, 1),
                createPrediction(2024, "Grand Final", "Accor Stadium", dateFormat.parse("2024-10-06"), "Storm", "Panthers", 1, 24, 20, 4)
        );
    }

    private Prediction createPrediction(int season, String round, String venue, Date date, String homeTeam, String awayTeam, int prediction, int homeScore, int awayScore, int margin) {
        CompositeKey key = new CompositeKey(season, round, homeTeam);
        Prediction predictionObj = new Prediction();
        predictionObj.setId(key);
        predictionObj.setAwayTeam(awayTeam);
        predictionObj.setVenue(venue);
        predictionObj.setDate(date);
        predictionObj.setHomeScore(homeScore);
        predictionObj.setAwayScore(awayScore);
        predictionObj.setPrediction(prediction);
        predictionObj.setMargin(margin);
        return predictionObj;
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        when(predictionService.getPredictions()).thenReturn(samplePredictions);

        mockMvc.perform(get("/api/v1/predictions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id.season", is(2024)))
                .andExpect(jsonPath("$[0].id.round", is("Round 1")))
                .andExpect(jsonPath("$[0].id.homeTeam", is("Sea Eagles")))
                .andExpect(jsonPath("$[4].id.round", is("Grand Final")))
                .andExpect(jsonPath("$[4].id.homeTeam", is("Storm")));
    }

    @Test
    public void testGetLatestPredictions() throws Exception {
        // Assume the latest prediction is for the Grand Final
        when(predictionService.getLatestPredictions()).thenReturn(List.of(samplePredictions.get(4)));

        mockMvc.perform(get("/api/v1/predictions/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id.season", is(2024)))
                .andExpect(jsonPath("$[0].id.round", is("Grand Final")));
    }

    @Test
    public void testGetPredictionsByIdSeasonAndIdRound() throws Exception {
        when(predictionService.getPredictionsByIdSeasonAndIdRound(2024, "Round 1")).thenReturn(List.of(samplePredictions.get(0), samplePredictions.get(1)));

        mockMvc.perform(get("/api/v1/predictions/season/2024/round/Round 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id.season", is(2024)))
                .andExpect(jsonPath("$[0].id.round", is("Round 1")));
    }

    @Test
    public void testGetPredictionsByHomeTeam() throws Exception {
        when(predictionService.getPredictionsByHomeTeam("Sea Eagles")).thenReturn(List.of(samplePredictions.get(0), samplePredictions.get(3)));

        mockMvc.perform(get("/api/v1/predictions/homeTeam/Sea Eagles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id.homeTeam", is("Sea Eagles")));
    }

    @Test
    public void testCreatePrediction() throws Exception {
        when(predictionService.newPrediction(any(Prediction.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/v1/predictions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"season\":2024,\"round\":\"Round 1\",\"homeTeam\":\"Sea Eagles\"},\"awayTeam\":\"Rabbitohs\", \"venue\":\"Allegiant Stadium\",\"date\":\"2024-03-03\",\"prediction\":1,\"homeScore\":24,\"awayScore\":20,\"margin\":4}"))
                .andExpect(status().isOk());
    }
}
