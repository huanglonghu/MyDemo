package com.xx.yuefang.bean;

public class Parter {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"DeveloperId":0,"CompanyName":null,"PhoneNumber":"13250554787","Sex":false,"Telephone":null,"Avatar":"images/face.png","Score":5,"GradeType":"无牌","Profile":null,"UserName":"tom","BusinessCardName":"tom","Email":null,"Address":null,"IsbindQQ":false,"IsbindWeChat":false,"IsbindFacebook":false,"Id":4}
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
         * DeveloperId : 0
         * CompanyName : null
         * PhoneNumber : 13250554787
         * Sex : false
         * Telephone : null
         * Avatar : images/face.png
         * Score : 5.0
         * GradeType : 无牌
         * Profile : null
         * UserName : tom
         * BusinessCardName : tom
         * Email : null
         * Address : null
         * IsbindQQ : false
         * IsbindWeChat : false
         * IsbindFacebook : false
         * Id : 4
         */

        private int DeveloperId;
        private String CompanyName;
        private String PhoneNumber;
        private boolean Sex;
        private String Telephone;
        private String Avatar;
        private double Score;
        private String GradeType;
        private String Profile;
        private String UserName;
        private String BusinessCardName;
        private String Email;
        private String Address;
        private boolean IsbindQQ;
        private boolean IsbindWeChat;
        private boolean IsbindFacebook;
        private int Id;

        public int getDeveloperId() {
            return DeveloperId;
        }

        public void setDeveloperId(int DeveloperId) {
            this.DeveloperId = DeveloperId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public boolean isSex() {
            return Sex;
        }

        public void setSex(boolean Sex) {
            this.Sex = Sex;
        }

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String Telephone) {
            this.Telephone = Telephone;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public double getScore() {
            return Score;
        }

        public void setScore(double Score) {
            this.Score = Score;
        }

        public String getGradeType() {
            return GradeType;
        }

        public void setGradeType(String GradeType) {
            this.GradeType = GradeType;
        }

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String Profile) {
            this.Profile = Profile;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getBusinessCardName() {
            return BusinessCardName;
        }

        public void setBusinessCardName(String BusinessCardName) {
            this.BusinessCardName = BusinessCardName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
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
