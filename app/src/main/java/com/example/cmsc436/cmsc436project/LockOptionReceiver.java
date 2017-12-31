package com.example.cmsc436.cmsc436project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by JeanClaude on 11/28/2017.
 */

public class LockOptionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences setting = context.getSharedPreferences("PREFS", 0);
        String switcher = setting.getString("switcher", "");

        if(switcher.equals("false")){
            //Toast.makeText(context, "Deactivated ", Toast.LENGTH_LONG).show();
            Intent service = new Intent(context, StartAppService.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, service, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(sender);
        }
        else {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent service = new Intent(context, StartAppService.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 0, pendingIntent);
            context.startService(service);
        }
    }
}
