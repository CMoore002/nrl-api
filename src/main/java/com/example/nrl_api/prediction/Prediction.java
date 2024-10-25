package com.example.nrl_api.prediction;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "prediction")
public class Prediction {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private CompositeKey id;

    /*
    @Column(name = "season", insertable = false, updatable = false)
    private Integer seasonSingle;
    @Column(name = "round", insertable = false, updatable = false)
    private String roundSingle;
    @Column(name = "home_team", insertable = false, updatable = false)
    private String homeTeamSingle;
    */
    private String venue;
    private Date date;
    private Integer prediction;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private Integer margin;

    public CompositeKey getId() {
        return id;
    }


    public void setId(CompositeKey id) {
        this.id = id;

    }


    public String getVenue() {
        return venue;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }


    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrediction() {
        return prediction;
    }

    public void setPrediction(Integer prediction) {
        this.prediction = prediction;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }
}
