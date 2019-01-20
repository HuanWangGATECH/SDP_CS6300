package edu.gatech.seclass.sdpvocabquiz.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;

public class IncorrectDefinitionAdapter extends RecyclerView.Adapter<IncorrectDefinitionAdapter.IncorrectDefinitionHolder> {

    private List<IncorrectDefinition> incorrectDefinitions;

    public IncorrectDefinitionAdapter(List<IncorrectDefinition> incorrectDefinitions) {
        this.incorrectDefinitions = incorrectDefinitions;
    }

    public List<IncorrectDefinition> getItems(){
        return incorrectDefinitions;
    }

    @Override
    public int getItemCount() {
        return incorrectDefinitions.size();
    }

    @Override
    public void onBindViewHolder(IncorrectDefinitionHolder incorrectDefinitionHolder, int i) {
        IncorrectDefinition incorrectDefinition = incorrectDefinitions.get(i);
        incorrectDefinitionHolder.incorrectDefinitionListener.updatePosition(i);
        incorrectDefinitionHolder.incorrectDefinitionEditText.setText(incorrectDefinition.getIncorrectDefinition());
    }

    @Override
    public IncorrectDefinitionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_incorrect_definition, viewGroup, false);
            IncorrectDefinitionHolder incorrectDefinitionHolder = new IncorrectDefinitionHolder(itemView, new IncorrectDefinitionListener());
        return incorrectDefinitionHolder;
    }

    public static class IncorrectDefinitionHolder extends RecyclerView.ViewHolder {

        IncorrectDefinitionListener incorrectDefinitionListener;
        protected EditText incorrectDefinitionEditText;

        public IncorrectDefinitionHolder(View v, IncorrectDefinitionListener incorrectDefinitionListener) {
            super(v);
            this.incorrectDefinitionEditText = (EditText)  v.findViewById(R.id.quizWordIncorrectDefinition);
            this.incorrectDefinitionListener = incorrectDefinitionListener;
            this.incorrectDefinitionEditText.addTextChangedListener(incorrectDefinitionListener);
        }
    }

    private class IncorrectDefinitionListener implements TextWatcher {
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            IncorrectDefinition incorrectDefinition = incorrectDefinitions.get(position);
            incorrectDefinition.setIncorrectDefinition(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}