package com.drkaiproject.model;

import java.io.Serializable;

public class Alcohol implements Serializable {
    public int alcohol_data;
    public String date;

    public int getAlcohol_data() {
        return alcohol_data;
    }

    public void setAlcohol_data(int alcohol_data) {
        this.alcohol_data = alcohol_data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
