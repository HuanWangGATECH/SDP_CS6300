package edu.gatech.seclass.sdpvocabquiz.utils;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.interfaces.ViewableStatistics;
import edu.gatech.seclass.sdpvocabquiz.MajorType;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.SeniorityType;
import edu.gatech.seclass.sdpvocabquiz.data.Word;

public class StudentUtils implements ViewableStatistics {

    public void register(String username, String firstName, String lastName, MajorType major, SeniorityType seniority, String email){
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.major = major;
//        this.seniority = seniority;
//        this.email = email;
    }

    //TODO: Add quiz and ensure uniqueName does not exist, incorrectDefinitions is 3*N and number of words between 0 to 10
    public void addQuiz(String uniqueName, String description, List<Word> words, List<String> incorrectDefinitions){

    }

    //TODO: Remove quiz using its uniqueName identifier, need to check it exists first
    public void removeQuiz(String uniqueName){

    }

    //TODO: A user may only practice other student quizzes by using the Quiz uniqueName identifier
    public void practiceQuiz(String uniqueName){

    }

    //Todo: Return list of stats for a specific student
    @Override
    public List<Score> stats(){

        return null;
    }

    //Todo: Are we going to use this or use attribute instead?
//    public List<Quiz> quizHistory(){
//
//    }
}
