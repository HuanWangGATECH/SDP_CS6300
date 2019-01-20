package edu.gatech.seclass.sdpvocabquiz.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.QuizSelection;
import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.SharedPrefsManager;

import static edu.gatech.seclass.sdpvocabquiz.utils.SQLUtils.register;
import static edu.gatech.seclass.sdpvocabquiz.utils.SQLUtils.userNameExistsInDatabase;

//Todo: Front End Logic for Practice Quiz
//Todo: Prevent "back stacking from going to previous questions - Can only go forward in the stack"
public class PracticeQuizActivity extends AppCompatActivity {

    public static String LOG_TAG = "PracticeQuizActivity";

    TextView displayWord;
    TextView displayNumber;
    TextView definitionOne;
    TextView definitionTwo;
    TextView definitionThree;
    TextView definitionFour;
    TextView displayAnswer;
    FloatingActionButton nextButton;

    QuizDBManager quizDBManager;
    Quiz quiz;
    List<QuizSelection> quizSelections;
    int correct;

    public enum Selection{
        ONE,
        TWO,
        THREE,
        FOUR,
        RESET
    }


    int wordNumber = 0;
    int correctAnswer;
    int selectedAnswer = Selection.RESET.ordinal();

    public float score(){
        return (float) correct / quizSelections.size() * 100.0f;
    }

    public void getNextWord(int i){
        displayWord.setText(quizSelections.get(i).getWord());
        displayNumber.setText(String.valueOf(i+1) + "/" + quizSelections.size());
        definitionOne.setText(quizSelections.get(i).getOption().get(0));
        definitionTwo.setText(quizSelections.get(i).getOption().get(1));
        definitionThree.setText(quizSelections.get(i).getOption().get(2));
        definitionFour.setText(quizSelections.get(i).getOption().get(3));
        correctAnswer = quizSelections.get(i).getCorrectAnsIndex();
        nextButton.hide();
    }

    public void checkAnswer(){
        Log.i(LOG_TAG, "Picked: " + selectedAnswer + "Correct: " + correctAnswer);
        if(correctAnswer == selectedAnswer){
            correct++;
            displayAnswer.setBackgroundResource(R.color.themedGreen);
            displayAnswer.setText(R.string.selection_correct);
            nextButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(), R.color.themedCheckBox)));
        }else{
            displayAnswer.setBackgroundResource(R.color.themedHighlightRed);
            displayAnswer.setText(R.string.selection_incorrect);
            nextButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(), R.color.themedRedCheckbox)));
        }
        displayAnswer.setVisibility(View.VISIBLE);
        nextButton.show();
    }

    public void displayAlreadyAnswered(){
        Toast.makeText(getBaseContext(), R.string.selection_alreadySelected, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);

        Theme.setColorBar(this);

        //Attach variable to ids
        displayWord = findViewById(R.id.displayWord);
        displayNumber = findViewById(R.id.displayNumber);
        definitionOne = findViewById(R.id.displayDefinitionOne);
        definitionTwo = findViewById(R.id.displayDefinitionTwo);
        definitionThree = findViewById(R.id.displayDefinitionThree);
        definitionFour = findViewById(R.id.displayDefinitionFour);

        LinearLayout definitionOne = findViewById(R.id.definitionOne);
        LinearLayout definitionTwo = findViewById(R.id.definitionTwo);
        LinearLayout definitionThree = findViewById(R.id.definitionThree);
        LinearLayout definitionFour = findViewById(R.id.definitionFour);

        // Initially hide these until our user selects an answer
        displayAnswer = findViewById(R.id.displayCheckAnswer);
        displayAnswer.setVisibility(View.GONE);
        nextButton = findViewById(R.id.fab_next_button);

        quizDBManager = new QuizDBManager(this, QuizDBManager.DB_NAME);

        // Get our quiz and generate the questions
        quiz = quizDBManager.getQuiz(Global.selectedQuiz);
        quizDBManager.close();

        Log.i(LOG_TAG, "Fetching quiz: " + Global.selectedQuiz);
        Log.i(LOG_TAG, "Description: " + quiz.getDescription());
        Log.i(LOG_TAG, "Author: " + quiz.getAuthor());
        List<WordPair> wordPairs = quiz.getWordPairList();
        Log.i(LOG_TAG, "Word Pairs: ");
        for(WordPair wp: wordPairs){
            Log.i(LOG_TAG, "WordPair: " + wp.getWord() + " - " + wp.getCorrectDefinition() );
        }

        Log.i(LOG_TAG, "Incorrect Definitions: ");
        List<IncorrectDefinition> definitions = quiz.getIncorrectDefList();
        for(IncorrectDefinition s: definitions){
            Log.i(LOG_TAG, "Incorrect Definition: " + s);
        }

        ArrayList<QuizSelection> selections = quiz.generateQuestions();
        // convert from Arraylist to array
        //quizSelections = selections.toArray(new QuizSelection[selections.size()]);
        quizSelections = selections;
        // Reset our answer
        selectedAnswer = Selection.RESET.ordinal();
        getNextWord(wordNumber);

        definitionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Definition One Selected");
                if(selectedAnswer == Selection.RESET.ordinal()){
                    selectedAnswer = Selection.ONE.ordinal();
                    checkAnswer();
                }else{
                    displayAlreadyAnswered();
                }
            }
        });

        definitionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Definition Two Selected");
                if(selectedAnswer == Selection.RESET.ordinal()) {
                    selectedAnswer = Selection.TWO.ordinal();
                    checkAnswer();
                }else{
                    displayAlreadyAnswered();
                }
            }
        });

        definitionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Definition Three Selected");
                if(selectedAnswer == Selection.RESET.ordinal()) {
                    selectedAnswer = Selection.THREE.ordinal();
                    checkAnswer();
                }else{
                    displayAlreadyAnswered();
                }
            }
        });

        definitionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Definition Four Selected");
                if(selectedAnswer == Selection.RESET.ordinal()) {
                    selectedAnswer = Selection.FOUR.ordinal();
                    checkAnswer();
                }else{
                    displayAlreadyAnswered();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Next Button Clicked");
                wordNumber++;
                if(wordNumber >= quizSelections.size()){
                    // Store double data type score as json and sent it to results
                    Gson gson = new Gson();
                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
                    //String serializedPercentScore = gson.toJson(double.class);
//                    SharedPrefsManager.putDouble(sharedPreferences.edit(), "quizScore", percentScore);
                    sharedPreferences.edit().commit();

                    // calculate and save score
                    Global.score = score();

                    // Add score to DB
                    ScoreDBManager scoreDBManager = new ScoreDBManager(getBaseContext(), ScoreDBManager.DB_NAME);
                    scoreDBManager.setQuizScore(Global.selectedQuiz, Global.loggedInUserName, Global.score);
                    scoreDBManager.close();

                    // add user to high scorers
                    if(Global.score >= 100.0f){
                        QuizDBManager quizDBManager = new QuizDBManager(getBaseContext(), QuizDBManager.DB_NAME);
                        quizDBManager.setFullScoreName(Global.selectedQuiz, Global.userNameLastName);
                        quizDBManager.close();
                    }


                    Log.i(LOG_TAG, "You scored: " + Global.score + "Correct: " + correct + "Total: " + quizSelections.size());
                    startActivity(new Intent(v.getContext(), ResultsQuizActivity.class));
                }else{
                    getNextWord(wordNumber);
                    displayAnswer.setVisibility(View.GONE);
                    // Reset our selection
                    selectedAnswer = Selection.RESET.ordinal();
                }

            }
        });
    }
}

