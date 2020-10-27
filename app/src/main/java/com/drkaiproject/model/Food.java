package com.drkaiproject.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String food_name;//이름
    private int food_count;//개수
    private int kcal;//칼로리
    private int carbo; //탄수화물
    private float protein; //단백질
    private int fat; //지방
    private String sugar; //당
    private String salt; //나트륨
    private String food_time;
    private String date;

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_count() {
        return food_count;
    }

    public void setFood_count(int food_count) {
        this.food_count = food_count;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getCarbo() {
        return carbo;
    }

    public void setCarbo(int carbo) {
        this.carbo = carbo;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFood_time() {
        return food_time;
    }

    public void setFood_time(String food_time) {
        this.food_time = food_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
