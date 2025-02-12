package com.example.nrl_api.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/predictions")
public class PredictionController {
    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    /*
    * TODO: Delete GET mappings, add certain queries i.e
    *  season & round / season & team
    * */
    @GetMapping
    public List<Prediction> getAllPredictions(){
        return this.predictionService.getPredictions();
    }

    @GetMapping("/latest")
    public List<Prediction> getLatestPredictions() {
        return predictionService.getLatestPredictions();
    }

    @GetMapping("/season/{season}/round/{round}")
    public List<Prediction> getPredictionsByIdSeasonAndIdRound(@PathVariable Integer season, @PathVariable String round){
        return this.predictionService.getPredictionsByIdSeasonAndIdRound(season, round);
    }

    @GetMapping("/homeTeam/{homeTeam}")
    public List<Prediction> getPredictionsByHomeTeam(@PathVariable String homeTeam){
        return this.predictionService.getPredictionsByHomeTeam(homeTeam);
    }

    @GetMapping("/awayTeam/{awayTeam}")
    public List<Prediction> getPredictionsByAwayTeam(@PathVariable String awayTeam){
        return this.predictionService.getPredictionsByAwayTeam(awayTeam);
    }

    @GetMapping("/season/{season}")
    public List<Prediction> getPredictionsBySeason(@PathVariable Integer season){
        return this.predictionService.getPredictionsBySeason(season);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPrediction(@RequestBody Prediction prediction) {
        return predictionService.newPrediction(prediction);
    }

    @PutMapping("/{season}/{round}/{homeTeam}")
    public ResponseEntity<Object> updatePredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String homeTeam, @RequestBody Prediction prediction) {
        CompositeKey id = new CompositeKey(season, round, homeTeam);
        return this.predictionService.updatePrediction(id, prediction);
    }

    @DeleteMapping("/{season}/{round}/{homeTeam}")
    public ResponseEntity<Object> deletePredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String homeTeam) {
        CompositeKey id = new CompositeKey(season, round, homeTeam);
        return this.predictionService.deletePrediction(id);
    }

    @GetMapping("/{season}/{round}/{homeTeam}")
    public ResponseEntity<Object> getPredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String homeTeam) {
        CompositeKey id = new CompositeKey(season, round, homeTeam);

        return this.predictionService.getPredictionById(id);
    }
}
