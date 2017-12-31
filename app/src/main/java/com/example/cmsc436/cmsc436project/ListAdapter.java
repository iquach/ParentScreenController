package com.example.cmsc436.cmsc436project;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JeanClaude on 11/26/2017.
 */

public class ListAdapter extends ArrayAdapter<PackageInfo> {

    private static String TAG = "checking";
    private List<PackageInfo> appList = null;
    private Context context;
    private PackageManager packageManager;
    private boolean[] isChecked;

    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PackageInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.appList = objects;
        packageManager = context.getPackageManager();
        isChecked = new boolean[appList.size()];
    }

    @Override
    public int getCount() {
        return ((null != appList) ? appList.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (null == view){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        PackageInfo data = appList.get(position);

        if (null != data) {
            final TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_package);
            ImageView iconView = (ImageView) view.findViewById(R.id.app_icon);
            final Switch switchBtn = (Switch) view.findViewById(R.id.lockSwitch);

            final String appNameString = packageManager.getApplicationLabel(data.applicationInfo).toString();
            String packageNameStr = data.applicationInfo.packageName;
            Drawable appIcon = packageManager.getApplicationIcon(data.applicationInfo);

            SharedPreferences setting = context.getSharedPreferences("PREFS", 0);
            Set<String> hashM = setting.getStringSet("hashSet", new HashSet<String>());


            if (hashM != null && hashM.size() != 0) {
                Log.i(TAG, "lockedApps is not empty and: " + appNameString);
                if (hashM.contains(appNameString)){
                    isChecked[position] = true;
                }else{
                    isChecked[position] = false;
                }
            }else {
                isChecked[position] = false;
            }

            if (isChecked[position]) {
                switchBtn.setChecked(true);
            }else{
                switchBtn.setChecked(false);
            }

            appName.setText(appNameString);
            packageName.setText(packageNameStr);
            iconView.setImageDrawable(appIcon);

            switchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences settings = context.getSharedPreferences("PREFS", 0);
                    Set<String> hashM = settings.getStringSet("hashSet", new HashSet<String>());
                    if (switchBtn.isChecked()){
                        isChecked[position] = true;
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("hashSet");
                        editor.commit();
                        hashM.add(appNameString);
                        editor.putStringSet("hashSet", hashM);
                        editor.apply();
                    }else{
                        isChecked[position] = false;
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("hashSet");
                        editor.commit();
                        hashM.remove(appNameString);
                        editor.putStringSet("hashSet", hashM);
                        editor.apply();
                    }
                }
            });
        }
        return view;
    }
}
