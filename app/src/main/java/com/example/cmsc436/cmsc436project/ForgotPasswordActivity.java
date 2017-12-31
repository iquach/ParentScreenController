package com.example.cmsc436.cmsc436project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {
    private String q, ans, password;
    private EditText answer;
    private Button submitB, backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        q = settings.getString("question", "");
        ans = settings.getString("answer", "");
        password = settings.getString("password", "");

        TextView qText = (TextView) findViewById(R.id.question);
        qText.setText(q);

        answer = (EditText) findViewById(R.id.answer);
        submitB = (Button) findViewById(R.id.submit);
        backB = (Button) findViewById(R.id.back);

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAns = answer.getText().toString();

                TextView result = (TextView) findViewById(R.id.result);

                if (userAns.equals(ans)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password: " + password, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Wrong answer for security question.", Toast.LENGTH_LONG).show();
                }

            }
        });
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnterPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}