package com.example.nrl_api.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PredictionService {
    private final PredictionRepository predictionRepository;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository){
        this.predictionRepository = predictionRepository;
    }


    public List<Prediction> getLatestPredictions() {
        List<Prediction> latestSeasonPredictions = predictionRepository.findTopByOrderBySeasonAndRoundDesc();
        // Find the latest round based on the custom order
        Optional<String> latestRound = latestSeasonPredictions.stream()
                .map(prediction -> prediction.getId().getRound())
                .max(Comparator.comparingInt(RoundUtils::getRoundOrder));

        if (latestRound.isPresent()) {
            Integer season = latestSeasonPredictions.getFirst().getId().getSeason(); // Latest season
            return predictionRepository.findByIdSeasonAndIdRound(season, latestRound.get());
        }

        return List.of(); // Return an empty list if no predictions found
    }

    public List<Prediction> getPredictionsByHomeTeam(String homeTeam){
        return predictionRepository.findByIdHomeTeam(homeTeam);
    }

    public List<Prediction> getPredictionsByAwayTeam(String awayTeam){
        return predictionRepository.findByAwayTeam(awayTeam);
    }

    public List<Prediction> getPredictionsBySeason(Integer season){
        return predictionRepository.findByIdSeason(season);
    }

    public List<Prediction> getPredictionsByIdSeasonAndIdRound(Integer season, String round){
        return predictionRepository.findByIdSeasonAndIdRound(season, round);
    }

    public ResponseEntity<Object> newPrediction(Prediction prediction){
        predictionRepository.save(prediction);
        return new ResponseEntity<>(prediction, HttpStatus.CREATED);
    }

    public List<Prediction> getPredictions(){
        return this.predictionRepository.findAll();
    }

    public ResponseEntity<Object> updatePrediction(CompositeKey id, Prediction prediction){
        Optional<Prediction> predictionOptional = predictionRepository.findById(id);

        if(predictionOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Prediction existingPrediction = predictionOptional.get();

        existingPrediction.setId(id);
        existingPrediction.setDate(prediction.getDate());
        existingPrediction.setPrediction(prediction.getPrediction());
        existingPrediction.setAwayScore(prediction.getAwayScore());
        existingPrediction.setAwayTeam(prediction.getAwayTeam());
        existingPrediction.setHomeScore(prediction.getHomeScore());
        existingPrediction.setMargin(prediction.getMargin());
        existingPrediction.setVenue(prediction.getVenue());
        predictionRepository.save(existingPrediction);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> deletePrediction(CompositeKey id){
        Optional<Prediction> predictionOptional = predictionRepository.findById(id);
        if(predictionOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Prediction existingPrediction = predictionOptional.get();
        predictionRepository.delete(existingPrediction);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getPredictionById(CompositeKey id){
        Optional<Prediction> predictionOptional = predictionRepository.findById(id);
        if(predictionOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(predictionOptional.get());

    }
}
