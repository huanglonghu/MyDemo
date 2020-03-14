package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

public class NewsItemBean {

    private String UserName;
    private String Avatar;
    private int Rise;
    private boolean IsRise;
    private String Content;
    private String CreationTime;
    private int Id;
    private String ParentName;
    private boolean IsComment;

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

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
        if(!TextUtils.isEmpty(CreationTime)){
            CreationTime= TimeUtil.getStringToDate3(CreationTime);
        }
        return CreationTime;
    }

    public void setCreationTime(String CreationTime) {
        this.CreationTime = CreationTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }





}
