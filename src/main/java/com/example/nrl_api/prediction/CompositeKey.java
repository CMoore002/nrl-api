package com.example.nrl_api.prediction;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompositeKey implements Serializable{
    private Integer season;
    private String  round;
    private String  homeTeam;

    public CompositeKey() {}

    public CompositeKey(Integer season, String round, String homeTeam) {
        this.season = season;
        this.round = round;
        this.homeTeam = homeTeam;
    }

    // Getters and Setters
    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    // Overriding equals and hashCode for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(season, that.season) &&
                Objects.equals(round, that.round) &&
                Objects.equals(homeTeam, that.homeTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, round, homeTeam);
    }
}
