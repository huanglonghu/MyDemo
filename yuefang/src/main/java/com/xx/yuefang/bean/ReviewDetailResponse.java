package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

public class ReviewDetailResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"ApplyType":1,"PremisesName":"钰海佳园","SalespersonName":"钰海佳园-李宁","SalespersonAvatar":"Salesperson/98e2ca08fa2147cd8267ec96ce23a82a.jpg","SPhoneNumber":"18777777777","ApplyReason":"1","ExamineState":1,"RefuseReason":null,"ApplyrType":"用户名","UserName":"天河","Avatar":"User/4/b6b6c273703e4a4788d1acc1be3a3ca4.jpg","PhoneNumber":"13570410444","CreationTime":"2019-12-25T10:59:32.757","ApplyTypeStr":"申请解绑经纪人","Id":4}
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
         * ApplyType : 1
         * PremisesName : 钰海佳园
         * SalespersonName : 钰海佳园-李宁
         * SalespersonAvatar : Salesperson/98e2ca08fa2147cd8267ec96ce23a82a.jpg
         * SPhoneNumber : 18777777777
         * ApplyReason : 1
         * ExamineState : 1
         * RefuseReason : null
         * ApplyrType : 用户名
         * UserName : 天河
         * Avatar : User/4/b6b6c273703e4a4788d1acc1be3a3ca4.jpg
         * PhoneNumber : 13570410444
         * CreationTime : 2019-12-25T10:59:32.757
         * ApplyTypeStr : 申请解绑经纪人
         * Id : 4
         */

        private int ApplyType;
        private String PremisesName;
        private String SalespersonName;
        private String SalespersonAvatar;
        private String SPhoneNumber;
        private String ApplyReason;
        private int ExamineState;
        private Object RefuseReason;
        private String ApplyrType;
        private String UserName;
        private String Avatar;
        private String PhoneNumber;
        private String CreationTime;
        private String ApplyTypeStr;
        private int Id;

        public int getApplyType() {
            return ApplyType;
        }

        public void setApplyType(int ApplyType) {
            this.ApplyType = ApplyType;
        }

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

        public String getSalespersonName() {
            return SalespersonName;
        }

        public void setSalespersonName(String SalespersonName) {
            this.SalespersonName = SalespersonName;
        }

        public String getSalespersonAvatar() {
            return SalespersonAvatar;
        }

        public void setSalespersonAvatar(String SalespersonAvatar) {
            this.SalespersonAvatar = SalespersonAvatar;
        }

        public String getSPhoneNumber() {
            return SPhoneNumber;
        }

        public void setSPhoneNumber(String SPhoneNumber) {
            this.SPhoneNumber = SPhoneNumber;
        }

        public String getApplyReason() {
            return ApplyReason;
        }

        public void setApplyReason(String ApplyReason) {
            this.ApplyReason = ApplyReason;
        }

        public int getExamineState() {
            return ExamineState;
        }

        public void setExamineState(int ExamineState) {
            this.ExamineState = ExamineState;
        }

        public Object getRefuseReason() {
            return RefuseReason;
        }

        public void setRefuseReason(Object RefuseReason) {
            this.RefuseReason = RefuseReason;
        }

        public String getApplyrType() {
            return ApplyrType;
        }

        public void setApplyrType(String ApplyrType) {
            this.ApplyrType = ApplyrType;
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

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public String getCreationTime() {

            if (!TextUtils.isEmpty(CreationTime)) {
                CreationTime = TimeUtil.getStringToDate(CreationTime);
            }
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }

        public String getApplyTypeStr() {
            return ApplyTypeStr;
        }

        public void setApplyTypeStr(String ApplyTypeStr) {
            this.ApplyTypeStr = ApplyTypeStr;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
