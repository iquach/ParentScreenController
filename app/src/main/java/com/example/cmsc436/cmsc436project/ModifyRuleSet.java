package com.example.cmsc436.cmsc436project;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ModifyRuleSet extends AppCompatActivity {

    static String startTimeString;
    static String userFriendlyStart;
    public static TextView startTimeView;

    static String endTimeString;
    static String userFriendlyEnd;
    public static TextView endTimeView;

    boolean isNew;
    int ruleSetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_rule_set);

        startTimeView = (TextView) findViewById(R.id.start_time);
        endTimeView = (TextView) findViewById(R.id.end_time);

        ruleSetId = getIntent().getExtras().getInt("ID");

        if (ruleSetId == -1) {
            isNew = true;
        } else {
            isNew = false;
            RuleSet ruleSet = RuleSetList.getRuleSet(ruleSetId);
            startTimeString = ruleSet.getStartTime();
            endTimeString = ruleSet.getEndTime();
            userFriendlyStart = ruleSet.getUserStart();
            userFriendlyEnd = ruleSet.getUserEnd();

            startTimeView.setText(userFriendlyStart);
            endTimeView.setText(userFriendlyEnd);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pickStartTime(View view) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    private static void setStartTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        startTimeString = hour + ":" + min;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class StartTimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setStartTimeString(hourOfDay, minute, 0);

            boolean pm = false;
            String userFriendlyString = "";
            if (hourOfDay > 12) {
                hourOfDay = hourOfDay - 12;
                pm = true;
                if (hourOfDay < 10) {
                    userFriendlyString += "0";
                }
            } else if (hourOfDay == 0) {
                hourOfDay = 12;
            } else if (hourOfDay == 12) {
                pm = true;
            }

            userFriendlyString += hourOfDay + ":";

            if (minute < 10) {
                userFriendlyString += "0";
            }

            userFriendlyString += minute;

            if (pm) {
                userFriendlyString += " PM";
            } else {
                userFriendlyString += " AM";
            }

            userFriendlyStart = userFriendlyString;

            startTimeView.setText(userFriendlyString);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pickEndTime(View view) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    private static void setEndTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        endTimeString = hour + ":" + min;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class EndTimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setEndTimeString(hourOfDay, minute, 0);

            boolean pm = false;
            String userFriendlyString = "";
            if (hourOfDay > 12) {
                hourOfDay = hourOfDay - 12;
                pm = true;
                if (hourOfDay < 10) {
                    userFriendlyString += "0";
                }
            } else if (hourOfDay == 0) {
                hourOfDay = 12;
            } else if (hourOfDay == 12) {
                pm = true;
            }

            userFriendlyString += hourOfDay + ":";

            if (minute < 10) {
                userFriendlyString += "0";
            }

            userFriendlyString += minute;

            if (pm) {
                userFriendlyString += " PM";
            } else {
                userFriendlyString += " AM";
            }

            userFriendlyEnd = userFriendlyString;

            endTimeView.setText(userFriendlyString);
        }
    }

    public void saveRuleSet(View view){

        if (startTimeString == null || endTimeString == null) {
            Toast.makeText(this, "Please Select Start and End Time",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isNew) {
            RuleSetList.deleteRuleSet(ruleSetId);
        }

        RuleSetList.addRuleSet(startTimeString, endTimeString, userFriendlyStart, userFriendlyEnd);
        try {
            RuleSetList.saveRuleSet(this);
        } catch (IOException e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(ModifyRuleSet.this, ViewRuleSets.class);
        startActivity(intent);
    }

    public void deleteRuleSet(View view) {
        if (!isNew) {
            RuleSetList.deleteRuleSet(ruleSetId);

            try {
                RuleSetList.saveRuleSet(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(ModifyRuleSet.this, ViewRuleSets.class);
        startActivity(intent);
    }

    public void cancelRuleSet(View view) {
        Intent intent = new Intent(ModifyRuleSet.this, ViewRuleSets.class);
        startActivity(intent);
    }

}
