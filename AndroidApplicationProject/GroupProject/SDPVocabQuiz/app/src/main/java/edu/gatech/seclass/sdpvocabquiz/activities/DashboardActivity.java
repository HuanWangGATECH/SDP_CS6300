package edu.gatech.seclass.sdpvocabquiz.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.adapters.QuizCardAdapter;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.SharedPrefsManager;
import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;

import static edu.gatech.seclass.sdpvocabquiz.data.Global.*;

public class DashboardActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Dashboard";

    QuizDBManager quizDBManager;
    //List<Quiz> quizzes;
    ArrayList<String> quizNamesTakenByUser;
    List<Quiz> quizzes;

    QuizCardAdapter quizCardAdapter;

    TextView loggedInAsText;
    TextView logoutLink;
    String username;
    RecyclerView recyclerView;
    FloatingActionButton addQuizButton;

    Quiz newQuiz;

    public void removeQuizAt(int position){
        // remove visually from adapter
        quizzes.remove(position);
        quizCardAdapter.notifyItemRemoved(position);
    }
    
    public void showDeleteDialog(final Activity activity, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Remove this Quiz?");
        builder.setMessage("You are about to permanently remove this quiz and its associated scores. Are you sure you want to proceed?");

        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove quiz scores associate to quiz
                ScoreDBManager scoreDBManager = new ScoreDBManager(activity, ScoreDBManager.DB_NAME);
                scoreDBManager.deleteQuizScore(quizzes.get(position).getUniqueName());
                scoreDBManager.close();
                // Get the position of the item to be deleted.
                removeQuizAt(position);
                quizCardAdapter.notifyItemChanged(position);
                Toast.makeText(getBaseContext(), R.string.toast_delete_quiz_success, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel and dismiss dialog.
                // refresh adapter to prevent hiding the item from UI.
                quizCardAdapter.notifyItemChanged(position);
            }
        });
        builder.create();
        builder.show();
    }

    public void showFailDialog(final Activity activity, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Could Not Remove Quiz");
        builder.setMessage("Only the quiz author can remove this quiz.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizCardAdapter.notifyItemChanged(position);
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Theme.setColorBar(this);

        quizDBManager = new QuizDBManager(this, QuizDBManager.DB_NAME);

        // Assign our elements with their Ids.
        loggedInAsText = findViewById(R.id.loggedUserDashHeader);
        recyclerView = (RecyclerView) findViewById(R.id.quizCardList);
        logoutLink = findViewById(R.id.logoutLink);
        addQuizButton = findViewById(R.id.fab_add_quiz);

        // Greet user
        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString(SharedPrefsManager.PREF_USERNAME, "");
        Global.loggedInUserName = savedUsername;
        StudentDBManager studentDBManager = new StudentDBManager(getBaseContext(), StudentDBManager.DB_NAME);
        Global.userNameLastName = studentDBManager.getUserNameFullName(savedUsername);
        studentDBManager.close();

        loggedInAsText.setText("Welcome " + Global.userNameLastName);

        // Initialize our quiz array list for the first time.
        if(quizzes == null){
            quizzes = new ArrayList<>();
        }

        // Load our list from the data base
        //ArrayList<Quiz> quizzesFromDB = quizDBManager.getAllQuiz();
        //quizzes.addAll(quizzesFromDB);

        // Load list of quizzes taken by user
        ScoreDBManager scoreDBManager = new ScoreDBManager(getBaseContext(), ScoreDBManager.DB_NAME);
        quizNamesTakenByUser = scoreDBManager.getUserQuizListbyScoreTimeOrder(Global.loggedInUserName);
        scoreDBManager.close();
        QuizDBManager quizDBManager = new QuizDBManager(getBaseContext(), QuizDBManager.DB_NAME);
        List<Quiz> quizzesFromDB = quizDBManager.getSatisticQuizList(Global.loggedInUserName, quizNamesTakenByUser);
        quizDBManager.close();

        quizzes.addAll(quizzesFromDB);

        // Setup our Recycler for the Quiz Card List
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        // Assign our Quiz List to the Quiz Adapter View and populate it
        quizCardAdapter = new QuizCardAdapter(this,quizzes);
        recyclerView.setAdapter(quizCardAdapter);

        // Attach an Item swipe event
        ItemTouchHelper.SimpleCallback simpleTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir){
                Log.i(LOG_TAG,"Swiped on element " + viewHolder.getAdapterPosition());
                Context context = recyclerView.getContext();
                // Remove it from db
                QuizDBManager quizDBManager = new QuizDBManager(context, QuizDBManager.DB_NAME);
                quizDBManager.close();
                boolean canDelete = quizDBManager.deleteQuiz(Global.loggedInUserName,quizzes.get(viewHolder.getAdapterPosition()).getUniqueName());
                if(canDelete){
                    showDeleteDialog((Activity) recyclerView.getContext(), viewHolder.getAdapterPosition());
                }else{
                    showFailDialog((Activity) recyclerView.getContext(), viewHolder.getAdapterPosition());
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Add out click event for Logout Link
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Need to clear the activity stack so when we logout we cant hit "Back button" to go to previous user's activities
                // Need to implement this: https://stackoverflow.com/questions/3007998/on-logout-clear-activity-history-stack-preventing-back-button-from-opening-l
                Log.i(LOG_TAG, "Clicked on Logout Link");
                Global.loggedInUserName = "";
                Global.userNameLastName = "";
                Global.selectedQuiz = "";
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });

        // Add click event for our Quiz Add fab
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Clicked on FAB to Add Quiz");
                startActivity(new Intent(v.getContext(), CreateQuizActivity.class));
            }
        });

    }
}
