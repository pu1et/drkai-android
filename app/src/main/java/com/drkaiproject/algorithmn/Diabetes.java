package com.drkaiproject.algorithmn;

/*
 * 당뇨병
 * 확률 반환
 */

public class Diabetes {

    private int age; //나이
    private int history_diabetes; //당뇨병 가족력 여부
    private int smoking; //현재 흡연 여부
    private double BMI; //체질량지수
    private int is_hypertension; //고혈압
    private int FPG; //공복혈당
    private int HDL; //HDL-콜레스테롤
    private int TG;
    private int HbA;

    private double percent; //발병 확률(%)

    Diabetes() {
    }

    public Diabetes(int age, int history_diabetes, int smoking, double BMI, int is_hypertension, int FPG, int HDL, int TG, int HbA) {
        this.age = age;
        this.history_diabetes = history_diabetes;
        this.smoking = smoking;
        this.BMI = BMI;
        this.is_hypertension = is_hypertension;
        this.FPG = FPG;
        this.HDL = HDL;
        this.TG = TG;
        this.HbA = HbA;
    }

    public double result_diabetes(int score) {

        double result = 0;

        if (score <= 1) result = 0.02;
        else if (score > 1 && score <= 6) result = 0.03;
        else if (score > 6 && score <= 10) result = 0.04;
        else if (score > 10 && score <= 14) result = 0.05;
        else if (score > 14 && score <= 17) result = 0.06;
        else if (score > 17 && score <= 19) result = 0.07;
        else if (score > 19 && score <= 21) result = 0.08;
        else if (score > 21 && score <= 23) result = 0.09;
        else if (score > 23 && score <= 25) result = 0.10;
        else if (score > 25 && score <= 26) result = 0.11;
        else if (score > 26 && score <= 28) result = 0.12;
        else if (score > 28 && score <= 29) result = 0.13;
        else if (score > 29 && score <= 31) result = 0.14;
        else if (score > 31 && score <= 32) result = 0.15;
        else if (score > 32 && score <= 42) result = 0.15 + 0.01 * (score - 31);
        else if (score > 42 && score <= 43) result = 0.27;
        else if (score > 43 && score <= 44) result = 0.28;
        else if (score > 44 && score <= 45) result = 0.29;
        else if (score > 45 && score <= 46) result = 0.31;
        else if (score > 46 && score <= 47) result = 0.32;
        else if (score > 47 && score <= 48) result = 0.33;
        else if (score > 48 && score <= 49) result = 0.35;
        else if (score > 49 && score <= 50) result = 0.36;
        else if (score > 50 && score <= 51) result = 0.38;
        else if (score > 51 && score <= 52) result = 0.39;
        else if (score > 52 && score <= 53) result = 0.41;
        else if (score > 53 && score <= 54) result = 0.42;
        else if (score > 54 && score <= 55) result = 0.44;
        else if (score > 55 && score <= 56) result = 0.46;
        else if (score > 56 && score <= 57) result = 0.47;
        else if (score > 57 && score <= 58) result = 0.49;
        else if (score > 58) result = 0.50;

        return result;
    }

    public double cal_diabetes() {
        int score = 0;

        if (this.age >= 45 && this.age < 50) score += 1;
        else if (this.age >= 50 && this.age < 55) score += 2;
        else if (this.age >= 55 && this.age < 60) score += 3;
        else if (this.age >= 60 && this.age < 65) score += 4;
        else if (this.age >= 65) score += 5;

        if (this.history_diabetes == 1) score += 9;

        if (this.smoking == 1) score += 4;

        if (this.BMI >= 23 && this.BMI < 25) score += 3;
        else if (this.BMI >= 25 && this.BMI < 30) score += 4;
        else if (this.BMI >= 30) score += 9;

        if (this.is_hypertension == 1) score += 6;

        if (this.FPG < 90) score -= 14;
        else if (this.FPG >= 100) score += 18;

        if (this.HDL < 35) score += 6;
        else if (this.HDL >= 50) score -= 3;

        if (this.TG >= 120 && this.TG < 150) score += 5;
        else if (this.TG >= 150) score += 11;

        if (this.HbA >= 5.5 && this.HbA < 6.5) score += 15;

        percent = Math.round(result_diabetes(score)*100*100)/100.0;
        return percent;
    }

    public int get_value() {
        if (percent <= 10) {
            return 1; //낮음
        } else if (percent <= 30) {
            return 2; //보통
        } else {
            return 3; //높음
        }
    }
}
