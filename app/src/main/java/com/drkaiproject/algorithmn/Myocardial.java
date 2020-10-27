package com.drkaiproject.algorithmn;

/*
 *심근경색
 * 확률, 3년내나 5년내는 무러선택할지 몰라서 배열형태로 반환
 */


public class Myocardial {

    int age; //나이
    double creatinine; //크레아티닌
    int is_hypertension; //고혈압
    int history_coronaryArtery; //심근경색 가족력 여부
    int is_diabetes; //당뇨병 여부
    int leukocyte; //백혈구
    int smoking; //현재 흡연 여부
    int total_colesterol; //총 콜레스테롤
    int HDL; //HDL-콜레스테롤
    int LDL; //LDL-콜레스테롤
    int HbA; //글리코헤모글로빈
    double SBP;
    double DBP;
    int is_atrialFibrillation; //심방세동

    double percent; //발병 확률

    Myocardial() {
    }

    public Myocardial(int age, double creatinine, int is_hypertension, int history_coronaryArtery, int is_diabetes, int leukocyte, int smoking, int total_colesterol, int HDL, int LDL, int HbA, double SBP, double DBP, int is_atrialFibrillation) {
        this.age = age;
        this.creatinine = creatinine;
        this.is_hypertension = is_hypertension;
        this.history_coronaryArtery = history_coronaryArtery;
        this.is_diabetes = is_diabetes;
        this.leukocyte = leukocyte;
        this.smoking = smoking;
        this.total_colesterol = total_colesterol;
        this.HDL = HDL;
        this.LDL = LDL;
        this.HbA = HbA;
        this.SBP = SBP;
        this.DBP = DBP;
        this.is_atrialFibrillation = is_atrialFibrillation;
    }

    public double score_myocardial() {
        double score = 0;

        if (this.age >= 30) score += 100 / 10.0 * (this.age - 30) / 5;
        score += 26 / 8.0 * (Math.log(this.creatinine) + 1.5) / 0.5;

        if (this.is_hypertension == 1) score += 9;

        if (this.history_coronaryArtery == 1) score += 4;

        if (this.is_diabetes == 1) score += 9;

        score += 43 / 8.0 * (Math.log(this.leukocyte) - 0.5) / 0.5;

        if (this.smoking == 1) score += 17;

        double colesterol_index = 0.19 * (this.LDL - 120) / 30 - 0.25 * (this.HDL - 55) / 14 + 0.16 * (total_colesterol - 190) / 34;
        score += 54 / 12.0 * (colesterol_index + 2) / 0.5;
        score += 35 / 9.0 * (Math.log(this.HbA) - 1) / 0.2;

        double BP_index = 0.5 * (this.SBP - 115) / 15 + 0.5 * (this.DBP - 75) / 100;
        score += 54 / 9.0 * (BP_index + 3) / 1;

        if (this.is_atrialFibrillation == 1) score += 25;

        return score;
    }

    public double cal_myocardial() {
        double in_3 = 0.0, in_5 = 0.0;
        double score = score_myocardial();

        if (score < 100) {
            in_5 += 0.00;
            in_3 += 0.00;
        } else if (score >= 100 && score < 120) {
            in_5 += 0.01;
            in_3 += 0.00;
        } else if (score >= 120 && score < 122) {
            in_5 += 0.02;
            in_3 += 0.00;
        } else if (score >= 122 && score < 130) {
            in_5 += 0.02;
            in_3 += 0.01;
        } else if (score >= 130 && score < 141) {
            in_5 += 0.03;
            in_3 += 0.01;
        } else if (score >= 141 && score < 144) {
            in_5 += 0.03;
            in_3 += 0.02;
        } else if (score >= 144 && score < 152) {
            in_5 += 0.05;
            in_3 += 0.02;
        } else if (score >= 152 && score < 162) {
            in_5 += 0.05;
            in_3 += 0.03;
        } else if (score >= 162 && score < 165) {
            in_5 += 0.10;
            in_3 += 0.03;
        } else if (score >= 165 && score < 182) {
            in_5 += 0.10;
            in_3 += 0.05;
        } else if (score >= 182 && score < 184) {
            in_5 += 0.20;
            in_3 += 0.05;
        } else if (score >= 184 && score < 203) {
            in_5 += 0.20;
            in_3 += 0.10;
        } else if (score >= 203 && score < 204) {
            in_5 += 0.40;
            in_3 += 0.10;
        } else if (score >= 204 && score < 211) {
            in_5 += 0.40;
            in_3 += 0.20;
        } else if (score >= 211) {
            in_5 += 0.50;
            in_3 += 0.20;
        }

        percent = Math.round(in_3 * 100 * 100) / 100.0;
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