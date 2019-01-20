package edu.gatech.seclass.sdpvocabquiz;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class QuizSelection {
    private String word;
    private int correctAnsIndex;
    private List<String> option;

    public QuizSelection(){};

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCorrectAnsIndex() {
        return correctAnsIndex;
    }

    public void setCorrectAnsIndex(int correctAnsIndex) {
        this.correctAnsIndex = correctAnsIndex;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

//    public String toString (){
//        System.out.println(this.word);
//        System.out.println(String.valueOf(this.correctAnsIndex));
//        for(int i=0;i<option.size();i++){
//            System.out.println(option.get(i));
//        }
//        return null;
//    }
}
