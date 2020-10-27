package com.drkaiproject.algorithmn;

/*
 * 천식
 * 우울증, 감기, 천식 문자열로 반환되는데 문자열로 양호, 중간, 심각, 위험 4가지 단계/ 우울증은 5가지
 */

public class Asthma {

    int area_num; //사는 지역
    double MT; //최저온도
    double DTR; //일교차
    double SP; //일평균습도
    double H; //일평균기압

    Asthma() {
    }

    public Asthma(int area_num, double MT, double DTR, double SP, double H) {
        this.area_num = area_num;
        this.MT = MT;
        this.DTR = DTR;
        this.SP = SP;
        this.H = H;
    }

    public String cal_asthma() {
        String result = "";
        double[][] weight = {{48.4, 24.3, 18.8, 8.5}, {20.6, 11.7, 46.4, 21.3}, {44.3, 20.2, 31.5, 4}, {34.9, 17.1, 43.3, 4.7},
                {43, 25.6, 31, 0.4}, {38.3, 26.3, 33, 2.4}, {40.4, 24.9, 30.3, 4.4}, {54.3, 17.7, 23.9, 4.1},
                {47.2, 23.1, 26.5, 3.2}, {61.8, 13.4, 21.6, 3.2}, {46.5, 21.3, 25.2, 7}, {39.1, 18.9, 34.1, 7.9},
                {46, 12, 31.4, 10.6}, {56.4, 4.4, 23.4, 15.8}, {44.8, 5.4, 44.1, 5.7}, {44.8, 5.4, 44.1, 5.7}};
        double[][] min_temp = {{10.7, 10.7, -2.8, -12.4}, {13.3, 13.3, 1.8, -5.2}, {13.5, 13.5, 0.6, -8.1}, {13.5, 13.5, 1, -7.1},
                {12.2, 12.2, -1.1, -9}, {12.6, 12.6, -0.6, -8.2}, {12.6, 12.6, -0.5, -7.7}, {13.8, 13.8, 1.6, -4.8},
                {12.8, 12.8, 0.1, -6.6}, {13.8, 13.8, 2.5, -3.9}, {14.9, 14.9, 3.5, -3.1}, {13.7, 13.7, 1.4, -4.8}
                , {15.1, 15.1, 4.7, -2.9}, {14, 14, 2.3, -3.3}, {16, 16, 6.5, 1.7}, {17, 17, 7.8, 1.9}};
        double[][] day_diff = {{10.1, 10.1, 14, 17.8}, {7.2, 7.2, 9.8, 12.8}, {7.3, 7.3, 9.9, 12.5}, {6.8, 6.8, 9.2, 11.7},
                {8.4, 8.4, 11.7, 15.1}, {8.7, 8.7, 12.4, 16}, {8.6, 8.6, 12.3, 15.8}, {8.7, 8.7, 12, 15.5},
                {9.1, 9.1, 12.9, 16.4}, {8.4, 8.4, 11.5, 15.1}, {7.5, 7.5, 10, 12.5}, {8.2, 8.2, 11.9, 15.6},
                {6.8, 6.8, 9, 11.2}, {6.7, 6.7, 9.4, 12.1}, {5, 5, 7.4, 10.4}, {5.9, 5.9, 8, 9.9}};
        double[][] humidity = {{74, 74, 59.3, 43.9}, {67.5, 67.5, 41.8, 26.1}, {65.9, 65.9, 50, 37.4}, {71.9, 71.9, 55.6, 42.3},
                {72, 72, 56.9, 42.1}, {67.6, 67.6, 53.4, 39.6}, {69.9, 69.9, 55.9, 40.9}, {62.5, 62.5, 44.5, 30.3},
                {69.9, 69.9, 58.1, 44.8}, {70, 70, 49.1, 31.3}, {68.4, 68.4, 47.3, 31.6}, {71.3, 71.3, 58.1, 44.6},
                {69.3, 69.3, 46.9, 30.1}, {79.4, 79.4, 67.1, 55.9}, {69, 69, 56.5, 45.6}, {71.9, 71.9, 56.5, 44.4}};
        double[][] pres = {{1004.8, 1004.8, 1012.9, 1019.2}, {1010.1, 1010.1, 1017.6, 1023.7}, {1003.5, 1003.5, 1011.9, 1017.9},
                {1005.7, 1005.7, 1014.1, 1020.2}, {1009.9, 1009.9, 1018.5, 1024.7}, {1007.2, 1007.2, 1015.6, 1021.7},
                {1005.8, 1005.8, 1014.1, 1020.2}, {1007, 1007, 1014.7, 1020.7}, {1007.6, 1007.6, 1016.2, 1022.1},
                {1009.6, 1009.6, 1016.8, 1022.7}, {1010.4, 1010.4, 1018, 1024.5}, {1005.5, 1005.5, 1013.8, 1019.6},
                {1005.4, 1005.4, 1012.5, 1018.1}, {1009.4, 1009.4, 1017.9, 1023.7}, {1011.2, 1011.2, 1019.8, 1025.6},
                {1007.5, 1007.5, 1015, 1020.5}};

        int FDX_MT = 0, FDX_DTR = 0, FDX_SP = 0, FDX_H = 0;
        double MT_W = weight[this.area_num][0] / 100.0;
        double DTR_W = weight[this.area_num][1] / 100.0;
        double SP_W = weight[this.area_num][2] / 100.0;
        double H_W = weight[this.area_num][3] / 100.0;

        if (this.MT < min_temp[this.area_num][3])
            FDX_MT = 4;
        else if (this.MT < min_temp[this.area_num][2])
            FDX_MT = 3;
        else if (this.MT < min_temp[this.area_num][1])
            FDX_MT = 2;
        else if (min_temp[this.area_num][0] <= this.MT)
            FDX_MT = 1;

        if (day_diff[this.area_num][3] <= this.DTR)
            FDX_DTR = 4;
        else if (day_diff[this.area_num][2] <= this.DTR)
            FDX_DTR = 3;
        else if (day_diff[this.area_num][1] <= this.DTR)
            FDX_DTR = 2;
        else if (this.DTR < day_diff[this.area_num][0])
            FDX_DTR = 1;

        if (pres[this.area_num][3] <= this.SP)
            FDX_SP = 4;
        else if (pres[this.area_num][2] <= this.SP)
            FDX_SP = 3;
        else if (pres[this.area_num][1] <= this.SP)
            FDX_SP = 2;
        else if (this.SP < pres[this.area_num][0])
            FDX_SP = 1;

        if (humidity[this.area_num][3] <= this.H)
            FDX_H = 4;
        else if (humidity[this.area_num][2] <= this.H)
            FDX_H = 3;
        else if (humidity[this.area_num][1] <= this.H)
            FDX_H = 2;
        else if (this.H < humidity[this.area_num][0])
            FDX_H = 1;

        double FV = (MT_W * FDX_MT) + (DTR_W * FDX_DTR) * +(SP_W * FDX_SP) + (H_W * FDX_H);

        if (FV >= 3.0525)
            result = "매우 위험";
        else if (FV >= 2.6452 && FV < 3.0525)
            result = "위험";
        else if (FV >= 1.5354 && FV < 2.6452)
            result = "보통";
        else if (FV >= 1 && FV < 1.5354)
            result = "양호";

        return result;
    }
}
