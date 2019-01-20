package edu.gatech.seclass.sdpvocabquiz.data;

import android.os.Debug;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.gatech.seclass.sdpvocabquiz.MessageType;
import edu.gatech.seclass.sdpvocabquiz.QuizSelection;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.data.Word;
import java.util.*;

public class Quiz
{
    public static String LOG_TAG = "Quiz";
    private String uniqueName;
    private String author;
    private String description;
    private ArrayList<WordPair> wordPairList;
    private List<IncorrectDefinition> incorrectDefList;
    private ArrayList<String> fullScoreStudent;
    private float percentage;
    private int numberCorrect;

    public Quiz(){}

    public Quiz(String uniqueName, String loggedInUser, String description, int percentage){
        this.uniqueName = uniqueName;
        this.author = loggedInUser;
        this.description = description;
        this.incorrectDefList = incorrectDefList;
        this.percentage = 0;
    }

    public Quiz(String uniqueName, String loggedInUser, String description,
                ArrayList<WordPair> wordPairsList, List<IncorrectDefinition> incorrectDefList){
        this.uniqueName = uniqueName;
        this.author = loggedInUser;
        this.description = description;
        this.wordPairList = wordPairsList;
        this.incorrectDefList = incorrectDefList;
        this.percentage = 0;
    }

    public ArrayList<String> getFullScoreStudent() {
        return fullScoreStudent;
    }

    public void setFullScoreStudent(ArrayList<String> fullScoreStudent) {
        this.fullScoreStudent = fullScoreStudent;
    }

    public String getUniqueName(){
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<WordPair> getWordPairList() {
        return wordPairList;
    }

    public void setWordPairList(ArrayList<WordPair> wordPairList) {
        this.wordPairList = wordPairList;
    }

    public List<IncorrectDefinition> getIncorrectDefList() {
        return incorrectDefList;
    }

    public void setIncorrectDefList(List<IncorrectDefinition> incorrectDefList) {
        this.incorrectDefList = incorrectDefList;
    }

    public String getDescription() {return description; }

    public String getAuthor(){return author; }

    public float getPercentage(){
        return percentage;
    }

    public ArrayList<String> getGoodDefList(){
        ArrayList<String> goodDefList = new ArrayList<>();
        if(this.wordPairList.size()> 0) {
            for (int i=0; i<this.wordPairList.size();i++) {
                goodDefList.add(this.wordPairList.get(i).getCorrectDefinition());
            }
            return goodDefList;
        }
        return null;
    }

    //Todo: Generate word questions to display where words can only be used once but incorrect definitions can be reused
    public ArrayList<QuizSelection> generateQuestions(){

        if(this.wordPairList.size() < 0) {
            return null; //invalid size
        }
        ArrayList<QuizSelection> quizSelectionList = new ArrayList<QuizSelection>();

        for (int i = 0;i<this.wordPairList.size();i++) {

            ArrayList<String> goodDefList =  this.getGoodDefList();
            List<IncorrectDefinition> badDefList = this.getIncorrectDefList();
            QuizSelection quizSelection = new QuizSelection();
            quizSelection.setWord(this.wordPairList.get(i).getWord());   //Set Word

            String correctAns = this.wordPairList.get(i).getCorrectDefinition(); //get correct ans

            //Shuffle incorrect definition list
            Collections.shuffle(badDefList, new Random());
            //Remove correct answer and shuffle correct definition list
            goodDefList.remove(correctAns);
            if(goodDefList.size() > 0) {
                Collections.shuffle(goodDefList, new Random());
            }

            //4 option of each word
            List<String> option = new ArrayList<>();
            option.add(correctAns); //Add correct ans into option

            //Generate random number for option case
            Random rand = new Random();
            int n = 1; //rand.nextInt(4)+1;
            switch (n) {
                default:
                case 1: //All 3 incorrect
                    option.add(badDefList.get(0).getIncorrectDefinition());
                    option.add(badDefList.get(1).getIncorrectDefinition());
                    option.add(badDefList.get(2).getIncorrectDefinition());
                break;

                case 2: //1 correct def from other word + 2 incorrect
                    if(goodDefList.get(0) != null) {
                        option.add(goodDefList.get(0));
                    }
                    else {
                        option.add(badDefList.get(0).getIncorrectDefinition());
                    }
                    option.add(badDefList.get(1).getIncorrectDefinition());
                    option.add(badDefList.get(2).getIncorrectDefinition());
                    break;
                case 3: //2 correct def from other word + 1 incorrect
                    if(goodDefList.get(0) != null) {
                        option.add(goodDefList.get(0));
                    }
                    else {
                        option.add(badDefList.get(0).getIncorrectDefinition());
                    }
                    if(goodDefList.get(1) != null) {
                        option.add(goodDefList.get(1));
                    }
                    else {
                        option.add(badDefList.get(1).getIncorrectDefinition());
                    }
                    option.add(badDefList.get(2).getIncorrectDefinition());
                    break;
                case 4: //3 correct def from other word
                    if(goodDefList.get(0) != null) {
                        option.add(goodDefList.get(0));
                    }
                    else {
                        option.add(badDefList.get(0).getIncorrectDefinition());
                    }
                    if(goodDefList.get(1) != null) {
                        option.add(goodDefList.get(1));
                    }
                    else {
                        option.add(badDefList.get(1).getIncorrectDefinition());
                    }
                    if(goodDefList.get(2) != null) {
                        option.add(goodDefList.get(2));
                    }
                    else {
                        option.add(badDefList.get(2).getIncorrectDefinition());
                    }
                    break;
            }

            //shuffle option again
            Collections.shuffle(option, new Random());
            quizSelection.setCorrectAnsIndex(option.indexOf(correctAns));
            quizSelection.setOption(option);
            quizSelectionList.add(quizSelection);
        }
        return quizSelectionList;
    }

    //Todo: Shuffle list of word questions
    public void shuffleQuestions(){

    }

    //Todo: display question to user
    public void displayQuestion(Word word){

    }

    //Todo: Check answer of user and return corresponding message "Correct or Incorrect"
    public MessageType checkAnswer(Word word){
        updateScore();
        return null;
    }

    //Todo: Keep track of user's score
    public float updateScore(){
        return numberCorrect / wordPairList.size() * 100.0f;
    }

    //Todo: Return a list of statistics for that specific quiz
    public List<Score> stats(){
        return null;
    }

    public void print(){
        Log.i(LOG_TAG,  getUniqueName().toString() + " by " + getAuthor().toString());


    }
}
