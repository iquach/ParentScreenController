package com.example.cmsc436.cmsc436project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private TextView lockStatus;
    private AlarmManager alarmManager;
    private ArrayList<RuleSet> ruleSets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lockStatus = (TextView) findViewById(R.id.onOff);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(MainActivity.this, LockOptionReceiver.class);
        SharedPreferences setting = getSharedPreferences("PREFS", 0);
        String switcher = setting.getString("lockStatus", "");
        setStatus(switcher);

        try {
            ruleSets = RuleSetList.retrieveRuleSet(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Button activate = (Button) findViewById(R.id.activate_rules);
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ruleSets.isEmpty()){
                    Toast.makeText(MainActivity.this, "You didn't make a ruleset.", Toast.LENGTH_SHORT).show();
                }else{
                    PendingIntent pending_start;
                    PendingIntent pending_stop;
                    Intent startIntent = new Intent(MainActivity.this, LockOptionReceiver.class);
                    Intent stopIntent = new Intent(MainActivity.this, LockOptionReceiver.class);
                    Calendar startTime = new GregorianCalendar();
                    Calendar endTime = new GregorianCalendar();
                    String startString = ruleSets.get(0).getStartTime();
                    String endString = ruleSets.get(0).getEndTime();

                    String[] startArr = startString.split(":");
                    String[] endArr = endString.split(":");

                    startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startArr[0]));
                    startTime.set(Calendar.MINUTE, Integer.parseInt(startArr[1]));

                    endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endArr[0]));
                    endTime.set(Calendar.MINUTE, Integer.parseInt(endArr[1]));

                    pending_start = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    pending_stop = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    startIntent.putExtra("status", "start");
                    stopIntent.putExtra("status", "stop");

                    Toast.makeText(MainActivity.this, "Your ruleset will start at " + startString, Toast.LENGTH_SHORT).show();

                    setStatus("Lock Active");

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_DAY, startTime.getTimeInMillis(), pending_start);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_DAY, startTime.getTimeInMillis(), pending_stop);
                }
            }
        });

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences setting = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.remove("switcher");
                editor.remove("lockStatus");
                editor.commit();
                editor.putString("switcher", "true");
                editor.putString("lockStatus","Lock Active");
                editor.apply();
                intent.putExtra("status", "start");
                sendBroadcast(intent);
                String status = setting.getString("lockStatus", "");
                setStatus(status);
            }
        });

        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences setting = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.remove("switcher");
                editor.remove("lockStatus");
                editor.commit();
                editor.putString("switcher", "false");
                editor.putString("lockStatus", "Lock Deactivated");
                editor.apply();
                intent.putExtra("status", "stop");
                sendBroadcast(intent);
                String status = setting.getString("lockStatus", "");

                setStatus(status);
            }
        });
    }

    private void setStatus(String s) {
        lockStatus.setText(s);
    }

    public void ruleSet(View view) {
        Intent intent = new Intent(this, ViewRuleSets.class);
        startActivity(intent);
    }

    public void apps(View view) {
        Intent intent = new Intent(this, ListAppActivity.class);
        startActivity(intent);
    }

    public void password_reset(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
