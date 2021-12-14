package com.happyhouse.vue.model;

public class HouseInfoDto {

    private int aptCode;
    private String aptName;
    private String dongCode;
    private String dongName;
    private String sidoName;
    private String gugunName;
    private int buildYear;
    private String jibun;
    private String lat;
    private String lng;
    private String img;
    private String recentprice;
    private int visited;

    public String getRecentprice() {
        return recentprice;
    }

    public void setRecentprice(String recentprice) {
        this.recentprice = recentprice;
    }

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

    public int getAptCode() {
        return aptCode;
    }

    public void setAptCode(int aptCode) {
        this.aptCode = aptCode;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getDongCode() {
        return dongCode;
    }

    public void setDongCode(String dongCode) {
        this.dongCode = dongCode;
    }

    public String getDongName() {
        return dongName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public String getJibun() {
        return jibun;
    }

    public void setJibun(String jibun) {
        this.jibun = jibun;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "HouseInfoDto [aptCode=" + aptCode + ", aptName=" + aptName + ", dongCode=" + dongCode + ", dongName="
                + dongName + ", sidoName=" + sidoName + ", gugunName=" + gugunName + ", buildYear=" + buildYear
                + ", jibun=" + jibun + ", lat=" + lat + ", lng=" + lng + ", img=" + img + ", recentprice=" + recentprice
                + ", visited=" + visited + "]";
    }

}
