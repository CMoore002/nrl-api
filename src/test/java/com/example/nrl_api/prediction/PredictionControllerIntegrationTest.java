package com.example.nrl_api.prediction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PredictionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllPredictions() throws Exception {
        mockMvc.perform(get("/api/v1/predictions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].id.homeTeam", is("Roosters")));
    }

    @Test
    public void testGetLatestPredictions() throws Exception {
        mockMvc.perform(get("/api/v1/predictions/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    public void testGetPredictionsBySeasonAndRound() throws Exception {
        mockMvc.perform(get("/api/v1/predictions/season/2024/round/Round 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id.round", is("Round 1")))
                .andExpect(jsonPath("$[0].id.season", is(2024)));
    }

    @Test
    public void testGetPredictionsByHomeTeam() throws Exception {
        mockMvc.perform(get("/api/v1/predictions/homeTeam/Sea Eagles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].id.homeTeam", is("Sea Eagles")));
    }

    @Test
    public void testGetPredictionsByAwayTeam() throws Exception {
        mockMvc.perform(get("/api/v1/predictions/awayTeam/Unknown Team"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreatePrediction() throws Exception {
        mockMvc.perform(post("/api/v1/predictions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"season\":2024,\"round\":\"Round 2\",\"homeTeam\":\"Panthers\"},"
                                + "\"venue\":\"StadiumB\","
                                + "\"date\":\"2024-03-10\","
                                + "\"prediction\":1,"
                                + "\"homeScore\":28,"
                                + "\"awayScore\":14,"
                                + "\"margin\":14}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdatePrediction() throws Exception {
        mockMvc.perform(put("/api/v1/predictions/2024/Round 1/Sea Eagles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"venue\":\"Updated Stadium\",\"homeScore\":25,\"awayScore\":10}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePrediction() throws Exception {
        mockMvc.perform(delete("/api/v1/predictions/2024/Round 1/Sea Eagles"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPredictionById() throws Exception {
        mockMvc.perform(get("/api/v1/predictions/2024/Round 1/Sea Eagles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.season", is(2024)))
                .andExpect(jsonPath("$.id.round", is("Round 1")))
                .andExpect(jsonPath("$.id.homeTeam", is("Sea Eagles")));
    }
}
