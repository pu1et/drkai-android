package com.drkaiproject;

import java.time.LocalDate;

public class Notice {

    private String title;
    private String content;
    private LocalDate date;

    public Notice(String title, LocalDate date){
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return this.date.toString();
    }

    public String getTitle() {
        return this.title;
    }
}
