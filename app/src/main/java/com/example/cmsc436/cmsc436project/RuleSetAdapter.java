package com.example.cmsc436.cmsc436project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jitesh on 11/17/2017.
 */
public class RuleSetAdapter extends ArrayAdapter<RuleSet> {

    ArrayList<RuleSet> ruleSets;

    public RuleSetAdapter(Context context, ArrayList<RuleSet> ruleSetsIn) {
        super(context, 0, ruleSetsIn);

        ruleSets = ruleSetsIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RuleSet ruleSet = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ruleset, parent, false);
        }

        // Lookup view for data population
        TextView time = (TextView) convertView.findViewById(R.id.time);

        // Populate the data into the template view using the data object
        time.setText(ruleSet.getUserStart() + " - " + ruleSet.getUserEnd());

        // Return the completed view to render on screen
        return convertView;
    }

    public RuleSet getItem(int position){
        return ruleSets.get(position);
    }
}