package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.SharedPrefsManager;

//Todo: Front End Logic for Practice Quiz
public class ResultsQuizActivity extends AppCompatActivity {

    public static String LOG_TAG = "ResultsQuizActivity";

    ScoreDBManager scoreDBManager;
    TextView quizTitle;
    TextView userScore;
    TextView userScoreTime;
    TextView userHighestScore;
    TextView userFirstScore;
    TextView perfectScorer1;
    TextView perfectScorer2;
    TextView perfectScorer3;
    List<TextView> perfectScorerTextViews;
    ImageButton backButton;
    Quiz quiz;
    ArrayList<String> perfectScorers;

    private SharedPreferences sharedPreferences;

    void display(){
        quizTitle.setText(quiz.getUniqueName());
        Score score = scoreDBManager.getQuizScore(Global.loggedInUserName, quiz.getUniqueName());
        userScore.setText("You Scored: " + String.format("%.2f%%", score.getPercentage()));
        userScoreTime.setText(" " + score.getNewScoreDate().toString());
        userFirstScore.setText("Scored " + score.getFirstScore() + "% on " + score.getNewScoreDate().toString());
        userHighestScore.setText("Scored " + score.getHighestScore() + "% on " + score.getNewScoreDate().toString());

        for (int i = 0; i < perfectScorers.size(); i ++){
            if(perfectScorers.get(0).isEmpty()){
                perfectScorerTextViews.get(i).setText("--------");
            }else{
                perfectScorerTextViews.get(i).setText(perfectScorers.get(i));
            }
        }

    }

    void clearQuizInPreferences(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedQuiz", null);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_results);

        Theme.setColorBar(this);

        scoreDBManager = new ScoreDBManager(this, ScoreDBManager.DB_NAME);

        quizTitle = findViewById(R.id.previewQuizTitle);
        userScore = findViewById(R.id.resultScore);
        userScoreTime = findViewById(R.id.resultScoreDate);
        userHighestScore = findViewById(R.id.previewQuizScoreHighest);
        userFirstScore = findViewById(R.id.previewQuizScoreFirst);
        perfectScorer1 = findViewById(R.id.previewQuizScorePerfect1);
        perfectScorer2 = findViewById(R.id.previewQuizScorePerfect2);
        perfectScorer3 = findViewById(R.id.previewQuizScorePerfect3);
        perfectScorerTextViews  = new ArrayList<TextView>();
        perfectScorerTextViews.add(perfectScorer1);
        perfectScorerTextViews.add(perfectScorer2);
        perfectScorerTextViews.add(perfectScorer3);
        backButton = findViewById(R.id.viewResultsBack);

        // Retrieve JSON objected from previously selected quiz
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
        String serializedQuiz = sharedPreferences.getString("selectedQuiz",  "");
        // If not defaults then save the scores
        if(Global.score!=-1.0f && !serializedQuiz.isEmpty()){
            //userScore.setText("You Scored: " + String.format("%.2f%%", percentageScore));
            quiz = gson.fromJson(serializedQuiz, Quiz.class);
            //quizTitle.setText(quiz.getUniqueName());

            scoreDBManager.setQuizScore(quiz.getUniqueName(), Global.loggedInUserName, Global.score);
            scoreDBManager.close();
            QuizDBManager quizDBManager = new QuizDBManager(getBaseContext(), QuizDBManager.DB_NAME);
            perfectScorers = quizDBManager.getFullScoreStudent(Global.selectedQuiz);
            quizDBManager.close();

            display();

            // Reset globals
            Global.score = -1.0f;
            Global.selectedQuiz = "";
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on back");
                clearQuizInPreferences(v);
                startActivity(new Intent(v.getContext(), DashboardActivity.class));
            }
        });
    }
}
