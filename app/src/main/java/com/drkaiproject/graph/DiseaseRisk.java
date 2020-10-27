package com.drkaiproject.graph;

public class DiseaseRisk {

    private String date;
    private int risk;

    public DiseaseRisk(){

    }

    public DiseaseRisk(String date, int risk){
        this.date = date;
        this.risk = risk;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }
}
