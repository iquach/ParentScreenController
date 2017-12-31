package com.example.cmsc436.cmsc436project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText password, confirmPassword, email, question, answer;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        password = (EditText) findViewById(R.id.passwordString);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordString);
        email = (EditText) findViewById(R.id.emailString);
        question = (EditText) findViewById(R.id.questionString);
        answer = (EditText) findViewById(R.id.answerString);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        Toast.makeText(this, "Please turn on usage access for this app", Toast.LENGTH_LONG).show();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 =  password.getText().toString();
                String pass2 =  confirmPassword.getText().toString();
                String userEmail =  email.getText().toString();
                //edited
                String q = question.getText().toString();
                String ans = answer.getText().toString();

                if (pass1.equals("") || pass2.equals("")){
                    Toast.makeText(CreateAccountActivity.this, "No password entered", Toast.LENGTH_LONG).show();
                }else if (email.equals("")) {
                    Toast.makeText(CreateAccountActivity.this, "No email entered", Toast.LENGTH_LONG).show();
                }else if (question.equals("")){
                    Toast.makeText(CreateAccountActivity.this, "No question entered", Toast.LENGTH_LONG).show();
                }else if (answer.equals("")){
                    Toast.makeText(CreateAccountActivity.this, "No answer entered", Toast.LENGTH_LONG).show();
                }else{
                    if (pass1.equals(pass2)){
                        HashSet<String> appLockedState = new HashSet<String>();
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", pass1);
                        editor.putString("email", userEmail);
                        editor.putString("question", q);
                        editor.putString("answer", ans);
                        editor.putStringSet("hashSet", appLockedState);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CreateAccountActivity.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
