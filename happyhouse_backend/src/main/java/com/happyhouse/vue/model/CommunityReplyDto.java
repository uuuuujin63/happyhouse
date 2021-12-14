package com.happyhouse.vue.model;

public class CommunityReplyDto {
	private int no;
	private String idx;
	private String iduser;
	private String content;
	private String regitime;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getIduser() {
		return iduser;
	}
	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegitime() {
		return regitime;
	}
	public void setRegitime(String regitime) {
		this.regitime = regitime;
	}
	@Override
	public String toString() {
		return "CommunityReplyDto [idx=" + idx + ", iduser=" + iduser + ", content=" + content + ", regitime="
				+ regitime + "]";
	}
	
	
}
