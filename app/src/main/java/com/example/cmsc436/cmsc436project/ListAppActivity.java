package com.example.cmsc436.cmsc436project;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ListAppActivity extends ListActivity {

    private PackageManager packageManager;
    private List<PackageInfo> appList;
    private List<PackageInfo> allApps, installedApps;
    private HashSet<String> defaultList, lockedAppList;
    private ListAdapter listAdapter;
    private Button lockBtn;
    private Switch switchBtn;
    private SharedPreferences setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_app);
        lockBtn = (Button) findViewById(R.id.saveBtn);
        switchBtn = (Switch) findViewById(R.id.lockSwitch);

        defaultList = new HashSet<String>();
        packageManager = getPackageManager();
        setting = getSharedPreferences("PREFS", 0);
        lockedAppList = (HashSet<String>) setting.getStringSet("Locked", defaultList);

        //Grabs all the apps that are installed
        allApps = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        //Will be used to hold all installed apps
        installedApps = packageManager.getInstalledPackages(0);

        try {
            installedApps.clear();
            for (PackageInfo ele : allApps) {
                if (packageManager.getLaunchIntentForPackage(ele.applicationInfo.packageName) != null) {
                    if (ele.applicationInfo.loadLabel(packageManager).equals("Parental App Lock")) {
                        continue;
                    }
                    installedApps.add(ele);
                    Collections.sort(installedApps, new SortByLabel());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        listAdapter = new ListAdapter(ListAppActivity.this, R.layout.activity_list_app, installedApps);
        setListAdapter(listAdapter);


        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAppActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    class SortByLabel implements Comparator<PackageInfo> {

        @Override
        public int compare(PackageInfo t1, PackageInfo t2) {
            return t1.applicationInfo.loadLabel(packageManager).toString()
                    .compareToIgnoreCase(t2.applicationInfo.loadLabel(packageManager).toString());
        }
    }
}
