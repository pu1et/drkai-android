package com.drkaiproject.algorithmn;

/*
 * 뇌졸중
 */

public class Stroke {

    int gender;//성별
    int age;//나이
    int is_hypertension;//고혈압 여부
    int is_diabetes;//당뇨병 여부
    int is_hypercholesterolemia;//고지혈증 여부
    int is_atrialFibrillation;//심방세동 여부
    int is_chemicHeartDisease; //관상 동맥 질환 여부
    int is_previousStroke; //과거 뇌졸중 여부
    int is_obesity;//비만
    int smoking; //현재 흡연 여부

    double percent; //발병 확률

    Stroke() {
    }

    public Stroke(int gender, int age, int is_hypertension, int is_diabetes, int is_hypercholesterolemia, int is_atrialFibrillation, int is_chemicHeartDisease, int is_previousStroke, int is_obesity, int smoking) {
        this.gender = gender;
        this.age = age;
        this.is_hypertension = is_hypertension;
        this.is_diabetes = is_diabetes;
        this.is_hypercholesterolemia = is_hypercholesterolemia;
        this.is_atrialFibrillation = is_atrialFibrillation;
        this.is_chemicHeartDisease = is_chemicHeartDisease;
        this.is_previousStroke = is_previousStroke;
        this.is_obesity = is_obesity;
        this.smoking = smoking;
    }

    public double score_stroke() {
        double score = 0;

        if (this.is_hypertension == 1) {
            if (this.age < 60)
                score += 4.0;
            else if (this.age >= 60 && this.age < 70)
                score += 3.0;
            else if (this.age >= 70 && this.age < 80)
                score += 2.0;
            else if (this.age >= 80 && this.age < 90)
                score += 1.4;
            else if (this.age >= 90)
                score += 1.0;
        }

        if (this.is_diabetes == 1) {
            if (this.gender == 1)
                score *= 1.4;
            else score *= 1.72;
        }

        if (this.is_hypercholesterolemia == 1)
            score *= 1.67;

        if (this.is_atrialFibrillation == 1) {
            if (this.age < 60)
                score *= 4.0;
            else if (this.age >= 60 && this.age < 70)
                score *= 2.6;
            else if (this.age >= 70 && this.age < 80)
                score *= 3.3;
            else if (this.age >= 80)
                score *= 4.5;
        }

        if (this.is_chemicHeartDisease == 1) {
            if (this.gender == 1)
                score *= 1.73;
            else score *= 1.55;
        }

        if (this.is_previousStroke == 1)
            score *= 3.1;

        if (this.is_obesity == 1)
            score *= 1.2;

        if (this.smoking == 1) {
            if (this.gender == 1)
                score *= 1.58;
            else score *= 1.6;
        }
        return score;
    }

    public double cal_stroke() {
        double in_5 = 0, in_10 = 0;
        double score = score_stroke();

        if (this.gender == 1) {
            if (this.age >= 25 && this.age < 30) {
                if (score < 2) {
                    in_5 = 0.08;
                    in_10 = 0.19;
                } else if (score < 5) {
                    in_5 = 0.17;
                    in_10 = 0.39;
                } else if (score < 10) {
                    in_5 = 0.41;
                    in_10 = 0.96;
                } else if (score < 20) {
                    in_5 = 0.83;
                    in_10 = 1.92;
                } else if (score >= 20) {
                    in_5 = 1.65;
                    in_10 = 3.85;
                }
            } else if (this.age >= 30 && this.age < 35) {
                if (score < 2) {
                    in_5 = 0.13;
                    in_10 = 0.30;
                } else if (score < 5) {
                    in_5 = 0.25;
                    in_10 = 0.60;
                } else if (score < 10) {
                    in_5 = 0.63;
                    in_10 = 1.49;
                } else if (score < 20) {
                    in_5 = 1.26;
                    in_10 = 2.97;
                } else if (score >= 20) {
                    in_5 = 2.53;
                    in_10 = 5.94;
                }
            } else if (this.age >= 35 && this.age < 40) {
                if (score < 2) {
                    in_5 = 0.20;
                    in_10 = 0.45;
                } else if (score < 5) {
                    in_5 = 0.39;
                    in_10 = 0.90;
                } else if (score < 10) {
                    in_5 = 0.97;
                    in_10 = 2.24;
                } else if (score < 20) {
                    in_5 = 1.95;
                    in_10 = 4.48;
                } else if (score >= 20) {
                    in_5 = 3.89;
                    in_10 = 8.94;
                }
            } else if (this.age >= 40 && this.age < 45) {
                if (score < 2) {
                    in_5 = 0.29;
                    in_10 = 0.69;
                } else if (score < 5) {
                    in_5 = 0.59;
                    in_10 = 1.37;
                } else if (score < 10) {
                    in_5 = 1.46;
                    in_10 = 3.43;
                } else if (score < 20) {
                    in_5 = 2.92;
                    in_10 = 6.84;
                } else if (score >= 20) {
                    in_5 = 5.82;
                    in_10 = 13.64;
                }
            } else if (this.age >= 45 && this.age < 50) {
                if (score < 2) {
                    in_5 = 0.45;
                    in_10 = 1.06;
                } else if (score < 5) {
                    in_5 = 0.91;
                    in_10 = 2.11;
                } else if (score < 10) {
                    in_5 = 2.27;
                    in_10 = 5.28;
                } else if (score < 20) {
                    in_5 = 4.52;
                    in_10 = 10.53;
                } else if (score >= 20) {
                    in_5 = 9.01;
                    in_10 = 20.95;
                }
            } else if (this.age >= 50 && this.age < 55) {
                if (score < 2) {
                    in_5 = 0.70;
                    in_10 = 1.60;
                } else if (score < 5) {
                    in_5 = 1.40;
                    in_10 = 3.20;
                } else if (score < 10) {
                    in_5 = 3.50;
                    in_10 = 7.98;
                } else if (score < 20) {
                    in_5 = 6.98;
                    in_10 = 15.90;
                } else if (score >= 20) {
                    in_5 = 13.87;
                    in_10 = 31.55;
                }
            } else if (this.age >= 55 && this.age < 60) {
                if (score < 2) {
                    in_5 = 1.04;
                    in_10 = 2.42;
                } else if (score < 5) {
                    in_5 = 2.09;
                    in_10 = 4.84;
                } else if (score < 10) {
                    in_5 = 5.19;
                    in_10 = 12.06;
                } else if (score < 20) {
                    in_5 = 10.33;
                    in_10 = 23.98;
                } else if (score >= 20) {
                    in_5 = 20.47;
                    in_10 = 47.40;
                }
            } else if (this.age >= 60 && this.age < 65) {
                if (score < 2) {
                    in_5 = 0.59;
                    in_10 = 3.71;
                } else if (score < 5) {
                    in_5 = 3.18;
                    in_10 = 7.41;
                } else if (score < 10) {
                    in_5 = 7.92;
                    in_10 = 18.42;
                } else if (score < 20) {
                    in_5 = 15.74;
                    in_10 = 36.51;
                } else if (score >= 20) {
                    in_5 = 31.06;
                    in_10 = 71.72;
                }
            } else if (this.age >= 65 && this.age < 70) {
                if (score < 2) {
                    in_5 = 2.44;
                    in_10 = 5.51;
                } else if (score < 5) {
                    in_5 = 4.87;
                    in_10 = 11.00;
                } else if (score < 10) {
                    in_5 = 12.10;
                    in_10 = 27.27;
                } else if (score < 20) {
                    in_5 = 23.94;
                    in_10 = 53.80;
                } else if (score >= 20) {
                    in_5 = 46.89;
                    in_10 = 100.00;
                }
            } else if (this.age >= 70 && this.age < 75) {
                if (score < 2) {
                    in_5 = 3.63;
                    in_10 = 8.08;
                } else if (score < 5) {
                    in_5 = 7.24;
                    in_10 = 16.09;
                } else if (score < 10) {
                    in_5 = 17.93;
                    in_10 = 39.74;
                } else if (score < 20) {
                    in_5 = 35.31;
                    in_10 = 77.89;
                } else if (score >= 20) {
                    in_5 = 68.48;
                    in_10 = 100.00;
                }
            } else if (this.age >= 75 && this.age < 80) {
                if (score < 2) {
                    in_5 = 5.33;
                    in_10 = 9.79;
                } else if (score < 5) {
                    in_5 = 10.60;
                    in_10 = 19.49;
                } else if (score < 10) {
                    in_5 = 26.14;
                    in_10 = 47.99;
                } else if (score < 20) {
                    in_5 = 51.08;
                    in_10 = 93.62;
                } else if (score >= 20) {
                    in_5 = 97.60;
                    in_10 = 100.00;
                }
            } else if (this.age >= 80 && this.age < 85) {
                if (score < 2) {
                    in_5 = 5.81;
                    in_10 = 8.88;
                } else if (score < 5) {
                    in_5 = 11.57;
                    in_10 = 17.67;
                } else if (score < 10) {
                    in_5 = 28.45;
                    in_10 = 43.48;
                } else if (score < 20) {
                    in_5 = 55.38;
                    in_10 = 84.67;
                } else if (score >= 20) {
                    in_5 = 100.00;
                    in_10 = 100.00;
                }
            } else if (this.age >= 85) {
                if (score < 2) {
                    in_5 = 5.70;
                    in_10 = 10.45;
                } else if (score < 5) {
                    in_5 = 11.34;
                    in_10 = 20.80;
                } else if (score < 10) {
                    in_5 = 27.92;
                    in_10 = 51.18;
                } else if (score < 20) {
                    in_5 = 54.41;
                    in_10 = 99.76;
                } else if (score >= 20) {
                    in_5 = 100.00;
                    in_10 = 100.00;
                }
            }
        } else {
            if (this.age >= 25 && this.age < 30) {
                if (score < 2) {
                    in_5 = 0.07;
                    in_10 = 0.17;
                } else if (score < 5) {
                    in_5 = 0.15;
                    in_10 = 0.34;
                } else if (score < 10) {
                    in_5 = 0.37;
                    in_10 = 0.85;
                } else if (score < 20) {
                    in_5 = 0.74;
                    in_10 = 1.69;
                } else if (score >= 20) {
                    in_5 = 1.48;
                    in_10 = 3.39;
                }
            } else if (this.age >= 30 && this.age < 35) {
                if (score < 2) {
                    in_5 = 0.11;
                    in_10 = 0.25;
                } else if (score < 5) {
                    in_5 = 0.22;
                    in_10 = 0.50;
                } else if (score < 10) {
                    in_5 = 0.55;
                    in_10 = 1.26;
                } else if (score < 20) {
                    in_5 = 1.11;
                    in_10 = 2.52;
                } else if (score >= 20) {
                    in_5 = 2.21;
                    in_10 = 5.04;
                }
            } else if (this.age >= 35 && this.age < 40) {
                if (score < 2) {
                    in_5 = 0.16;
                    in_10 = 0.38;
                } else if (score < 5) {
                    in_5 = 0.33;
                    in_10 = 0.76;
                } else if (score < 10) {
                    in_5 = 0.82;
                    in_10 = 1.91;
                } else if (score < 20) {
                    in_5 = 1.64;
                    in_10 = 3.81;
                } else if (score >= 20) {
                    in_5 = 3.27;
                    in_10 = 7.60;
                }
            } else if (this.age >= 40 && this.age < 45) {
                if (score < 2) {
                    in_5 = 0.25;
                    in_10 = 0.60;
                } else if (score < 5) {
                    in_5 = 0.50;
                    in_10 = 1.19;
                } else if (score < 10) {
                    in_5 = 1.25;
                    in_10 = 2.97;
                } else if (score < 20) {
                    in_5 = 2.50;
                    in_10 = 5.94;
                } else if (score >= 20) {
                    in_5 = 4.99;
                    in_10 = 11.84;
                }
            } else if (this.age >= 45 && this.age < 50) {
                if (score < 2) {
                    in_5 = 0.40;
                    in_10 = 0.93;
                } else if (score < 5) {
                    in_5 = 0.79;
                    in_10 = 1.85;
                } else if (score < 10) {
                    in_5 = 1.98;
                    in_10 = 4.62;
                } else if (score < 20) {
                    in_5 = 3.96;
                    in_10 = 9.22;
                } else if (score >= 20) {
                    in_5 = 7.88;
                    in_10 = 18.35;
                }
            } else if (this.age >= 50 && this.age < 55) {
                if (score < 2) {
                    in_5 = 0.61;
                    in_10 = 1.43;
                } else if (score < 5) {
                    in_5 = 1.22;
                    in_10 = 2.85;
                } else if (score < 10) {
                    in_5 = 3.03;
                    in_10 = 7.12;
                } else if (score < 20) {
                    in_5 = 6.05;
                    in_10 = 14.18;
                } else if (score >= 20) {
                    in_5 = 12.04;
                    in_10 = 28.17;
                }
            } else if (this.age >= 55 && this.age < 60) {
                if (score < 2) {
                    in_5 = 0.95;
                    in_10 = 2.19;
                } else if (score < 5) {
                    in_5 = 1.90;
                    in_10 = 4.37;
                } else if (score < 10) {
                    in_5 = 4.73;
                    in_10 = 10.89;
                } else if (score < 20) {
                    in_5 = 9.43;
                    in_10 = 21.67;
                } else if (score >= 20) {
                    in_5 = 18.71;
                    in_10 = 42.89;
                }
            } else if (this.age >= 60 && this.age < 65) {
                if (score < 2) {
                    in_5 = 1.43;
                    in_10 = 3.31;
                } else if (score < 5) {
                    in_5 = 2.85;
                    in_10 = 6.60;
                } else if (score < 10) {
                    in_5 = 7.10;
                    in_10 = 16.43;
                } else if (score < 20) {
                    in_5 = 14.11;
                    in_10 = 32.60;
                } else if (score >= 20) {
                    in_5 = 27.89;
                    in_10 = 64.16;
                }
            } else if (this.age >= 65 && this.age < 70) {
                if (score < 2) {
                    in_5 = 2.18;
                    in_10 = 4.90;
                } else if (score < 5) {
                    in_5 = 4.36;
                    in_10 = 9.78;
                } else if (score < 10) {
                    in_5 = 10.83;
                    in_10 = 24.28;
                } else if (score < 20) {
                    in_5 = 21.46;
                    in_10 = 47.99;
                } else if (score >= 20) {
                    in_5 = 42.13;
                    in_10 = 93.76;
                }
            } else if (this.age >= 70 && this.age < 75) {
                if (score < 2) {
                    in_5 = 3.17;
                    in_10 = 7.20;
                } else if (score < 5) {
                    in_5 = 6.33;
                    in_10 = 14.35;
                } else if (score < 10) {
                    in_5 = 15.70;
                    in_10 = 35.50;
                } else if (score < 20) {
                    in_5 = 30.98;
                    in_10 = 69.77;
                } else if (score >= 20) {
                    in_5 = 60.32;
                    in_10 = 100.00;
                }
            } else if (this.age >= 75 && this.age < 80) {
                if (score < 2) {
                    in_5 = 4.69;
                    in_10 = 8.98;
                } else if (score < 5) {
                    in_5 = 9.33;
                    in_10 = 17.88;
                } else if (score < 10) {
                    in_5 = 23.05;
                    in_10 = 44.11;
                } else if (score < 20) {
                    in_5 = 45.19;
                    in_10 = 86.29;
                } else if (score >= 20) {
                    in_5 = 86.86;
                    in_10 = 100.00;
                }
            } else if (this.age >= 80 && this.age < 85) {
                if (score < 2) {
                    in_5 = 5.40;
                    in_10 = 8.54;
                } else if (score < 5) {
                    in_5 = 10.75;
                    in_10 = 17.00;
                } else if (score < 10) {
                    in_5 = 26.48;
                    in_10 = 41.89;
                } else if (score < 20) {
                    in_5 = 51.68;
                    in_10 = 81.80;
                } else if (score >= 20) {
                    in_5 = 98.53;
                    in_10 = 100.00;
                }
            } else if (this.age >= 85) {
                if (score < 2) {
                    in_5 = 5.25;
                    in_10 = 9.63;
                } else if (score < 5) {
                    in_5 = 10.46;
                    in_10 = 19.17;
                } else if (score < 10) {
                    in_5 = 25.77;
                    in_10 = 47.25;
                } else if (score < 20) {
                    in_5 = 50.35;
                    in_10 = 92.31;
                } else if (score >= 20) {
                    in_5 = 96.15;
                    in_10 = 100.00;
                }
            }
        }
        percent = Math.round(in_10*100)/100.0;
        return percent;
    }

    public int get_value() {
        if (percent <= 20) {
            return 1; //낮음
        } else if (percent <= 60) {
            return 2; //보통
        } else {
            return 3; //높음
        }
    }
}
