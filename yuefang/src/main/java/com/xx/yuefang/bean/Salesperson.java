package com.xx.yuefang.bean;

public class Salesperson {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"DeveloperId":1,"CompanyName":"钰海楼盘项目负责人","PremisesBaseId":13,"PremisesName":"佳兆业悦峰","PhoneNumber":"18555555555","Sex":false,"Telephone":"18555555555","Avatar":"Salesperson/1/81feefb876754f4cb69d2bde12d44386.jpg","Score":2,"GradeType":"铜牌","Profile":null,"BusinessCardName":"佳兆业悦峰\u2014张三","Email":"ddd@613.","Address":"快乐旅途了","CreationTime":"2019-08-16T15:48:22.83","LastLoginTime":"2020-01-04T10:51:05.207","IsbindQQ":false,"IsbindWeChat":false,"IsbindFacebook":false,"Id":1}
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
         * DeveloperId : 1
         * CompanyName : 钰海楼盘项目负责人
         * PremisesBaseId : 13
         * PremisesName : 佳兆业悦峰
         * PhoneNumber : 18555555555
         * Sex : false
         * Telephone : 18555555555
         * Avatar : Salesperson/1/81feefb876754f4cb69d2bde12d44386.jpg
         * Score : 2.0
         * GradeType : 铜牌
         * Profile : null
         * BusinessCardName : 佳兆业悦峰—张三
         * Email : ddd@613.
         * Address : 快乐旅途了
         * CreationTime : 2019-08-16T15:48:22.83
         * LastLoginTime : 2020-01-04T10:51:05.207
         * IsbindQQ : false
         * IsbindWeChat : false
         * IsbindFacebook : false
         * Id : 1
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
        private Object Profile;
        private String BusinessCardName;
        private String Email;
        private String Address;
        private String CreationTime;
        private String LastLoginTime;
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

        public Object getProfile() {
            return Profile;
        }

        public void setProfile(Object Profile) {
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
