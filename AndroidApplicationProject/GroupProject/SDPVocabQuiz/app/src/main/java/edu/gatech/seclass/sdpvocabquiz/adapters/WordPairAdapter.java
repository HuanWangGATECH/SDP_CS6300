package edu.gatech.seclass.sdpvocabquiz.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;

public class WordPairAdapter extends RecyclerView.Adapter<WordPairAdapter.WordPairHolder> {

    private List<WordPair> wordPairs;
    private boolean addButtonClicked;

    public WordPairAdapter(List<WordPair> wordPairs) {
        this.wordPairs = wordPairs;
    }

    public void setFocusToLastAdded(){
        addButtonClicked = true;
    }

    @Override
    public int getItemCount() {
        return wordPairs.size();
    }

    @Override
    public void onBindViewHolder(WordPairHolder wordPairHolder, int i) {
        // Attaching an event listener to the dynamically created adapter item
        // Update our textfields every time we bind a new element via a textListener
        // Updating textEdit fields that are dynamically generated with adapters
        // Source: https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview

        WordPair wp = wordPairs.get(i);

        wordPairHolder.wordListener.updatePosition(i);
        wordPairHolder.definitionListener.updatePosition(i);

        wordPairHolder.wordEditText.setText(wp.getWord());
        wordPairHolder.definitionEditText.setText(wp.getCorrectDefinition());

        // Set focus to latest item if added
        if(i == getItemCount()-1){
            Log.i("WordPairAdapter","Item count: " + i + "getItemCount: " + String.valueOf(getItemCount()));
            wordPairHolder.wordEditText.requestFocus();
        }

    }

    @Override
    public WordPairHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_word_pair, viewGroup, false);
        WordPairHolder wordPairHolder = new WordPairHolder(itemView, new AttachedWordEditTextListener(), new DefinitionEditTextListener());
        return wordPairHolder;
    }

    public static class WordPairHolder extends RecyclerView.ViewHolder {

        AttachedWordEditTextListener wordListener;
        DefinitionEditTextListener definitionListener;
        protected EditText wordEditText;
        protected EditText definitionEditText;

        public WordPairHolder(View v, AttachedWordEditTextListener wordListener, DefinitionEditTextListener definitionListener) {
            super(v);

            // Reference our elements
            this.wordEditText =  (EditText) v.findViewById(R.id.quizWordPairWord);
            this.definitionEditText = (EditText)  v.findViewById(R.id.quizWordPairDefinition);

            // Reference and Attach our text listener
            this.wordListener = wordListener;
            this.definitionListener = definitionListener;
            this.wordEditText.addTextChangedListener(wordListener);
            this.definitionEditText.addTextChangedListener(definitionListener);
        }
    }

    private class AttachedWordEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            WordPair wp = wordPairs.get(position);
            wp.setWord(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class DefinitionEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            WordPair wp = wordPairs.get(position);
            wp.setCorrectDefinition(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
