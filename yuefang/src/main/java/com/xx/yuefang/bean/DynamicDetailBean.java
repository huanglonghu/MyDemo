package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

public class DynamicDetailBean {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesBaseId":6,"PremisesName":"万科春风十里","ActivityName":"大湾区未来·地铁18号线通达珠江新城","IsActive":true,"Picture":"PremisesActivity/6/82231135246947e3b1b7ea7708d91b22.jpeg","Number":1,"CreationTime":"0001-01-01T00:00:00","Id":1}
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
         * PremisesName : 万科春风十里
         * ActivityName : 大湾区未来·地铁18号线通达珠江新城
         * IsActive : true
         * Picture : PremisesActivity/6/82231135246947e3b1b7ea7708d91b22.jpeg
         * Number : 1
         * CreationTime : 0001-01-01T00:00:00
         * Id : 1
         */

        private int PremisesBaseId;
        private String PremisesName;
        private String ActivityName;
        private boolean IsActive;
        private String Picture;
        private int Number;
        private String CreationTime;
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

        public String getActivityName() {
            return ActivityName;
        }

        public void setActivityName(String ActivityName) {
            this.ActivityName = ActivityName;
        }

        public boolean isIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public String getCreationTime() {
            if(!TextUtils.isEmpty(CreationTime)){
                CreationTime= TimeUtil.getStringToDate3(CreationTime);
            }
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
