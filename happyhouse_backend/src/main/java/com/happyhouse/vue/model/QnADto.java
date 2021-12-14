package com.happyhouse.vue.model;

import java.util.Date;

public class QnADto {
    
    private String iduser;
    private int q_no;
    private String q_title;
    private String q_content;
    private String answer;
    private Date regtime;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public int getQ_no() {
        return q_no;
    }

    public void setQ_no(int q_no) {
        this.q_no = q_no;
    }

    public String getQ_title() {
        return q_title;
    }

    public void setQ_title(String q_title) {
        this.q_title = q_title;
    }

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    @Override
    public String toString() {
        return "QnADto [iduser=" + iduser + ", q_no=" + q_no + ", q_title=" + q_title + ", q_content=" + q_content + ", answer=" + answer + ", regtime=" + regtime + "]";
    }

}
