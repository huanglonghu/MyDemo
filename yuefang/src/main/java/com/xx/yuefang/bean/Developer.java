package com.xx.yuefang.bean;

public class Developer {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PhoneNumber":"18307200141","CompanyName":"腾讯","RoleId":2,"RoleName":"发展商","Avatar":"Developer/4fbbabe672ec4d558ca5e9f474a7c880.jpg","Introduce":"精品中的精品","ReservationDays":10,"CreationTime":"2019-03-06T18:07:22.877","LastLoginTime":"2019-05-20T14:42:34.35","IsbindQQ":false,"IsbindWeChat":false,"IsbindFacebook":false,"Id":5}
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
         * PhoneNumber : 18307200141
         * CompanyName : 腾讯
         * RoleId : 2
         * RoleName : 发展商
         * Avatar : Developer/4fbbabe672ec4d558ca5e9f474a7c880.jpg
         * Introduce : 精品中的精品
         * ReservationDays : 10
         * CreationTime : 2019-03-06T18:07:22.877
         * LastLoginTime : 2019-05-20T14:42:34.35
         * IsbindQQ : false
         * IsbindWeChat : false
         * IsbindFacebook : false
         * Id : 5
         */

        private String PhoneNumber;
        private String CompanyName;
        private int RoleId;
        private String RoleName;
        private String Avatar;
        private String Introduce;
        private int ReservationDays;
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

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int RoleId) {
            this.RoleId = RoleId;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String RoleName) {
            this.RoleName = RoleName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public String getIntroduce() {
            return Introduce;
        }

        public void setIntroduce(String Introduce) {
            this.Introduce = Introduce;
        }

        public int getReservationDays() {
            return ReservationDays;
        }

        public void setReservationDays(int ReservationDays) {
            this.ReservationDays = ReservationDays;
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
