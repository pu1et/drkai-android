package com.drkaiproject.sqliteHelper;

public class Health {
    private int activity;
    private int balance_meal;
    private int overeat_salt;
    private int overeat_sugar;
    private int smoking;
    private int alcohol;
    private int water;
    private int sleep;

    public Health(){

    }

    public Health(int activity, int balance_meal, int overeat_salt, int overeat_sugar, int smoking, int alcohol, int water, int sleep){
        this.activity = activity;
        this.balance_meal = balance_meal;
        this.overeat_salt = overeat_salt;
        this.overeat_sugar = overeat_sugar;
        this.smoking = smoking;
        this.alcohol = alcohol;
        this.water = water;
        this.sleep = sleep;

    }

    //평균 건강 알고리즘(일간)
    public float daily_health(){
        float result_score = 0;

        if(activity>30)
            result_score += 1;
        if(balance_meal==1)
            result_score += 0.34;
        if(overeat_salt<=2000)
            result_score += 0.33;
        if(overeat_sugar<=260) //19~29세 청년 기준으로 측정했음
            result_score += 0.33;
        if(alcohol<=2)
            result_score += 1;
        if(smoking==0)
            result_score += 1;
        if(sleep>=6 && sleep <11)
            result_score += 1;
        if(water>=4)
            result_score += 1;

        result_score = (result_score/6)*100;
        return result_score;

    }

    public float week_health(){
        float result_score = 0;

        if(activity<1)
            result_score += 0;
        else if(activity<=2 && activity>=1)
            result_score += 1;
        else if(activity == 3)
            result_score += 2;
        else if(activity == 4)
            result_score += 3;
        else if(activity  >= 5)
            result_score += 4;

        if(balance_meal<1)
            result_score += 0;
        else if(balance_meal<=2 && balance_meal>=1)
            result_score += 0.34;
        else if(balance_meal == 3)
            result_score += 0.68;
        else if(balance_meal == 4)
            result_score += 1.02;
        else if(balance_meal  >= 5)
            result_score += 1.36;

        if(overeat_salt<1)
            result_score += 0;
        else if(overeat_salt<=2 && overeat_salt>=1)
            result_score += 0.33;
        else if(overeat_salt == 3)
            result_score += 0.33;
        else if(overeat_salt == 4)
            result_score += 0.99;
        else if(overeat_salt  >= 5)
            result_score += 1.32;

        if(overeat_sugar<1)
            result_score += 0;
        else if(overeat_sugar<=2 && overeat_sugar>=1)
            result_score += 0.33;
        else if(overeat_sugar == 3)
            result_score += 0.33;
        else if(overeat_sugar == 4)
            result_score += 0.99;
        else if(overeat_sugar  >= 5)
            result_score += 1.32;

        if(smoking>30)
            result_score += 0;
        else if(smoking<30 && smoking>=21)
            result_score += 1;
        else if(smoking<20 && smoking>=11)
            result_score += 2;
        else if(smoking<10 && smoking>=1)
            result_score += 3;
        else if(smoking<1)
            result_score += 4;

        if(alcohol>20)
            result_score += 0;
        else if(alcohol<=20 && alcohol>=13)
            result_score += 1;
        else if(alcohol<=12 && alcohol>=11)
            result_score += 2;
        else if(alcohol<=10 && alcohol>=8)
            result_score += 3;
        else if(alcohol<8)
            result_score += 4;

        if(water<0)
            result_score += 0;
        else if(water<=2 && water>=1)
            result_score += 1;
        else if(water<=4 && water>=3)
            result_score += 2;
        else if(water<=6 && water>=5)
            result_score += 3;
        else if(water>=7)
            result_score += 4;

        if(sleep<=3 || sleep >=14)
            result_score += 0;
        else if((sleep<6 && sleep>3) || (sleep>=11 && sleep<14))
            result_score += 1;
        else if(sleep==6 || (sleep>=10 && sleep<11))
            result_score += 2;
        else if((sleep<7 && sleep >6) || (sleep>=9 && sleep <10))
            result_score += 3;
        else if(sleep>=7 && sleep <9)
            result_score += 4;

        result_score = (result_score/24)*100;
        return result_score;

    }

    //개선알고리즘(일간)
    public float improve_health(int activity_gravity, int meal_gravity, int salt_gravity, int sugar_gravity, int smoking_gravity, int alcohol_gravity, int water_gravity, int sleep_gravity){
        float result_score = 0;
        int total_gravity = activity_gravity + meal_gravity + smoking_gravity + alcohol_gravity + water_gravity + sleep_gravity;

        if(activity>30)
            result_score += activity_gravity;
        if(balance_meal==1)
            result_score += 0.34*meal_gravity;
        if(overeat_salt<=2000)
            result_score += 0.33*salt_gravity;
        if(overeat_sugar<=260) //19~29세 청년 기준으로 측정했음
            result_score += 0.33*sugar_gravity;
        if(alcohol<=2)
            result_score += alcohol_gravity;
        if(smoking==0)
            result_score += smoking_gravity;
        if(water>=4)
            result_score += water_gravity;
        if(sleep>=6 && sleep <11)
            result_score += sleep_gravity;

        result_score = (result_score/total_gravity)*100;
        return result_score;

    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getBalance_meal() {
        return balance_meal;
    }

    public void setBalance_meal(int balance_meal) {
        this.balance_meal = balance_meal;
    }

    public int getOvereat_salt() {
        return overeat_salt;
    }

    public void setOvereat_salt(int overeat_salt) {
        this.overeat_salt = overeat_salt;
    }

    public int getOvereat_sugar() {
        return overeat_sugar;
    }

    public void setOvereat_sugar(int overeat_sugar) {
        this.overeat_sugar = overeat_sugar;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
}