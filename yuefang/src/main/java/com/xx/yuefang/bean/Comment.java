package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class Comment {

    /**
     * PremisesName : 万科春风十里
     * UserName : 哦了安分
     * Avatar : User/2\d7f17abe826c41fcbd9a8bd538a3f22a.jpg
     * Rise : 0
     * IsRise : false
     * Fall : 0
     * IsFall : false
     * Score : 4.0
     * Content : 位置好，环境棒
     * CreationTime : 2019-05-07T14:45:28.58
     * Number : 2
     * PremisesCommentDtos : [{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"您的评论很有价值","CreationTime":"2019-05-07T14:47:59.273","Id":13},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"谢谢您的评论","CreationTime":"2019-05-07T14:46:31.447","Id":12}]
     * Id : 11
     */


    /**
     * GradeType :
     * LeadSee : 0
     * Turnover : 0
     */


    private String PremisesName;
    private String UserName;
    private String Avatar;
    private int Rise;
    private boolean IsRise;
    private int Fall;
    private boolean IsFall;
    private double Score;
    private String Content;
    private String CreationTime;
    private int Number;
    private int Id;
    private String GradeType;
    private int LeadSee;
    private int Turnover;
    private boolean IsComment;

    public boolean isComment() {
        return IsComment;
    }

    public void setComment(boolean comment) {
        IsComment = comment;
    }

    public String getGradeType() {
        return GradeType;
    }

    public void setGradeType(String gradeType) {
        GradeType = gradeType;
    }

    public int getLeadSee() {
        return LeadSee;
    }

    public void setLeadSee(int leadSee) {
        LeadSee = leadSee;
    }

    public int getTurnover() {
        return Turnover;
    }

    public void setTurnover(int turnover) {
        Turnover = turnover;
    }

    private List<PremisesCommentDtosBean> PremisesCommentDtos;

    public String getPremisesName() {
        return PremisesName;
    }

    public void setPremisesName(String PremisesName) {
        this.PremisesName = PremisesName;
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

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
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

    public List<PremisesCommentDtosBean> getPremisesCommentDtos() {
        return PremisesCommentDtos;
    }

    public void setPremisesCommentDtos(List<PremisesCommentDtosBean> PremisesCommentDtos) {
        this.PremisesCommentDtos = PremisesCommentDtos;
    }

    public static class PremisesCommentDtosBean {
        /**
         * UserName : 销售经理2
         * Avatar : Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg
         * Rise : 0
         * IsRise : false
         * Fall : 0
         * IsFall : false
         * Content : 您的评论很有价值
         * CreationTime : 2019-05-07T14:47:59.273
         * Id : 13
         */

        private String UserName;
        private String Avatar;
        private int Rise;
        private boolean IsRise;
        private int Fall;
        private boolean IsFall;
        private String Content;
        private String CreationTime;
        private int Id;
        private String ParentName;
        private boolean IsComment;

        public boolean isComment() {
            return IsComment;
        }

        public void setComment(boolean comment) {
            IsComment = comment;
        }

        public String getParentName() {
            return ParentName;
        }

        public void setParentName(String parentName) {
            ParentName = parentName;
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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}