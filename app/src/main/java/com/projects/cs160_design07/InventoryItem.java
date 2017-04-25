package com.projects.cs160_design07;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class InventoryItem extends AppCompatActivity {

    private String name, unit;
    private Integer numLeft;
    private Date lastUpdateTime;

    public InventoryItem() {
        this.name = "";
        this.numLeft = 0;
        this.unit = "";
        this.lastUpdateTime = Calendar.getInstance().getTime();
    }

    public InventoryItem(String name, Integer numLeft, String unit) {
        this.name = name;
        this.numLeft = numLeft;
        this.unit = unit;
        this.lastUpdateTime = Calendar.getInstance().getTime();
    }

    public void setNum(Integer newNum) {
        numLeft = newNum;
        lastUpdateTime = Calendar.getInstance().getTime();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumUnits() {
        return numLeft.toString() + " " + unit;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

}
