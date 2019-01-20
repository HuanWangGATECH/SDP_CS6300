package edu.gatech.seclass.sdpvocabquiz.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.adapters.IncorrectDefinitionAdapter;
import edu.gatech.seclass.sdpvocabquiz.adapters.WordPairAdapter;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;

public class CreateQuizActivity extends AppCompatActivity {

    public static String LOG_TAG = "CreateQuizActivity";

    QuizDBManager quizDBManager;

    EditText quizNameField;
    EditText quizDescriptionField;

    ImageButton backButton;
    FloatingActionButton continueButton;
    RecyclerView wordDefinitionRecyclerView;
    WordPairAdapter wordPairAdapter;
    RecyclerView incorrectDefinitionRecyclerView;
    IncorrectDefinitionAdapter incorrectDefinitionAdapter;
    Button addWordButton;

    List<WordPair> wordPairs;
    List<IncorrectDefinition> incorrectDefintions;

    boolean noError(EditText editText){
        return (editText.getError() != null || editText.getText().length() == 0) ? false : true;
    }

    public void addQuizToDatabase(Activity activity){
        String uniqueName = quizNameField.getText().toString();
        String description = quizDescriptionField.getText().toString();

        // Add our quiz to our database
        quizDBManager.createQuiz(uniqueName, Global.userNameLastName, description, wordPairs, incorrectDefintions);

        // Debug
        Log.i(LOG_TAG, "Created Quiz " + uniqueName + " by " + Global.userNameLastName);
        Log.i(LOG_TAG, "Description: " + description);
        Log.i(LOG_TAG, "Word Pairs List: ");
        for (WordPair wp: wordPairs) {
            Log.i(LOG_TAG, wp.print());
        }
        Log.i(LOG_TAG, "Incorrect Definitions List: ");
        for (IncorrectDefinition ic : incorrectDefintions) {
            Log.i(LOG_TAG, "Incorrect Definitions: " + ic.getIncorrectDefinition());
        }
    }

    public void addWord(){
        if(wordPairs.size() < 10){
            wordPairs.add(new WordPair("",""));
            incorrectDefintions.add(new IncorrectDefinition(""));
            incorrectDefintions.add(new IncorrectDefinition(""));
            incorrectDefintions.add(new IncorrectDefinition(""));
            wordPairAdapter.notifyDataSetChanged();
            incorrectDefinitionAdapter.notifyDataSetChanged();
            wordPairAdapter.setFocusToLastAdded();
            Toast.makeText(getBaseContext(), getString(R.string.msg_added_words) + " " + String.valueOf(wordPairs.size()) + "/10" , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), getString(R.string.msg_cant_add_words), Toast.LENGTH_LONG).show();
        }
    }

    public void removeWord(int position){
        if(wordPairs.size() > 1){
            removeWordAt(position);
            Toast.makeText(getBaseContext(), getString(R.string.msg_removed_word_success) + " " + String.valueOf(wordPairs.size()) + "/10" , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), getString(R.string.msg_removed_word_failure), Toast.LENGTH_LONG).show();
            // Re-Add Word Pair item (cant prevent swipe removing lat item)
            wordPairAdapter.notifyDataSetChanged();
        }
    }

    public void showDialog(final Activity activity) {
        Log.i(LOG_TAG,"Showing create quiz dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.dialog_title_create_quiz);
        builder.setMessage("You are about to create your quiz. Do you want to proceed?");
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(LOG_TAG,"Ok button pressed");

                addQuizToDatabase(activity);

                Intent i = new Intent(getBaseContext(), DashboardActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(LOG_TAG,"Cancel button pressed");
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void removeWordAt(int position){
        removeThreeIncorrectDefinitions();
        wordPairs.remove(position);
        wordPairAdapter.notifyItemRemoved(position);
    }

    public void removeThreeIncorrectDefinitions(){
        for(int i = 0; i < 3; i++){
            incorrectDefintions.remove(incorrectDefintions.size()-1);
        }
        incorrectDefinitionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        Theme.setColorBar(this);

        // establish our database
        quizDBManager = new QuizDBManager(this, QuizDBManager.DB_NAME);

        quizNameField = (EditText) findViewById(R.id.createQuizUniqueName);
        quizDescriptionField = (EditText) findViewById(R.id.createQuizDescription);

        backButton = (ImageButton) findViewById(R.id.createQuizBackButton);
        continueButton = (FloatingActionButton)  findViewById(R.id.fab_confirm_quiz_details);
        wordDefinitionRecyclerView = (RecyclerView) findViewById(R.id.wordDefinitionList);
        incorrectDefinitionRecyclerView = (RecyclerView) findViewById(R.id.incorrectDefinitionList);
        addWordButton = (Button) findViewById(R.id.addMoreWordpairs);

        quizNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(quizDBManager.isQuizExist(input)){
                    quizNameField.setError(getResources().getString(R.string.error_quiz_in_use));
                }else if (input.trim().length()==0 || input.isEmpty()){
                    quizNameField.setError(getResources().getString(R.string.error_cannot_be_blanks));
                }else{
                    quizNameField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_check, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on back button");
                startActivity(new Intent(v.getContext(), DashboardActivity.class));
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on Done FAB button");
                if(noError(quizNameField)){
                    showDialog((Activity) v.getContext());
                }else{
                    Toast.makeText(getBaseContext(), "Could not Continue due to an error", Toast.LENGTH_SHORT).show();
                    quizNameField.requestFocus();
                }

            }
        });

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on Add Word button");
                addWord();
            }
        });

        ItemTouchHelper.SimpleCallback simpleTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // Remove item.
                Log.i(LOG_TAG,"Swiped on element " + viewHolder.getAdapterPosition());
                // Ensure we only remove if we have at least 1 word pair item
                removeWord(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(wordDefinitionRecyclerView);

        // Setup our Word Pair Recycler View.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        wordDefinitionRecyclerView.setLayoutManager(llm);

        // Setup our Wrong Definitions Recycler View.
        LinearLayoutManager llmTwo = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        incorrectDefinitionRecyclerView.setLayoutManager(llmTwo);

        // Initialize our lists.
        wordPairs = new ArrayList<>();
        incorrectDefintions = new ArrayList<>();

        // Create Initial word definition.
        wordPairs.add(new WordPair("",""));
        // For each word we will have 3 incorrect definitions
        incorrectDefintions.add(new IncorrectDefinition(""));
        incorrectDefintions.add(new IncorrectDefinition(""));
        incorrectDefintions.add(new IncorrectDefinition(""));

        // Populate our quiz objects to the adapter view.
        wordPairAdapter = new WordPairAdapter(wordPairs);
        wordDefinitionRecyclerView.setAdapter(wordPairAdapter);
        incorrectDefinitionAdapter = new IncorrectDefinitionAdapter(incorrectDefintions);
        incorrectDefinitionRecyclerView.setAdapter(incorrectDefinitionAdapter);

        // Set focus to quizName
        quizNameField.requestFocus();
    }
}
