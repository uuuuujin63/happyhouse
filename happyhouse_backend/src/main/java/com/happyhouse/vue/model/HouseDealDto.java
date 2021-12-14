package com.happyhouse.vue.model;

public class HouseDealDto {

    private String dealAmount;
    private int builtYear;
    private String dongName;
    private String aptName;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private double area;
    private int floor;

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public int getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(int builtYear) {
        this.builtYear = builtYear;
    }

    public String getDongName() {
        return dongName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public int getDealYear() {
        return dealYear;
    }

    public void setDealYear(int dealYear) {
        this.dealYear = dealYear;
    }

    public int getDealMonth() {
        return dealMonth;
    }

    public void setDealMonth(int dealMonth) {
        this.dealMonth = dealMonth;
    }

    public int getDealDay() {
        return dealDay;
    }

    public void setDealDay(int dealDay) {
        this.dealDay = dealDay;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "HouseDealDto [dealAmount=" + dealAmount + ", builtYear=" + builtYear + ", dongName=" + dongName
                + ", aptName=" + aptName + ", dealYear=" + dealYear + ", dealMonth=" + dealMonth + ", dealDay="
                + dealDay + ", area=" + area + ", floor=" + floor + "]";
    }

}
