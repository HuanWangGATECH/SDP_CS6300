package edu.gatech.seclass.sdpvocabquiz;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.data.Score;

public class Scoreboard
{
    List<Score> scores;

    //Todo: Sort the scoreboard by username alphabetically (or firstname / lastname where appictable? needs clarification in spec)
    public List<Score> orderAlphabetically(){

        return null;
    }

    //Todo: Sort scores by Date and Time
    public List<Score> orderByDate(){
        return null;
    }

    //Todo: Order scores by their score value
    public List<Score> orderByValue(){
        return null;
    }


    //Todo: Return list of scores where student has taken quizzes
    public List<Score> filterByHasTaken(){
        return null;
    }

}
