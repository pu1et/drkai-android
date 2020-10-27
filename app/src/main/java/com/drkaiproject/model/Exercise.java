package com.drkaiproject.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    public int exer_data;
    public String date;

    public int getExer_data() {
        return exer_data;
    }

    public void setExer_data(int exer_data) {
        this.exer_data = exer_data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
