package com.drkaiproject.algorithmn;

/*
 * 간경변증
 */

public class Cirrhosis {

    private int gender; //성별
    private int age; //나이
    private int AFP;
    private float albumin; //알부민
    private int platelet; //혈소판
    private int DLD_serve;
    private int alcohol; //3년동안 80ml음주여부

    private double percent; //발병 확률(%)


    Cirrhosis() {
    }

    public Cirrhosis(int gender, int age, int AFP, float albumin, int platelet, int DLD_serve, int alcohol) {
        this.gender = gender;
        this.age = age;
        this.AFP = AFP;
        this.albumin = albumin;
        this.platelet = platelet;
        this.DLD_serve = DLD_serve;
        this.alcohol = alcohol;
    }

    public double cal_cirrhosis() {
        double score = -6.0993;

        if (this.gender == 1) score += 1.6230;

        if (this.age >= 40) score += 1.6463;

        if (this.platelet < 150000) score += 0.1513;

        if (this.albumin < 3.5) score += 0.1714;

        if (this.AFP >= 20) score += 1.0940;

        if (this.alcohol == 1) score += 0.8912;

        if (this.DLD_serve == 1) score += 1.8374;

        score = Math.exp(score);
        percent = Math.round((score / (1 + score) * 100)*100)/100.0;
        return percent;
    }

    public int get_value() {
        if (percent <= 10) {
            return 1; //낮음
        } else if (percent <= 40) {
            return 2; //보통
        } else {
            return 3; //높음
        }
    }
}
