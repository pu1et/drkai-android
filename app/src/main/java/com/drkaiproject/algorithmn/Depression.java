package com.drkaiproject.algorithmn;

/*
 * 우울증
 */

public class Depression{

	private int score;
	Depression(){}
	public Depression(int score) {
		this.score = score;
	}


	public String cal_depression() {
		String result = "";

	    if (score >= 0 && score <= 7)
	        result = "0"; // "매우 양호"
	    else if (score >= 8 && score <= 13)
	        result = "1"; // "양호"
	    else if (score >= 14 && score <= 18)
	        result = "2"; // "보통"
	    else if (score >= 19 && score <= 22)
	        result = "3"; // "주의"
	    else if (score >= 23)
	        result = "4"; // "매우 주의"
	    
	    return result;
	}
}
