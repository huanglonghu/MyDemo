package com.xx.yuefang.bean;

import java.io.Serializable;

public class AgentBean {

    /**
     * Errcode : Success
     * Message : string
     * Data : {"UserType":"Unknown","Avatar":"string","CompanyName":"string","BusinessCardName":"string","PhoneNumber":"string","Email":"string","Address":"string","GradeType":"string","LeadSee":0,"Turnover":0,"Profile":"string","Id":0}
     */

    private String Errcode;
    private String Message;
    private DataBean Data;

    public String getErrcode() {
        return Errcode;
    }

    public void setErrcode(String Errcode) {
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

    public static class DataBean implements Serializable {
        /**
         * UserType : Unknown
         * Avatar : string
         * CompanyName : string
         * BusinessCardName : string
         * PhoneNumber : string
         * Email : string
         * Address : string
         * GradeType : string
         * LeadSee : 0
         * Turnover : 0
         * Profile : string
         * Id : 0
         */

        private String UserType;
        private String Avatar;
        private String CompanyName;
        private String BusinessCardName;
        private String PhoneNumber;
        private String Email;
        private String Address;
        private String GradeType;
        private int LeadSee;
        private int Turnover;
        private String Profile;
        private int Id;

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String UserType) {
            this.UserType = UserType;
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

        public String getBusinessCardName() {
            return BusinessCardName;
        }

        public void setBusinessCardName(String BusinessCardName) {
            this.BusinessCardName = BusinessCardName;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
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

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String Profile) {
            this.Profile = Profile;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
