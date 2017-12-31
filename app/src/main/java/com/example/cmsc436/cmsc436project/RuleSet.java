package com.example.cmsc436.cmsc436project;

import java.io.Serializable;

/**
 * Created by Jitesh on 11/17/2017.
 */
public class RuleSet implements Serializable{

    private int id;
    private String startTime;
    private String endTime;
    private String userStart;
    private String userEnd;

    public RuleSet(int idIn, String startTimeIn, String endTimeIn, String userStartIn,
                   String userEndIn) {
        id = idIn;
        startTime = startTimeIn;
        endTime = endTimeIn;
        userStart = userStartIn;
        userEnd = userEndIn;
    }

    public int getId() {
        return id;
    }

    public void setStartTime(String startTimeIn) {
        startTime = startTimeIn;
    }

    public String getStartTime() {
        return  startTime;
    }

    public void setEndTime(String endTimeIn) {
        endTime = endTimeIn;
    }

    public String getEndTime() {
        return  endTime;
    }

    public void setUserStart(String startTimeIn) {
        userStart = startTimeIn;
    }

    public String getUserStart() {
        return  userStart;
    }

    public void setUserEnd(String endTimeIn) {
        userEnd = endTimeIn;
    }

    public String getUserEnd() {
        return  userEnd;
    }
}
