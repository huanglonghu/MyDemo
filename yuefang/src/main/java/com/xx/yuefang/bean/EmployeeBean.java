package com.xx.yuefang.bean;

import java.io.Serializable;
import java.util.List;

public class EmployeeBean {

    /**
     * Errcode : Success
     * Message : string
     * Data : {"Count":0,"Data":[{"DeveloperId":0,"CompanyName":"string","PremisesBaseId":0,"PremisesName":"string","PhoneNumber":"string","Sex":true,"Telephone":"string","Avatar":"string","Score":0,"GradeType":"string","Profile":"string","BusinessCardName":"string","Email":"string","Address":"string","CreationTime":"2019-12-30T06:01:24.384Z","LastLoginTime":"2019-12-30T06:01:24.384Z","IsbindQQ":true,"IsbindWeChat":true,"IsbindFacebook":true,"Id":0}]}
     */

    private String Errcode;
    private String Message;
    private DataBeanX Data;

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

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * Count : 0
         * Data : [{"DeveloperId":0,"CompanyName":"string","PremisesBaseId":0,"PremisesName":"string","PhoneNumber":"string","Sex":true,"Telephone":"string","Avatar":"string","Score":0,"GradeType":"string","Profile":"string","BusinessCardName":"string","Email":"string","Address":"string","CreationTime":"2019-12-30T06:01:24.384Z","LastLoginTime":"2019-12-30T06:01:24.384Z","IsbindQQ":true,"IsbindWeChat":true,"IsbindFacebook":true,"Id":0}]
         */

        private int Count;
        private List<DataBean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean implements Serializable {
            /**
             * DeveloperId : 0
             * CompanyName : string
             * PremisesBaseId : 0
             * PremisesName : string
             * PhoneNumber : string
             * Sex : true
             * Telephone : string
             * Avatar : string
             * Score : 0
             * GradeType : string
             * Profile : string
             * BusinessCardName : string
             * Email : string
             * Address : string
             * CreationTime : 2019-12-30T06:01:24.384Z
             * LastLoginTime : 2019-12-30T06:01:24.384Z
             * IsbindQQ : true
             * IsbindWeChat : true
             * IsbindFacebook : true
             * Id : 0
             */

            private int DeveloperId;
            private String CompanyName;
            private int PremisesBaseId;
            private String PremisesName;
            private String PhoneNumber;
            private boolean Sex;
            private String Telephone;
            private String Avatar;
            private double Score;
            private String GradeType;
            private String Profile;
            private String BusinessCardName;
            private String Email;
            private String Address;
            private String CreationTime;
            private String LastLoginTime;
            private boolean IsbindQQ;
            private boolean IsbindWeChat;
            private boolean IsbindFacebook;
            private boolean IsDel;
            private String UserName;
            private int Id;


            public boolean isDel() {
                return IsDel;
            }

            public void setDel(boolean del) {
                IsDel = del;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
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
}
