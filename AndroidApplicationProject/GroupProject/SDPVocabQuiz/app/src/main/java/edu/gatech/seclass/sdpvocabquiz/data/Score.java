package edu.gatech.seclass.sdpvocabquiz.data;

import java.util.Date;

public class Score
{
    private String uniqueName;
    private String username;
    private float percentage;
    private float firstScore;
    private float highestScore;
    private Date newScoreData;
    private Date firstScoreDate;
    private Date highestScoreDate;
    private int taken;

    public Score() {}

    public Score(String uniqueName, String username, float percentage, float firstScore,
                 float highestScore, Date newScoreData, Date firstScoreDate, Date highestScoreDate,
                 int taken) {
        this.uniqueName = uniqueName;
        this.username = username;
        this.percentage = percentage;
        this.firstScore = firstScore;
        this.highestScore = highestScore;
        this.newScoreData = newScoreData;
        this.firstScoreDate = firstScoreDate;
        this.highestScoreDate = highestScoreDate;
        this.taken = taken;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(float firstScore) {
        this.firstScore = firstScore;
    }

    public float getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(float highestScore) {
        this.highestScore = highestScore;
    }

    public Date getNewScoreDate() {
        return newScoreData;
    }

    public void setNewScoreDate(Date newScoreData) {
        this.newScoreData = newScoreData;
    }

    public Date getFirstScoreDate() {
        return firstScoreDate;
    }

    public void setFirstScoreDate(Date firstScoreDate) {
        this.firstScoreDate = firstScoreDate;
    }

    public Date getHighestScoreDate() {
        return highestScoreDate;
    }

    public void setHighestScoreDate(Date highestScoreDate) {
        this.highestScoreDate = highestScoreDate;
    }

    public void setTaken(){
        this.taken = 1;
    }

    public void setTaken(int i){
        this.taken = i;
    }

    public int getTaken(){
        return this.taken;
    }

    public boolean hasTaken(){
        return (this.taken == 1) ? true : false;
    }

}
