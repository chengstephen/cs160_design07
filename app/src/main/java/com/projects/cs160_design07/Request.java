package com.projects.cs160_design07;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by cstep on 4/19/2017.
 */

public class Request extends AppCompatActivity {

    private String message;
    private int time;

    public Request(String message, int time) {
        this.message = message;
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public int getTime() {
        return time;
    }

}
