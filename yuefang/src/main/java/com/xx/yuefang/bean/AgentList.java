package com.xx.yuefang.bean;

import java.util.List;

public class AgentList {
    /**
     * Errcode : 0
     * Message : success
     * Data : [{"MyBrokerId":40007,"SalespersonId":10018,"UserType":3,"Avatar":"dd","BusinessCardName":"小明","PhoneNumber":"13122222222","GradeType":"Not","LeadSee":0,"Turnover":0,"CompanyId":1,"CompanyName":"","CompanyAvatar":"","Introduce":"","PremisesName":"斯越云谷","Picture":"PremisesBase/8/96d768027811421db8e2c2f6bd2ac331.jpeg","Region":"香洲区","Address":"环岛东路与长隆大道交汇处","ConstructionArea":"0-120","State":"在售","PropertyType":"住宅","AveragePrice":10000,"TotalPrice":"0-120","Characteristics":["带装修","大阳台","高绿化率"],"Id":8}]
     */

    private int Errcode;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * MyBrokerId : 40007
         * SalespersonId : 10018
         * UserType : 3
         * Avatar : dd
         * BusinessCardName : 小明
         * PhoneNumber : 13122222222
         * GradeType : Not
         * LeadSee : 0
         * Turnover : 0
         * CompanyId : 1
         * CompanyName :
         * CompanyAvatar :
         * Introduce :
         * PremisesName : 斯越云谷
         * Picture : PremisesBase/8/96d768027811421db8e2c2f6bd2ac331.jpeg
         * Region : 香洲区
         * Address : 环岛东路与长隆大道交汇处
         * ConstructionArea : 0-120
         * State : 在售
         * PropertyType : 住宅
         * AveragePrice : 10000.0
         * TotalPrice : 0-120
         * Characteristics : ["带装修","大阳台","高绿化率"]
         * Id : 8
         */

        private int MyBrokerId;
        private int SalespersonId;
        private int UserType;
        private String Avatar;
        private String BusinessCardName;
        private String PhoneNumber;
        private String GradeType;
        private int LeadSee;
        private int Turnover;
        private int CompanyId;
        private String CompanyName;
        private String CompanyAvatar;
        private String Introduce;
        private String PremisesName;
        private String Picture;
        private String Region;
        private String Address;
        private String ConstructionArea;
        private String State;
        private String PropertyType;
        private double AveragePrice;
        private String TotalPrice;
        private int Id;
        private List<String> Characteristics;
        private int ExamineState;
        private String RefuseReason;


        public int getExamineState() {
            return ExamineState;
        }

        public void setExamineState(int examineState) {
            ExamineState = examineState;
        }

        public String getRefuseReason() {
            return RefuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            RefuseReason = refuseReason;
        }

        public int getMyBrokerId() {
            return MyBrokerId;
        }

        public void setMyBrokerId(int MyBrokerId) {
            this.MyBrokerId = MyBrokerId;
        }

        public int getSalespersonId() {
            return SalespersonId;
        }

        public void setSalespersonId(int SalespersonId) {
            this.SalespersonId = SalespersonId;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
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

        public int getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(int CompanyId) {
            this.CompanyId = CompanyId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getCompanyAvatar() {
            return CompanyAvatar;
        }

        public void setCompanyAvatar(String CompanyAvatar) {
            this.CompanyAvatar = CompanyAvatar;
        }

        public String getIntroduce() {
            return Introduce;
        }

        public void setIntroduce(String Introduce) {
            this.Introduce = Introduce;
        }

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getConstructionArea() {
            return ConstructionArea;
        }

        public void setConstructionArea(String ConstructionArea) {
            this.ConstructionArea = ConstructionArea;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getPropertyType() {
            return PropertyType;
        }

        public void setPropertyType(String PropertyType) {
            this.PropertyType = PropertyType;
        }

        public double getAveragePrice() {
            return AveragePrice;
        }

        public void setAveragePrice(double AveragePrice) {
            this.AveragePrice = AveragePrice;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<String> getCharacteristics() {
            return Characteristics;
        }

        public void setCharacteristics(List<String> Characteristics) {
            this.Characteristics = Characteristics;
        }
    }
}
