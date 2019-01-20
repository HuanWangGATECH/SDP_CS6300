package edu.gatech.seclass.sdpvocabquiz.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.activities.DashboardActivity;
import edu.gatech.seclass.sdpvocabquiz.activities.PracticeQuizActivity;
import edu.gatech.seclass.sdpvocabquiz.activities.PreviewQuizActivity;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.SharedPrefsManager;

import static edu.gatech.seclass.sdpvocabquiz.data.Global.*;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardAdapter.QuizViewHolder> {

    public static final String LOG_TAG = "QuizCardAdapter";

    public String currentUser;
    ScoreDBManager scoreDBManager;
    private List<Quiz> quizList;

    // this is useful for the test methods
    public List<Quiz> getQuizList(){
        return quizList;
    }

    // this is useful for the test methods asserting
    public boolean quizWithNameExist(String uniqueName){
        for(Quiz q: quizList){
            if(q.getUniqueName().equals(uniqueName)){
                return true;
            }
        }
        return false;
    }

    public QuizCardAdapter(Context context, List<Quiz> quizList) {
        this.scoreDBManager = new ScoreDBManager(context, ScoreDBManager.DB_NAME);
        this.quizList = quizList;
    }


    @Override
    public int getItemCount() {
        return quizList.size();
    }

    @Override
    public void onBindViewHolder(final QuizViewHolder quizViewHolder, int i) {
        final Quiz qi = quizList.get(i);
        quizViewHolder.vQuizName.setText(qi.getUniqueName());

        // If logged in user is the author.
        if(qi.getAuthor().equals(Global.loggedInUserName)){
            // Then show the Quill Icon for them.
            quizViewHolder.vIconAuthor.setVisibility(View.VISIBLE);

            // Set high score (on the right of the quiz list) to be a long Hyphen as they cannot take quizzes.
            quizViewHolder.vQuizScore.setText("---");
        }else{
            // If not author then format score
            Score score = scoreDBManager.getQuizScore(Global.loggedInUserName, qi.getUniqueName());
            if(score.getNewScoreDate() != null){
                quizViewHolder.vQuizScore.setText(String.format("%.2f%%", score.getPercentage()));
            }else{
                quizViewHolder.vQuizScore.setText(String.format("TBD", score.getPercentage()));
            }

        }

        quizViewHolder.vQuizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Log.i(LOG_TAG, "Clicked on a quiz " + qi.getUniqueName());
                Log.i(LOG_TAG, "Logged in as " + currentUser + " author is" + qi.getAuthor());
                Intent i = new Intent(v.getContext(), PreviewQuizActivity.class);

                // Using an object with Shared Preferences Source: https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(qi);
                editor.putString("selectedQuiz", json);
                editor.commit();

                //start new activity
                context.startActivity(i);
            }
        });
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_quiz, viewGroup, false);

        return new QuizViewHolder(itemView);
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {

        protected CardView vQuizCard;
        protected TextView vQuizName;
        protected TextView vQuizScore;
        protected ImageView vIconAuthor;

        public QuizViewHolder(View v) {
            super(v);

            vQuizCard = (CardView) v.findViewById(R.id.quizCard);
            vQuizName =  (TextView) v.findViewById(R.id.cardQuizName);
            vQuizScore = (TextView)  v.findViewById(R.id.cardQuizScore);
            vIconAuthor = (ImageView) v.findViewById(R.id.iconAuthor);
        }
    }
}