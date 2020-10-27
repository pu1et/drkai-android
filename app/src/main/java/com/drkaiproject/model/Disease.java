package com.drkaiproject.model;

import java.io.Serializable;

public class Disease implements Serializable {
    private String name; //질병 이름
    private double percent; //확률(%)
    private int value; //등급
    private String value_txt; //등급 문자열


    public Disease(String name, String value_txt) { //우울증
        this.name = name;
        this.value_txt = value_txt;
    }

    public Disease(String name, double percent, int value) {
        this.name = name;
        this.percent = percent;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValue_txt() {
        return value_txt;
    }

    public void setValue_txt(String value_txt) {
        this.value_txt = value_txt;
    }

    public String toString(){
        return name+" "+percent+" "+value;
    }
}
