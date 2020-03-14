package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

public class PremisesCommentSonsBean {
    /**
     * ParentName : 陈小姐
     * UserName : 丽丽
     * Avatar : User/2/b2588d73570445a09e99292f2a6d78d1.jpg
     * Rise : 0
     * IsRise : false
     * Fall : 0
     * IsFall : false
     * Content : 哦哦哦
     * CreationTime : 2019-12-12T09:45:54.1972673+08:00
     * GradeType :
     * LeadSee : 0
     * Turnover : 0
     * IsComment : false
     * Id : 598
     */
    private String ParentName;
    private String UserName;
    private String Avatar;
    private int Rise;
    private boolean IsRise;
    private int Fall;
    private boolean IsFall;
    private String Content;
    private String CreationTime;
    private String GradeType;
    private int LeadSee;
    private int Turnover;
    private boolean IsComment;
    private int Id;

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
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

    public int getFall() {
        return Fall;
    }

    public void setFall(int Fall) {
        this.Fall = Fall;
    }

    public boolean isIsFall() {
        return IsFall;
    }

    public void setIsFall(boolean IsFall) {
        this.IsFall = IsFall;
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

    public String getGradeType() {
        return GradeType;
    }

    public void setGradeType(String GradeType) {
        this.GradeType = GradeType;
    }

    public int getLeadSee() {
        return LeadSee;
    }

    public void setLeadSee(int LeadSee) {
        this.LeadSee = LeadSee;
    }

    public int getTurnover() {
        return Turnover;
    }

    public void setTurnover(int Turnover) {
        this.Turnover = Turnover;
    }

    public boolean isIsComment() {
        return IsComment;
    }

    public void setIsComment(boolean IsComment) {
        this.IsComment = IsComment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
