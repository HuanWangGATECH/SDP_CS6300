package edu.gatech.seclass.sdpvocabquiz.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.Theme;
import edu.gatech.seclass.sdpvocabquiz.data.Student;

import static edu.gatech.seclass.sdpvocabquiz.utils.SQLUtils.*;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameField;
    EditText emailField;
    EditText firstNameField;
    EditText lastNameField;
    Button registerButton;
    ImageButton registerBackButton;
    Spinner majorSpinner;
    Spinner senioritySpinner;

    boolean noError(EditText editText){
        return (editText.getError() != null || editText.getText().length() == 0) ? false : true;
    }

    public void checkUserName(){
        String usernameText = usernameField.getText().toString();

        if(usernameText.isEmpty()){
            usernameField.setError(getString(R.string.error_username_cant_empty));
        }
        else if(!userNameExistsInDatabase(this, usernameText)){
            usernameField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_check, 0);
        }else{
            usernameField.setError(getString(R.string.error_username_in_use));
        }
    }

    public void checkEmail() {
        CharSequence email = emailField.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.error_email_invalid_format));
        }
        else {
            emailField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_check, 0);
            //Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Theme.setColorBar(this);

        usernameField = findViewById(R.id.usernameEditText);
        emailField = findViewById(R.id.emailEditText);
        firstNameField = findViewById(R.id.firstNameTextEdit);
        lastNameField = findViewById(R.id.lastNameTextEdit);
        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        senioritySpinner = (Spinner) findViewById(R.id.senioritySpinner);
        registerButton = findViewById(R.id.registerButton);
        registerBackButton = findViewById(R.id.registerBackButton);

        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this,
                R.array.majors, android.R.layout.simple_spinner_item);
        majorSpinner.setAdapter(majorAdapter);

        ArrayAdapter<CharSequence> seniorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.seniorityLevels, android.R.layout.simple_spinner_item);
        senioritySpinner.setAdapter(seniorityAdapter);

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmail();
            }
            @Override
            public void afterTextChanged(Editable s) {

             }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Force checks prior to registration
                checkUserName();
                checkEmail();

                Student s = new Student(
                        usernameField.getText().toString(),
                        emailField.getText().toString(),
                        firstNameField.getText().toString(),
                        lastNameField.getText().toString(),
                        majorSpinner.getSelectedItem().toString(),
                        senioritySpinner.getSelectedItem().toString()
                );

                if(noError(usernameField) && noError(emailField)) {
                    register((Activity) v.getContext(),s);
                    Toast.makeText(getBaseContext(), getString(R.string.msg_registration_success), Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(login);
                    // remove this activity from back stack
                    finish();
                }else{
                    Toast.makeText(getBaseContext(), getString(R.string.msg_registration_fail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
