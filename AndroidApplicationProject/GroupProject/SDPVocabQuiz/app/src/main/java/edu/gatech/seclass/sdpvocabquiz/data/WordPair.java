package edu.gatech.seclass.sdpvocabquiz.data;

import android.util.Log;

public class WordPair {

    public final String LOG_TAG = "WordPair";

    private String word;
    private String correctDefinition;

    public WordPair(){}

    public WordPair(String word, String definition){
        this.word = word;
        this.correctDefinition = definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCorrectDefinition(String correctDefinition) {
        this.correctDefinition = correctDefinition;
    }

    public String getWord() {
        return word;
    }

    public String getCorrectDefinition() {
        return correctDefinition;
    }

    public String print(){
        return "Word: " + word + " Definition: " + correctDefinition;
    }
}
