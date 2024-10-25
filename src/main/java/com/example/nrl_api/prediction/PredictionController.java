package com.example.nrl_api.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/predictions")
public class PredictionController {
    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPrediction(@RequestBody Prediction prediction) {
        return predictionService.newPrediction(prediction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePredictionById(@PathVariable CompositeKey id, @RequestBody Prediction prediction) {
        return this.predictionService.updatePrediction(id, prediction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePredictionById(@PathVariable CompositeKey id) {
        return this.predictionService.deletePrediction(id);
    }

    @GetMapping("/{season}/{round}/{home_team}")
    public ResponseEntity<Object> getPredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String home_team) {
        CompositeKey id = new CompositeKey(season, round, home_team);

        return this.predictionService.getPredictionById(id);
    }
}
