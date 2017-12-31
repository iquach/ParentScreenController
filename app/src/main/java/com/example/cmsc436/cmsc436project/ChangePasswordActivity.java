package com.example.cmsc436.cmsc436project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPassword, currentPassword, confirmPassword;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        save = (Button) findViewById(R.id.changePassBtn);
        newPassword = (EditText) findViewById(R.id.newPass);
        currentPassword = (EditText) findViewById(R.id.currentPass);
        confirmPassword = (EditText) findViewById(R.id.reEnterPass);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword1 = newPassword.getText().toString();
                String currentPassword2 = currentPassword.getText().toString();
                String confirmPassword2 = confirmPassword.getText().toString();

                if(newPassword1.equals("") || currentPassword2.equals("") || confirmPassword2.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences settings = getSharedPreferences("PREFS", 0);
                    String currPass = settings.getString("password", "");
                    if(currPass.equals(currentPassword2)) {
                        if(newPassword1.equals(confirmPassword2)) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.remove("password");
                            editor.commit();
                            editor.putString("password", newPassword1);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(ChangePasswordActivity.this, "New passwords do not match", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
