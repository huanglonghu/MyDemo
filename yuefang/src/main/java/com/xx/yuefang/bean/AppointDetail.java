package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

public class AppointDetail {

    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesBaseId":6,"PremisesName":"再生时代大厦","DeveloperId":5,"CompanyName":"腾讯","SalespersonId":2,"BusinessCardName":"销售经理2","SalespersonScore":5,"SPhoneNumber":"18307200143","UserId":2,"NickName":"哦了安分","UserCredit":0,"UserCall":"哦明","PhoneNumber":"13076737863","LookHouseNumber":3,"IsPickUp":"自驾","ReservationTime":"2019-04-30T00:00:00","State":"已完成","Remarks":null,"Id":29}
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
         * PremisesBaseId : 6
         * PremisesName : 再生时代大厦
         * DeveloperId : 5
         * CompanyName : 腾讯
         * SalespersonId : 2
         * BusinessCardName : 销售经理2
         * SalespersonScore : 5.0
         * SPhoneNumber : 18307200143
         * UserId : 2
         * NickName : 哦了安分
         * UserCredit : 0.0
         * UserCall : 哦明
         * PhoneNumber : 13076737863
         * LookHouseNumber : 3
         * IsPickUp : 自驾
         * ReservationTime : 2019-04-30T00:00:00
         * State : 已完成
         * Remarks : null
         * Id : 29
         */

        private int PremisesBaseId;
        private String PremisesName;
        private int DeveloperId;
        private String CompanyName;
        private int SalespersonId;
        private String BusinessCardName;
        private double SalespersonScore;
        private String SPhoneNumber;
        private int UserId;
        private String NickName;
        private int UserCredit;
        private String UserCall;
        private String PhoneNumber;
        private String LookHouseNumber;
        private String IsPickUp;
        private String ReservationTime;
        private String State;
        private Object Remarks;
        private int Id;

        public int getPremisesBaseId() {
            return PremisesBaseId;
        }

        public void setPremisesBaseId(int PremisesBaseId) {
            this.PremisesBaseId = PremisesBaseId;
        }

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

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

        public int getSalespersonId() {
            return SalespersonId;
        }

        public void setSalespersonId(int SalespersonId) {
            this.SalespersonId = SalespersonId;
        }

        public String getBusinessCardName() {
            return BusinessCardName;
        }

        public void setBusinessCardName(String BusinessCardName) {
            this.BusinessCardName = BusinessCardName;
        }

        public double getSalespersonScore() {
            return SalespersonScore;
        }

        public void setSalespersonScore(double SalespersonScore) {
            this.SalespersonScore = SalespersonScore;
        }

        public String getSPhoneNumber() {
            return SPhoneNumber;
        }

        public void setSPhoneNumber(String SPhoneNumber) {
            this.SPhoneNumber = SPhoneNumber;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public int getUserCredit() {
            return UserCredit;
        }

        public void setUserCredit(int UserCredit) {
            this.UserCredit = UserCredit;
        }

        public String getUserCall() {
            return UserCall;
        }

        public void setUserCall(String UserCall) {
            this.UserCall = UserCall;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public String getLookHouseNumber() {
            return LookHouseNumber;
        }

        public void setLookHouseNumber(String LookHouseNumber) {
            this.LookHouseNumber = LookHouseNumber;
        }

        public String getIsPickUp() {
            return IsPickUp;
        }

        public void setIsPickUp(String IsPickUp) {
            this.IsPickUp = IsPickUp;
        }

        public String getReservationTime() {
            if (!TextUtils.isEmpty(ReservationTime)) {
                ReservationTime = TimeUtil.getStringToDate(ReservationTime);
            }
            return ReservationTime;
        }

        public void setReservationTime(String ReservationTime) {
            this.ReservationTime = ReservationTime;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public Object getRemarks() {
            return Remarks;
        }

        public void setRemarks(Object Remarks) {
            this.Remarks = Remarks;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
