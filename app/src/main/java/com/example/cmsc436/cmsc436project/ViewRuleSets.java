package com.example.cmsc436.cmsc436project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class ViewRuleSets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rule_sets);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Construct the data source
        ArrayList<RuleSet> ruleSets = null;
        try {
            ruleSets = RuleSetList.retrieveRuleSet(this);
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        if (ruleSets != null) {
            RuleSetList.setRuleSets(ruleSets);
        } else {
            ruleSets = RuleSetList.getRuleSets();
        }

        // Create the adapter to convert the array to views
        final RuleSetAdapter adapter = new RuleSetAdapter(this, ruleSets);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RuleSet item = adapter.getItem(position);

                Intent intent = new Intent(ViewRuleSets.this,ModifyRuleSet.class);
                intent.putExtra("ID", item.getId());
                startActivity(intent);
            }
        });
    }

    public void addNewRuleSet(View view) {
        Intent intent = new Intent(ViewRuleSets.this, ModifyRuleSet.class);
        intent.putExtra("ID", -1);
        startActivity(intent);
    }

    public void goHome(View view) {
        Intent intent = new Intent(ViewRuleSets.this, MainActivity.class);
        startActivity(intent);
    }
}
