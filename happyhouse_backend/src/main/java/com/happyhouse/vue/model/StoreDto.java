package com.happyhouse.vue.model;

public class StoreDto {
    
    private String storeNo;
    // storeNo : 상가 업소 번호
    private String storeName;
    // storeName : 상호명
    private String storeType;
    // storeType : 상권업종대분류명
    private String sidoCode;
    // sidoCode : 시도코드
    private String sidoName;
    // sidoName : 시도명
    private String sigunguCode;
    // sigunguCode : 시군구코드
    private String sigunguName;
    // sigunguName : 시군구명
    private String dongCode;
    // dongCode : 법정동코드
    private String dongName;
    // dongName : 법정동명
    private String addr;
    // addr : 도로명주소
    private double lat;
    // lat : 위도
    private double lon;

    // lon : 경도
    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getSidoCode() {
        return sidoCode;
    }

    public void setSidoCode(String sidoCode) {
        this.sidoCode = sidoCode;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getSigunguCode() {
        return sigunguCode;
    }

    public void setSigunguCode(String sigunguCode) {
        this.sigunguCode = sigunguCode;
    }

    public String getSigunguName() {
        return sigunguName;
    }

    public void setSigunguName(String sigunguName) {
        this.sigunguName = sigunguName;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "StoreDto [storeNo=" + storeNo + ", storeName=" + storeName + ", storeType=" + storeType + ", sidoCode=" + sidoCode + ", sidoName=" + sidoName + ", sigunguCode=" + sigunguCode
                + ", sigunguName=" + sigunguName + ", dongCode=" + dongCode + ", dongName=" + dongName + ", addr=" + addr + ", lat=" + lat + ", lon=" + lon + "]";
    }

}
