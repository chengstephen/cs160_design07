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

    public void addNum() {
        numLeft++;
    }

    public void subtractNum() {
        numLeft--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getNumUnits() {
        String ending;
        if (numLeft == 1) {
            ending = unit;
        } else {
            ending = unit + "s";
        }
        return numLeft.toString() + " " + ending;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

}
