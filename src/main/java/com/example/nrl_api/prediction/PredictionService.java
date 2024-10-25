package com.example.nrl_api.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredictionService {
    private final PredictionRepository predictionRepository;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
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
        existingPrediction.setAwayScore(prediction.getawayScore());
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
