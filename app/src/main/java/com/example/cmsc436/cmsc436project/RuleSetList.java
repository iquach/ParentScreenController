package com.example.cmsc436.cmsc436project;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jitesh on 11/17/2017.
 */
public class RuleSetList implements Serializable{
    private static ArrayList<RuleSet> ruleSets = new ArrayList<>();
    private static int counter = 0;
    private static final String key = "Global Rule Set";

    public static ArrayList<RuleSet> getRuleSets() {
        return ruleSets;
    }

    public static void setRuleSets(ArrayList<RuleSet> ruleSetsIn) {
        ruleSets = ruleSetsIn;
    }

    public static void addRuleSet(String startTimeIn, String endTimeIn,String userStartIn,
                                  String userEndIn ) {
        RuleSet ruleSet = new RuleSet(counter, startTimeIn, endTimeIn, userStartIn, userEndIn);
        ruleSets.add(ruleSet);
        counter++;
    }

    public static int getRuleSetId(String startTimeIn, String endTimeIn) {
        for (int i = 0; i < ruleSets.size(); i++) {
            if (ruleSets.get(i).getStartTime().equals(startTimeIn) &&
                    ruleSets.get(i).getEndTime().equals(endTimeIn)) {
                return ruleSets.get(i).getId();
            }
        }

        return -1;
    }

    public static int getRuleSetIndex(String startTimeIn, String endTimeIn, ArrayList<String> daysOfWeekIn,
                            String frequencyIn) {
        for (int i = 0; i < ruleSets.size(); i++) {
            if (ruleSets.get(i).getStartTime().equals(startTimeIn) &&
                    ruleSets.get(i).getEndTime().equals(endTimeIn)) {
                return i;
            }
        }

        return -1;
    }

    private static boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(String itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }

    public static RuleSet getRuleSet(int id) {
        for (int i = 0; i < ruleSets.size(); i++) {
            if (ruleSets.get(i).getId() == id) {
                return ruleSets.get(i);
            }
        }

        return null;
    }

    public static RuleSet getRuleSetAt(int index) {
        return ruleSets.get(index);
    }

    public static void deleteRuleSet(int id) {
        for (int i = 0; i < ruleSets.size(); i++) {
            if (ruleSets.get(i).getId() == id) {
                ruleSets.remove(i);
            }
        }
    }

    public static void deleteRuleSetAt(int index){
        ruleSets.remove(index);
    }

    public static void saveRuleSet(Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ruleSets);
        oos.close();
        fos.close();
    }

    public static ArrayList<RuleSet> retrieveRuleSet(Context context) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<RuleSet> ruleSets = (ArrayList<RuleSet>) ois.readObject();

        if (ruleSets.isEmpty()) {
            return null;
        }

        return ruleSets;
    }
}
