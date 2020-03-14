package com.xx.yuefang.bean;

import android.text.TextUtils;
import com.xx.yuefang.util.TimeUtil;
import java.util.List;

public class PremisesCommentDetailBean {
    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"佳兆业悦峰","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":4,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"#未实地看过#桂花糕","CreationTime":"2019-08-19T10:25:42.06","Number":17,"GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentDtos":[{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"的","CreationTime":"2019-11-29T18:14:56.313","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"家里冷","CreationTime":"2019-11-30T19:14:34.44","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":272},{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"哈哈","CreationTime":"2019-11-30T18:56:27.607","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":270}],"Id":259},{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"史地政吗","CreationTime":"2019-11-29T18:13:13.56","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[],"Id":258},{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"我刚刚发的在哪里","CreationTime":"2019-11-29T18:13:05.827","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[],"Id":257}],"Id":7}
     */

    private int Errcode;
    private String Message;
    private DataBean Data;

    public int getErrcode() {
        return Errcode;
    }

    public void setErrcode(int Errcode) {
        this.Errcode = Errcode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * PremisesName : 佳兆业悦峰
         * UserName : 丽丽
         * Avatar : User/2/b2588d73570445a09e99292f2a6d78d1.jpg
         * Rise : 4
         * IsRise : false
         * Fall : 0
         * IsFall : false
         * Score : 2.5
         * Content : #未实地看过#桂花糕
         * CreationTime : 2019-08-19T10:25:42.06
         * Number : 17
         * GradeType :
         * LeadSee : 0
         * Turnover : 0
         * IsComment : true
         * PremisesCommentDtos : [{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"的","CreationTime":"2019-11-29T18:14:56.313","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"家里冷","CreationTime":"2019-11-30T19:14:34.44","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":272},{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"哈哈","CreationTime":"2019-11-30T18:56:27.607","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":270}],"Id":259},{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"史地政吗","CreationTime":"2019-11-29T18:13:13.56","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[],"Id":258},{"UserName":"没错，大名鼎鼎的程咬金","Avatar":"User/7/9487850d183b4936a91b222a6d65633e.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"我刚刚发的在哪里","CreationTime":"2019-11-29T18:13:05.827","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"PremisesCommentSonDtos":[],"Id":257}]
         * Id : 7
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
        private String GradeType;
        private int LeadSee;
        private int Turnover;
        private boolean IsComment;
        private int Id;
        private CurrentCommentBean CurrentComment;
        public CurrentCommentBean getCurrentComment() {
            return CurrentComment;
        }

        public void setCurrentComment(CurrentCommentBean CurrentComment) {
            this.CurrentComment = CurrentComment;
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

        public List<PremisesCommentDtosBean> getPremisesCommentDtos() {
            return PremisesCommentDtos;
        }

        public void setPremisesCommentDtos(List<PremisesCommentDtosBean> PremisesCommentDtos) {
            this.PremisesCommentDtos = PremisesCommentDtos;
        }

        public static class PremisesCommentDtosBean {
            /**
             * UserName : 没错，大名鼎鼎的程咬金
             * Avatar : User/7/9487850d183b4936a91b222a6d65633e.jpg
             * Rise : 0
             * IsRise : false
             * Fall : 0
             * IsFall : false
             * Content : 的
             * CreationTime : 2019-11-29T18:14:56.313
             * GradeType :
             * LeadSee : 0
             * Turnover : 0
             * IsComment : true
             * PremisesCommentSonDtos : [{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"家里冷","CreationTime":"2019-11-30T19:14:34.44","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":272},{"ParentName":"","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"哈哈","CreationTime":"2019-11-30T18:56:27.607","GradeType":"","LeadSee":0,"Turnover":0,"IsComment":true,"Id":270}]
             * Id : 259
             */

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
            private List<PremisesCommentSonsBean> PremisesCommentSonDtos;

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

            public List<PremisesCommentSonsBean> getPremisesCommentSonDtos() {
                return PremisesCommentSonDtos;
            }

            public void setPremisesCommentSonDtos(List<PremisesCommentSonsBean> PremisesCommentSonDtos) {
                this.PremisesCommentSonDtos = PremisesCommentSonDtos;
            }

        }
    }
}
