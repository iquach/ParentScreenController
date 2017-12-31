package com.example.cmsc436.cmsc436project;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ivan on 11/28/17.
 */

public class StartAppService extends Service {

    private static final String TAG = "Stuff";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Context context = getApplicationContext();
        SharedPreferences setting = getSharedPreferences("PREFS", 0);
        HashSet<String> temp = new HashSet<String>();
        HashSet<String> lockedApps = (HashSet<String>) setting.getStringSet("hashSet", temp);
        String appsInForeground = printForegroundTask();
        PackageManager packageManager = getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(appsInForeground, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = (String) packageManager.getApplicationLabel(applicationInfo);
        if (lockedApps.contains(appName)) {
            showHomeScreen();
            Toast.makeText(this, appName +" is locked." , Toast.LENGTH_LONG).show();

        }else{
            //Toast.makeText(this, "App in foreground: " + appsInForeground, Toast.LENGTH_LONG).show();
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("");
    }

    //  This grabs all the apps running in the foreground
//    https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#19852713
    private String printForegroundTask() {
        String currentApp = "NULL";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        Log.e("AppLockerService", "Current App in foreground is: " + currentApp);
        return currentApp;
    }

    public boolean showHomeScreen(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return true;
    }

}