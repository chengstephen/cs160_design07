package com.projects.cs160_design07;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

/**
 * Created by cstep on 4/19/2017.
 */

public class Request extends AppCompatActivity {

    private String name, message;
    private Date currentTime;

    public Request() {
        this.name = "";
        this.message = "";
        this.currentTime = Calendar.getInstance().getTime();
    }

    public Request(String name, String message) {
        this.name = name;
        this.message = message;
        this.currentTime = Calendar.getInstance().getTime();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Date getRequestTime() {
        return currentTime;
    }

}
