package com.xx.yuefang.bean;

public class Company {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PhoneNumber":"15918569001","Avatar":"images/face.png","CompanyName":"陈奕迅公司","UserName":"陈医生","Introduce":null,"IsbindQQ":false,"IsbindWeChat":false,"IsbindFacebook":false,"Id":5}
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
         * PhoneNumber : 15918569001
         * Avatar : images/face.png
         * CompanyName : 陈奕迅公司
         * UserName : 陈医生
         * Introduce : null
         * IsbindQQ : false
         * IsbindWeChat : false
         * IsbindFacebook : false
         * Id : 5
         */

        private String PhoneNumber;
        private String Avatar;
        private String CompanyName;
        private String UserName;
        private String Introduce;
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

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getIntroduce() {
            return Introduce;
        }

        public void setIntroduce(String Introduce) {
            this.Introduce = Introduce;
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
