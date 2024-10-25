package com.example.nrl_api.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/venue")
    public List<Prediction> getPredictionsByVenue(@RequestParam String venue){
        return this.predictionService.getPredictionsByVenue(venue);
    }

    @GetMapping("/date")
    public List<Prediction> getPredictionsByDate(@RequestParam Date date){
        return this.predictionService.getPredictionsByDate(date);
    }

    @GetMapping("/homeTeam")
    public List<Prediction> getPredictionsByHomeTeam(@RequestParam String homeTeam){
        return this.predictionService.getPredictionsByHomeTeam(homeTeam);
    }

    @GetMapping("/awayTeam")
    public List<Prediction> getPredictionsByAwayTeam(@RequestParam String awayTeam){
        return this.predictionService.getPredictionsByAwayTeam(awayTeam);
    }

    @GetMapping("/season")
    public List<Prediction> getPredictionsBySeason(@RequestParam Integer season){
        return this.predictionService.getPredictionsBySeason(season);
    }

    @GetMapping("/round")
    public List<Prediction> getPredictionsByRound(@RequestParam String round){
        return this.predictionService.getPredictionsByRound(round);
    }

    /*
    * TODO: potentially update
    *  */
    @PostMapping("/")
    public ResponseEntity<Object> createPrediction(@PathVariable Prediction prediction) {
        return predictionService.newPrediction(prediction);
    }

    @PutMapping("/{season}/{round}/{home_team}")
    public ResponseEntity<Object> updatePredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String home_team, @RequestBody Prediction prediction) {
        CompositeKey id = new CompositeKey(season, round, home_team);
        return this.predictionService.updatePrediction(id, prediction);
    }

    @DeleteMapping("/{season}/{round}/{home_team}")
    public ResponseEntity<Object> deletePredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String home_team) {
        CompositeKey id = new CompositeKey(season, round, home_team);
        return this.predictionService.deletePrediction(id);
    }

    @GetMapping("/{season}/{round}/{home_team}")
    public ResponseEntity<Object> getPredictionById(@PathVariable Integer season, @PathVariable String round, @PathVariable String home_team) {
        CompositeKey id = new CompositeKey(season, round, home_team);

        return this.predictionService.getPredictionById(id);
    }
}
