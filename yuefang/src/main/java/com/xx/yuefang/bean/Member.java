package com.xx.yuefang.bean;

public class Member {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PhoneNumber":"13076737863","NickName":"哦了安分","Avatar":"User/2\\21a86987af0f42a8ad46a8f6224db300.jpg","Sex":false,"Credit":91,"Score":1.75,"CreationTime":"2019-03-14T11:35:10.857","LastLoginTime":"2019-06-13T15:29:24.857","IsbindQQ":false,"IsbindWeChat":false,"IsbindFacebook":false,"Id":2}
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
         * PhoneNumber : 13076737863
         * NickName : 哦了安分
         * Avatar : User/2\21a86987af0f42a8ad46a8f6224db300.jpg
         * Sex : false
         * Credit : 91
         * Score : 1.75
         * CreationTime : 2019-03-14T11:35:10.857
         * LastLoginTime : 2019-06-13T15:29:24.857
         * IsbindQQ : false
         * IsbindWeChat : false
         * IsbindFacebook : false
         * Id : 2
         */

        private String PhoneNumber;
        private String NickName;
        private String Avatar;
        private boolean Sex;
        private int Credit;
        private double Score;
        private String CreationTime;
        private String LastLoginTime;
        private boolean IsbindQQ;
        private boolean IsbindWeChat;
        private boolean IsbindFacebook;
        private int Id;

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public boolean isSex() {
            return Sex;
        }

        public void setSex(boolean Sex) {
            this.Sex = Sex;
        }

        public int getCredit() {
            return Credit;
        }

        public void setCredit(int Credit) {
            this.Credit = Credit;
        }

        public double getScore() {
            return Score;
        }

        public void setScore(double Score) {
            this.Score = Score;
        }

        public String getCreationTime() {
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }

        public String getLastLoginTime() {
            return LastLoginTime;
        }

        public void setLastLoginTime(String LastLoginTime) {
            this.LastLoginTime = LastLoginTime;
        }

        public boolean isIsbindQQ() {
            return IsbindQQ;
        }

        public void setIsbindQQ(boolean IsbindQQ) {
            this.IsbindQQ = IsbindQQ;
        }

        public boolean isIsbindWeChat() {
            return IsbindWeChat;
        }

        public void setIsbindWeChat(boolean IsbindWeChat) {
            this.IsbindWeChat = IsbindWeChat;
        }

        public boolean isIsbindFacebook() {
            return IsbindFacebook;
        }

        public void setIsbindFacebook(boolean IsbindFacebook) {
            this.IsbindFacebook = IsbindFacebook;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
