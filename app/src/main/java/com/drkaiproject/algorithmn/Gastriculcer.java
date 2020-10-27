package com.drkaiproject.algorithmn;

/*
* 의궤양
*/

public class Gastriculcer{

	private int gender; //성별
	private int age; //나이
	private float BMI; //체질량지수
	private int history_cancer; //암가족력 여부
	private int meal_reg; //규칙적인 식사
	private int salt_pref; //짠 음식
	private int alcohol;// 음주
	private int smoking; //흡연
	private int PhA;//운동

	private double percent; //발병 확률(%)

	Gastriculcer(){}
	public Gastriculcer(int gender, int age, float BMI, int history_cancer, int meal_reg, int salt_pref, int alcohol, int smoking, int PhA){
		this.gender = gender;
		this.age = age;
		this.BMI = BMI;
		this.history_cancer = history_cancer;
		this.meal_reg = meal_reg;
		this.salt_pref = salt_pref;
		this.alcohol = alcohol;
		this.smoking = smoking;
		this.PhA = PhA;
	}
	public double cal_gastriculcer() {
	    double score = 0;
	    if (this.gender == 1){ // 남자
	       score = 0.1050 * (this.age - 45.08) - 0.0014 * ((this.age - 45.08) * (this.age - 45.08) - 109.7040);

	        if (this.BMI < 18.5)
	            score += 0.1269 * 0.0244;
	        else if (this.BMI >= 23.0 && this.BMI < 25.0)
	            score -= 0.0847 * 0.2832;
	        else if (this.BMI >= 25.0)
	            score -= 0.1109 * 0.2843;

	        if (this.history_cancer == 1)
	            score += 0.2640 * 0.1431;

	        if (this.meal_reg == 1)
	            score += 0.0301 * 0.3306;
	        else if (this.meal_reg == 2)
	            score += 0.0666 * 0.0857;

	        if (this.salt_pref == 1)
	            score += 0.0255 * 0.6264;
	        else if (this.salt_pref == 2)
	            score += 0.0836 * 0.2125;

	        if (this.alcohol == 1)
	            score += 0.0218 * 0.2858;
	        else if (this.alcohol == 2)
	            score += 0.0778 * 0.1747;
	        else if (this.alcohol == 3)
	            score += 0.1856 * 0.2391;

	        if (this.smoking == 1)
	            score += 0.1414 * 0.1468;
	        else if (this.smoking == 2)
	            score += 0.2132 * 0.0933;
	        else if (this.smoking == 3)
	            score += 0.2997 * 0.3261;
	        else if (this.smoking == 4)
	            score += 0.3587 * 0.1355;

	        if (this.PhA == 1)
	            score += 0.0010 * 0.1592;
	        else if (this.PhA == 2)
	            score -= 0.0513 * 0.3589;
	    }
	    else{
	        score = 0.0692 * (this.age - 48.7) - 0.006 * ((this.age - 48.7) * (this.age - 48.7) - 121.1973);

	        if (this.BMI < 18.5)
	            score += 0.1487 * 0.0393;
	        else if (this.BMI >= 23.0 && this.BMI < 25.0)
	            score -= 0.0071 * 0.2426;
	        else if (this.BMI >= 25.0)
	            score -= 0.0347 * 0.2865;

	        if (this.history_cancer == 1)
	            score += 0.2424 * 0.1531;

	        if (this.salt_pref == 1)
	            score += 0.0052 * 0.6760;
	        else if (this.salt_pref == 2)
	            score += 0.0862 * 0.1643;

	        if (this.alcohol == 1)
	            score -= 0.0286 * 0.1256;
	        else if (this.alcohol == 2)
	            score += 0.1502 * 0.0223;

	        if (this.smoking == 1)
	            score += 0.2291 * 0.0453;

	        score = 1 - Math.pow(0.9961374, Math.exp(score));
	    }

		percent =  Math.round (score*100)/100.0;
		return percent;
	}

	public int get_value() {
		if (percent <= 2) {
			return 1; //낮음
		} else if (percent <= 8) {
			return 2; //보통
		} else {
			return 3; //높음
		}
	}
}
