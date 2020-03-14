package com.xx.yuefang.bean;

import java.util.List;

public class SalePresonList {


    /**
     * Errcode : Success
     * Message : string
     * Data : [{"SalespersonId":0,"CompanyName":"string","Avatar":"string","Telephone":"string","BusinessCardName":"string","Email":"string","Address":"string","IsThePremises":true,"GradeType":"string","Id":0}]
     */

    private String Errcode;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * SalespersonId : 0
         * CompanyName : string
         * Avatar : string
         * Telephone : string
         * BusinessCardName : string
         * Email : string
         * Address : string
         * IsThePremises : true
         * GradeType : string
         * Id : 0
         */

        private int SalespersonId;
        private String CompanyName;
        private String Avatar;
        private String Telephone;
        private String BusinessCardName;
        private String Email;
        private String Address;
        private boolean IsThePremises;
        private String GradeType;
        private int Id;

        public int getSalespersonId() {
            return SalespersonId;
        }

        public void setSalespersonId(int SalespersonId) {
            this.SalespersonId = SalespersonId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String Telephone) {
            this.Telephone = Telephone;
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

        public boolean isIsThePremises() {
            return IsThePremises;
        }

        public void setIsThePremises(boolean IsThePremises) {
            this.IsThePremises = IsThePremises;
        }

        public String getGradeType() {
            return GradeType;
        }

        public void setGradeType(String GradeType) {
            this.GradeType = GradeType;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
