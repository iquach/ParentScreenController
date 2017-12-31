package com.example.cmsc436.cmsc436project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    private EditText passwrd;
    private Button button;
    private Button forgotBtn;

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        //load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");


        passwrd = (EditText) findViewById(R.id.pass);
        button = (Button) findViewById(R.id.enterButton);
        forgotBtn = (Button) findViewById(R.id.forgotPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = passwrd.getText().toString();

                if (pass.equals(password)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(EnterPasswordActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
