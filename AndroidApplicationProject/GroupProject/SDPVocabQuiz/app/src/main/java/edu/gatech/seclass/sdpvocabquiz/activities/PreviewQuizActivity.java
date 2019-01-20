package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.SharedPrefsManager;

//Todo: Front End Logic for Practice Quiz
public class PreviewQuizActivity extends AppCompatActivity {

    public static String LOG_TAG = "PracticeQuizActivity";
    private  boolean isAuthor;
    private Quiz quiz;
    ArrayList<String> perfectScorers;
    List<TextView> perfectScorerTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_quiz);

        Theme.setColorBar(this);

        ImageButton backButton = findViewById(R.id.previewQuizBackButton);
        TextView title = findViewById(R.id.previewQuizTitle);
        TextView author = findViewById(R.id.previewQuizAuthor);
        TextView description = findViewById(R.id.previewQuizDescription);

        TextView yourFirstScore = findViewById(R.id.previewQuizScoreFirst);
        TextView yourHighestScore = findViewById(R.id.previewQuizScoreHighest);
        TextView perfectScorer1 = findViewById(R.id.previewQuizScorePerfect1);
        TextView perfectScorer2 = findViewById(R.id.previewQuizScorePerfect2);
        TextView perfectScorer3 = findViewById(R.id.previewQuizScorePerfect3);
        perfectScorerTextViews  = new ArrayList<TextView>();
        perfectScorerTextViews.add(perfectScorer1);
        perfectScorerTextViews.add(perfectScorer2);
        perfectScorerTextViews.add(perfectScorer3);

        Button practiceQuizButton = findViewById(R.id.previewPracticeQuizButton);

        // Retrieve JSON objected from previously selected quiz
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("selectedQuiz",  "");
        quiz = gson.fromJson(json, Quiz.class);

        title.setText(quiz.getUniqueName());
        author.setText(quiz.getAuthor());
        description.setText(quiz.getDescription());

        // check and set if is author
        isAuthor = (quiz.getAuthor().equals(Global.loggedInUserName)) ? true: false;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on back");
                startActivity(new Intent(v.getContext(), DashboardActivity.class));
            }
        });

        if(!isAuthor){
            practiceQuizButton.setBackgroundResource(R.color.themedHighlightYellow);
            ScoreDBManager scoreDBManager = new ScoreDBManager(this, ScoreDBManager.DB_NAME);
            Score score = scoreDBManager.getQuizScore(Global.loggedInUserName, quiz.getUniqueName());
            scoreDBManager.close();
            Log.i(LOG_TAG, " First Score " + score.getFirstScore() + " " + score.getFirstScoreDate());
            Log.i(LOG_TAG, " Highest Score " + score.getHighestScore() + " " + score.getHighestScoreDate());
            // If there is no date for first score then show not yet taken otherwise show score and date
            if(score.getFirstScoreDate() == null){
                yourFirstScore.setText("Not yet taken");
            }else{
                yourFirstScore.setText("Scored " + score.getFirstScore() +"% on " + score.getFirstScoreDate());
            }
            // If there is no date for first score then show not yet taken otherwise show score and date
            if(score.getHighestScoreDate() == null){
                yourHighestScore.setText("Not yet taken");
            }else{
                yourHighestScore.setText("Scored " + score.getHighestScore() + "% on " + score.getNewScoreDate());
            }

        }else{
            // If user is an author then they cannot have a score and display Author of this quiz
            practiceQuizButton.setBackgroundResource(R.color.themedHighlightYellowDisabled);
            yourFirstScore.setText("Cannot Score Your Own Quiz");
            yourHighestScore.setText("Cannot Score Your Own Quiz");
        }

        // Populate Perfect Scorer List
        QuizDBManager quizDBManager = new QuizDBManager(getBaseContext(), QuizDBManager.DB_NAME);
        perfectScorers = quizDBManager.getFullScoreStudent(quiz.getUniqueName());
        quizDBManager.close();

        for (int i = 0; i < perfectScorers.size(); i ++){
            if(perfectScorers.get(0).isEmpty()){
                perfectScorerTextViews.get(i).setText("--------");
            }else{
                perfectScorerTextViews.get(i).setText(perfectScorers.get(i));
            }
        }

        practiceQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAuthor){
                    Global.selectedQuiz = quiz.getUniqueName();
                    startActivity(new Intent(v.getContext(), PracticeQuizActivity.class));
                }else{
                    Toast.makeText(getBaseContext(), getString(R.string.msg_author_cannot_take_quiz), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
