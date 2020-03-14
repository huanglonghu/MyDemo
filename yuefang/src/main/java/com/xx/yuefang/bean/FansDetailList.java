package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class FansDetailList {


    /**
     * Errcode : 0
     * Message : success
     * Data : [{"ReservationTime":"2019-05-31T00:00:00","ReservationState":"已接单","ReservationType":3,"PremisesName":"再生时代大厦","Picture":"PremisesBase/6/d5a360572bca4a1c9e0b8de0a4ed0a28.jpeg","Region":"香洲区","Address":"屏北二路55号  ","ConstructionArea":"6.04万","State":"在售","PropertyType":"写字楼","AveragePrice":0,"TotalPrice":"待定","Characteristics":["多轨交","大型社区","大平层","品牌开发商"],"Id":10045}]
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
         * ReservationTime : 2019-05-31T00:00:00
         * ReservationState : 已接单
         * ReservationType : 3
         * PremisesName : 再生时代大厦
         * Picture : PremisesBase/6/d5a360572bca4a1c9e0b8de0a4ed0a28.jpeg
         * Region : 香洲区
         * Address : 屏北二路55号
         * ConstructionArea : 6.04万
         * State : 在售
         * PropertyType : 写字楼
         * AveragePrice : 0.0
         * TotalPrice : 待定
         * Characteristics : ["多轨交","大型社区","大平层","品牌开发商"]
         * Id : 10045
         */

        private String ReservationTime;
        private String ReservationState;
        private int ReservationType;
        private String PremisesName;
        private int PremisesId;
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

        public int getPremisesId() {
            return PremisesId;
        }

        public void setPremisesId(int premisesId) {
            PremisesId = premisesId;
        }

        public String getReservationTime() {
            if (!TextUtils.isEmpty(ReservationTime)) {
                ReservationTime = TimeUtil.getStringToDate2(ReservationTime);
            }
            return ReservationTime;
        }

        public void setReservationTime(String ReservationTime) {
            this.ReservationTime = ReservationTime;
        }

        public String getReservationState() {
            return ReservationState;
        }

        public void setReservationState(String ReservationState) {
            this.ReservationState = ReservationState;
        }

        public int getReservationType() {
            return ReservationType;
        }

        public void setReservationType(int ReservationType) {
            this.ReservationType = ReservationType;
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
