package edu.gatech.seclass.sdpvocabquiz.data;

import android.util.Log;

public class IncorrectDefinition {

    public final String LOG_TAG = "IncorrecrDefinition";

    private String incorrectDefinition;

    public IncorrectDefinition(){}

    public IncorrectDefinition(String incorrectDefinition){
        this.incorrectDefinition = incorrectDefinition;
    }

    public void setDefinition(String incorrectDefinition) {
        this.incorrectDefinition = incorrectDefinition;
    }

    public void setIncorrectDefinition(String incorrectDefinition) {
        this.incorrectDefinition = incorrectDefinition;
    }

    public String getIncorrectDefinition() {
        return incorrectDefinition;
    }
}
