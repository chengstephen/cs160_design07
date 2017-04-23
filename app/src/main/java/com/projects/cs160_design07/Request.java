package com.projects.cs160_design07;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

/**
 * Created by cstep on 4/19/2017.
 */

public class Request extends AppCompatActivity {

    private String name, message, time;
    private Date currentTime;

    public Request() {
        this.name = "";
        this.message = "";
        this.time = "";
        this.currentTime = Calendar.getInstance().getTime();
    }

    public Request(String name, String message, String time) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.currentTime = Calendar.getInstance().getTime();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public Date getRequestTime() {
        return currentTime;
    }

}
