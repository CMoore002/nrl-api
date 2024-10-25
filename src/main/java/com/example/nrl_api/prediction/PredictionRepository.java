package com.example.nrl_api.prediction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, CompositeKey> {
}
