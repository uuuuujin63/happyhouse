package com.happyhouse.vue.model;

public class UserInterestDto {
    
    private String dongCode;
    private String dongName;

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

    @Override
    public String toString() {
        return "UserInterestDto [dongCode=" + dongCode + ", dongName=" + dongName + "]";
    }

}
