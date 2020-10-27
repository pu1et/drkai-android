package com.drkaiproject.model;


import java.io.Serializable;

public class Smoke implements Serializable {
    private int smoke_data;
    private String date;

    public int getSmoke_data() {
        return smoke_data;
    }

    public void setSmoke_data(int smoke_data) {
        this.smoke_data = smoke_data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
