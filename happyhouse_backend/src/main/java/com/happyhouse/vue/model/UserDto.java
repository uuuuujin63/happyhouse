package com.happyhouse.vue.model;


import java.util.List;

public class UserDto {
    
    private String userid;
    private String password;
    private String name;
    private String addr;
    private String sidoAddr;
    private String gugunAddr;
    private List<UserInterestDto> userInterest;
    private int admin;
    private String findpwdkey;
    private String addrcert;

    
    public String getAddrcert() {
		return addrcert;
	}

	public void setAddrcert(String addrcert) {
		this.addrcert = addrcert;
	}

	public String getFindpwdkey() {
		return findpwdkey;
	}

	public void setFindpwdkey(String findpwdkey) {
		this.findpwdkey = findpwdkey;
	}

	public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<UserInterestDto> getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(List<UserInterestDto> userInterest) {
        this.userInterest = userInterest;
    }

    
	public String getSidoAddr() {
		return sidoAddr;
	}

	public void setSidoAddr(String sidoAddr) {
		this.sidoAddr = sidoAddr;
	}

	public String getGugunAddr() {
		return gugunAddr;
	}

	public void setGugunAddr(String gugunAddr) {
		this.gugunAddr = gugunAddr;
	}

	@Override
	public String toString() {
		return "UserDto [userid=" + userid + ", password=" + password + ", name=" + name + ", addr=" + addr
				+ ", sidoAddr=" + sidoAddr + ", gugunAddr=" + gugunAddr + ", userInterest=" + userInterest + ", admin="
				+ admin + ", findpwdkey=" + findpwdkey + ", addrcert=" + addrcert + "]";
	}

	

	
    

}
