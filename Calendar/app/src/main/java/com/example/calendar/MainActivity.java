package com.example.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Credentials;
import android.net.wifi.hotspot2.pps.Credential;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login, forgot_password, closepopup, new_account;
    String TAG = "MAIN_ACTIVITY";
    EditText usernameTxt, passwordTxt;
    private PopupWindow popwindow, popwindow2;
    public static final String PREFS_NAME = "CredentialsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginButton);
        forgot_password = (Button) findViewById(R.id.forgot_password);
        new_account = (Button) findViewById(R.id.create_account_button);
        usernameTxt = (EditText) findViewById(R.id.editName);
        passwordTxt = (EditText) findViewById(R.id.editPassword);
        final SharedPreferences credentials = getSharedPreferences(PREFS_NAME,0);

        //OnClickListener Login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                //method to check if username and password are correct
                validate(username,password);
            }
        });

        //OnClickListener Forgot Password Button
        forgot_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createForgotPopup();
            }
        });

        //OnClickListener Create Account Button
        new_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createAccountPopup();
            }
        });

    }

    private void createAccountPopup() {
        try {
            //create popup window for creating a new account
            LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.account_popup, (ViewGroup) findViewById(R.id.account_popup_element));
            popwindow = new PopupWindow(layout, 800, 870, true);
            popwindow.showAtLocation(layout, Gravity.CENTER,0,0);

            final EditText code = (EditText) layout.findViewById(R.id.secret_code);
            final EditText name = (EditText) layout.findViewById(R.id.account_username);
            final EditText pass = (EditText) layout.findViewById(R.id.account_pass);
            Button submit_button = (Button) layout.findViewById(R.id.submit_button);
            final SharedPreferences credentials = getSharedPreferences(PREFS_NAME,0);
            final SharedPreferences.Editor editor = credentials.edit();

            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (code.getText().toString().equals("CMSC436")) {
                        editor.putString(name.getText().toString(), pass.getText().toString());
                        editor.commit();

                        Log.d(TAG, "username set " + credentials.getAll().containsKey(name.getText().toString()));
                        Log.d(TAG, "password is " + credentials.getString(name.getText().toString(), ""));

                        Toast.makeText(getApplicationContext(), "Account created successfully",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        popwindow.dismiss();
                    }
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void createForgotPopup() {
        try {
            LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup, (ViewGroup) findViewById(R.id.popup_element));
            popwindow2 = new PopupWindow(layout, 1000, 1070, true);
            popwindow2.showAtLocation(layout, Gravity.CENTER,0,0);

            Button forgot_submit = (Button) layout.findViewById(R.id.forgot_submit);
            final EditText forgot_pass = (EditText) layout.findViewById(R.id.forgot_pass);
            final EditText forgot_name = (EditText) layout.findViewById(R.id.forgot_name);
            final EditText forgot_code = (EditText) layout.findViewById(R.id.forgot_code);

            final SharedPreferences credentials = getSharedPreferences(PREFS_NAME,0);
            final SharedPreferences.Editor editor = credentials.edit();

            forgot_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (forgot_code.getText().toString().equals("CMSC436")) {
                        if (credentials.contains(forgot_name.getText().toString())) {
                            editor.putString(forgot_name.getText().toString(), forgot_pass.getText().toString());
                            editor.commit();
                            Log.d(TAG, "password is changed to " + credentials.getString(forgot_name.getText().toString(), ""));

                            Toast.makeText(getApplicationContext(), "Password changed successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void validate(String username,String password){
        SharedPreferences creds = getSharedPreferences(PREFS_NAME,0);
        Log.d(TAG, creds.getAll().toString());
        if (creds.contains(username)) {
            if (creds.getString(username,"").equals(password)) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No account with that name", Toast.LENGTH_SHORT).show();
        }
    }
}
