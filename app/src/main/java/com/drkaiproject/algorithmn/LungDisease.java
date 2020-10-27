package com.drkaiproject.algorithmn;

/*
 * 폐질환
 * 10년내로 발병할 확률
 */

public class LungDisease {

    private int gender; //성별
    private int age; //나이
    private int smoking; //흡연 경험 여부
    private int carstairs_level; //결핍 지수
    private int prior_asthma; //이전 천식 경험

    private double percent; //발병 확률(%)


    LungDisease() {
    }

    public LungDisease(int gender, int age, int smoking, int carstairs_level, int prior_asthma) {
        this.gender = gender;
        this.age = age;
        this.smoking = smoking;
        this.carstairs_level = carstairs_level;
        this.prior_asthma = prior_asthma;
    }

    public double cal_lungdisease() {
        double score = 0;
        double result = 0;
        if (this.gender == 0) {
            if (this.age >= 40 && this.age <= 44)
                score += 0.7195;
            else if (this.age >= 45 && this.age <= 49)
                score += 1.3113;
            else if (this.age >= 50 && this.age <= 54)
                score += 1.7030;
            else if (this.age >= 55 && this.age <= 59)
                score += 2.0982;
            else if (this.age >= 60 && this.age <= 64)
                score += 2.3529;
            else if (this.age >= 65)
                score += 3.2485;

            if (this.smoking == 1)
                score += 2.2623;

            if (this.carstairs_level == 2)
                score += 0.2233;
            else if (this.carstairs_level == 3)
                score += 0.4989;
            else if (this.carstairs_level == 4)
                score += 0.6666;
            else if (this.carstairs_level == 5)
                score += 0.9485;

            if (this.prior_asthma == 1)
                score += 1.0250;

            if (score >= 0 && score < 0.831)
                result = 0.000;
            else if (score >= 0.834 && score < 1.662)
                result = 0.001;
            else if (score >= 1.662 && score < 2.493)
                result = 0.002;
            else if (score >= 2.493 && score < 3.324)
                result = 0.005;
            else if (score >= 3.324 && score < 4.155)
                result = 0.010;
            else if (score >= 4.155 && score < 4.986)
                result = 0.015;
            else if (score >= 4.986 && score < 5.817)
                result = 0.030;
            else if (score >= 5.817 && score < 6.648)
                result = 0.055;
            else if (score >= 6.648 && score < 7.479)
                result = 0.100;
            else if (score >= 7.479)
                result = 0.159;
        } else {
            if (this.age >= 40 && this.age <= 44)
                score += 0.7226;
            else if (this.age >= 45 && this.age <= 49)
                score += 1.3540;
            else if (this.age >= 50 && this.age <= 54)
                score += 1.7945;
            else if (this.age >= 55 && this.age <= 59)
                score += 2.2681;
            else if (this.age >= 60 && this.age <= 64)
                score += 2.6401;
            else if (this.age >= 65)
                score += 3.4623;

            if (this.smoking == 1)
                score += 1.9057;

            if (this.carstairs_level == 2)
                score += 0.3073;
            else if (this.carstairs_level == 3)
                score += 0.4686;
            else if (this.carstairs_level == 4)
                score += 0.6470;
            else if (this.carstairs_level == 5)
                score += 0.9262;

            if (this.prior_asthma == 1)
                score += 1.2148;

            if (score >= 0 && score < 0.834)
                result = 0.000;
            else if (score >= 0.834 && score < 1.668)
                result = 0.001;
            else if (score >= 1.668 && score < 2.502)
                result = 0.002;
            else if (score >= 2.502 && score < 3.336)
                result = 0.005;
            else if (score >= 3.336 && score < 4.17)
                result = 0.010;
            else if (score >= 4.17 && score < 5.004)
                result = 0.015;
            else if (score >= 5.004 && score < 5.838)
                result = 0.030;
            else if (score >= 5.838 && score < 6.672)
                result = 0.060;
            else if (score >= 6.672 && score < 7.506)
                result = 0.102;
            else if (score >= 7.506)
                result = 0.161;
        }
        percent = Math.round(result*100*100)/100.0;
        return percent;
    }

    public int get_value() {
        if (percent <= 1) {
            return 1; //낮음
        } else if (percent <= 6) {
            return 2; //보통
        } else {
            return 3; //높음
        }
    }
}
