package com.happyhouse.vue.model;

public class NoticeDto {
    
    private String idx;
    private String userid;
    private String title;
    private String content;
    private String regitime;

    public NoticeDto() {
        super();
    }

    public NoticeDto(String idx) {
        super();
        this.idx = idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUserid() {
        return userid;
    }

    public String getRegitime() {
        return regitime;
    }

    public void setRegitime(String regitime) {
        this.regitime = regitime;
    }

    public String getIdx() {
        return idx;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoticeDto [idx=" + idx + ", userid=" + userid + ", title=" + title + ", content=" + content + ", regitime=" + regitime + "]";
    }

}
