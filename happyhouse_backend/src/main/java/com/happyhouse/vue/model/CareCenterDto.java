package com.happyhouse.vue.model;

public class CareCenterDto {

    private String sidoName;
    // 시도
    private String gugunName;
    // 구군
    private String centerName;
    // 의료기관명
    private String addr;
    // 주소
    private String hourWeek;
    // 평일 운영시간
    private String hourSat;
    // 토요일 운영시간
    private String hourHol;
    // 일요일 및 공휴일 운영시간
    private String phoneNum;
    // 대표 전화번호

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getGugunName() {
        return gugunName;
    }

    public void setGugunName(String gugunName) {
        this.gugunName = gugunName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getHourWeek() {
        return hourWeek;
    }

    public void setHourWeek(String hourWeek) {
        this.hourWeek = hourWeek;
    }

    public String getHourSat() {
        return hourSat;
    }

    public void setHourSat(String hourSat) {
        this.hourSat = hourSat;
    }

    public String getHourHol() {
        return hourHol;
    }

    public void setHourHol(String hourHol) {
        this.hourHol = hourHol;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "CareCenterDto [sidoName=" + sidoName + ", gugunName=" + gugunName + ", centerName=" + centerName + ", addr=" + addr + ", hourWeek=" + hourWeek + ", hourSat=" + hourSat + ", hourHol="
                + hourHol + ", phoneNum=" + phoneNum + "]";
    }

}
