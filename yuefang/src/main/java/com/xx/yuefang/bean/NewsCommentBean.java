package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public  class NewsCommentBean {
    /**
     * UserName : 丽丽
     * Avatar : User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg
     * Rise : 0
     * IsRise : false
     * Content : 这是一条好评
     * CreationTime : 2019-10-11T14:33:44.26
     * Number : 1
     * NewsInfoCommentDtos : [{"UserName":"周先生","Avatar":"User/1/10bfa826512e465b83644a7c2eef02ef.jpg","Rise":0,"IsRise":false,"Content":"哈哈","CreationTime":"2019-10-11T14:55:32.263","Id":6}]
     * Id : 5
     */

    private String UserName;
    private String Avatar;
    private int Rise;
    private boolean IsRise;
    private String Content;
    private String CreationTime;
    private int Number;
    private int Id;
    private List<NewsItemBean> NewsInfoCommentDtos;
    private boolean IsComment;

    public boolean isComment() {
        return IsComment;
    }

    public void setComment(boolean comment) {
        IsComment = comment;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public int getRise() {
        return Rise;
    }

    public void setRise(int Rise) {
        this.Rise = Rise;
    }

    public boolean isIsRise() {
        return IsRise;
    }

    public void setIsRise(boolean IsRise) {
        this.IsRise = IsRise;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getCreationTime() {
        if (!TextUtils.isEmpty(CreationTime)) {
            CreationTime = TimeUtil.getStringToDate3(CreationTime);
        }
        return CreationTime;
    }

    public void setCreationTime(String CreationTime) {
        this.CreationTime = CreationTime;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public List<NewsItemBean> getNewsInfoCommentDtos() {
        return NewsInfoCommentDtos;
    }

    public void setNewsInfoCommentDtos(List<NewsItemBean> NewsInfoCommentDtos) {
        this.NewsInfoCommentDtos = NewsInfoCommentDtos;
    }

}
