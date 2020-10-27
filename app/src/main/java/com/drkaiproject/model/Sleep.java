package com.drkaiproject.model;

import java.io.Serializable;

public class Sleep implements Serializable {
    private int sleep_hour;
    private int sleep_minute;
    private int sleep_sum;
    private String date;

    public int getSleep_hour() {
        return sleep_hour;
    }

    public void setSleep_hour(int sleep_hour) {
        this.sleep_hour = sleep_hour;
    }

    public int getSleep_minute() {
        return sleep_minute;
    }

    public void setSleep_minute(int sleep_minute) {
        this.sleep_minute = sleep_minute;
    }

    public int getSleep_sum() {
        return sleep_sum;
    }

    public void setSleep_sum(int sleep_sum) {
        this.sleep_sum = sleep_sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
