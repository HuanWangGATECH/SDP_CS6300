package edu.gatech.seclass.sdpvocabquiz.data;

import edu.gatech.seclass.sdpvocabquiz.MessageType;

public class Word
{
    private String word;
    private String[] definitions = new String[4];
    private MessageType message;

    public String getWord() {
        return word;
    }

    public String[] getDefinitions() {
        return definitions;
    }

    public MessageType getMessage() {
        return message;
    }
}
