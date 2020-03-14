package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class QABean {
    /**
     * PremisesName : 万科春风十里
     * UserName : 哦了安分
     * Avatar : User/2\d7f17abe826c41fcbd9a8bd538a3f22a.jpg
     * Content : 租房孩子有学位吗？
     * CreationTime : 2019-04-09T11:28:45.993
     * Number : 1
     * QuestionsAnswersDtos : [{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Content":"您好，租房没有学位的奥，业主才有学位的","CreationTime":"2019-04-09T11:30:26.947","Id":3}]
     * Id : 2
     */


    private String PremisesName;
    private String UserName;
    private String Avatar;
    private String Content;
    private String CreationTime;
    private int Number;
    private int Id;
    private String GradeType;
    private int LeadSee;
    private int Turnover;

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

    private List<QuestionsAnswersDtosBean> QuestionsAnswersDtos;

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

    public List<QuestionsAnswersDtosBean> getQuestionsAnswersDtos() {
        return QuestionsAnswersDtos;
    }

    public void setQuestionsAnswersDtos(List<QuestionsAnswersDtosBean> QuestionsAnswersDtos) {
        this.QuestionsAnswersDtos = QuestionsAnswersDtos;
    }

    public static class QuestionsAnswersDtosBean {
        /**
         * UserName : admin
         * Avatar : Operator\admin\65b0f9f33b884ed59e51aa9939b22fbc.jpeg
         * Content : 您好，租房没有学位的奥，业主才有学位的
         * CreationTime : 2019-04-09T11:30:26.947
         * Id : 3
         */

        private String UserName;
        private String Avatar;
        private String Content;
        private String CreationTime;
        private int Id;

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
