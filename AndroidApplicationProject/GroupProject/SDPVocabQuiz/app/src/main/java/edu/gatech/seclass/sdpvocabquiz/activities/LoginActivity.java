package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.utils.*;
import static edu.gatech.seclass.sdpvocabquiz.utils.SQLUtils.*;

public class LoginActivity extends AppCompatActivity {

    public final String LOG_TAG ="LoginActivity";
    // used for testing
    public final Student studentTest = new Student("Test", "test@mail.com", "", "", "Computer Science", "Freshman");
    public final Student studentUser = new Student("User", "user@gmail.com", "First", "Last", "Computer Science", "Junior");
    public final Student studentAnotherUser = new Student("AnotherUser", "AnotherUser@gmail.com", "Another", "User", "Electrical Engineering", "Freshman");


    SQLiteDatabase database;
    QuizDBManager quizdb = new QuizDBManager(this, QuizDBManager.DB_NAME);
    EditText usernameInput;
    Button loginButton;
    TextView newUserLink;
    CheckBox rememberCheckBox;

    public void login(){
        String usernameText = usernameInput.getText().toString();
        if(!userNameExistsInDatabase(this, usernameText)){
            usernameInput.setError(getString(R.string.error_username_not_exist));
        }else{
            Toast.makeText(getBaseContext(), getString(R.string.msg_logging_in), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
        }
    }

    //TODO: Move this to use backend calls
    // Initialize or Load
    private void loadDatabase(){
        // create database named Students -> may change to one database containing tables rather than database for each object
        database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);
        // Uncomment below to clear database when debugging, run app then re-comment it.
        // database.execSQL("DROP TABLE students");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + Student.DB_TABLE_NAME +" (username VARCHAR, email VARCHAR, firstname VARCHAR, lastname VARCHAR, major VARCHAR, seniority VARCHAR)");
        // Add some users to test
        StudentDBManager studentDBManager = new StudentDBManager(getBaseContext(), StudentDBManager.DB_NAME);
        studentDBManager.addStudent(studentTest);
        studentDBManager.addStudent(studentUser);
        studentDBManager.addStudent(studentAnotherUser);
        studentDBManager.close();

    }

    private void clearDatabase(){
        // Clear database each run for testing
        database.execSQL("DROP TABLE students");
    }

    private void loadPreviousUsername(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
        // If nothing then enter empty string
        String savedUsername = sharedPreferences.getString(SharedPrefsManager.PREF_USERNAME, "");
        Log.i(LOG_TAG, "Username on startup is" + savedUsername);
        Boolean savedRememberMe = sharedPreferences.getBoolean(SharedPrefsManager.PREF_REMEMBER_ME, false);

        if(savedRememberMe){
            usernameInput.setText(savedUsername);
            rememberCheckBox.setChecked(true);
        }
        else{
            usernameInput.setText("");
            rememberCheckBox.setChecked(false);
        }
    }

    private void savePreviousUsername(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPrefsManager.MY_PREFERNCES, Context.MODE_PRIVATE);
        String usernameText = usernameInput.getText().toString();
        if(rememberCheckBox.isChecked() && userNameExistsInDatabase(this, usernameText)) {
            // save username if valid
            sharedPreferences.edit().putBoolean(SharedPrefsManager.PREF_REMEMBER_ME, true).apply();

        }else{
            // do not save username
            sharedPreferences.edit().putBoolean(SharedPrefsManager.PREF_REMEMBER_ME, false).apply();
        }
        sharedPreferences.edit().putString(SharedPrefsManager.PREF_USERNAME, usernameText).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Theme.setColorBar(this);

        // link to xml elements
        usernameInput = findViewById(R.id.editTextUsername);
        loginButton = findViewById(R.id.buttonLogin);
        newUserLink = findViewById(R.id.newUserLink);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);

        // get username from previous sessions if Remember Me enabled
        loadPreviousUsername();
        loadDatabase();

        usernameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    savePreviousUsername();
                    login();
                    return  true;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreviousUsername();
                login();
            }
        });

        newUserLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(registration);
            }
        });
    }

}
