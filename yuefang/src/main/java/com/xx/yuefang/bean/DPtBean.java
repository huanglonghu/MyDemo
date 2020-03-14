package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class DPtBean {


    /**
     * Errcode : Success
     * Message : string
     * Data : {"NickName":"string","UAvatar":"string","UScore":0,"UContent":"string","UCreationTime":"2019-05-07T08:38:55.108Z","BusinessCardName":"string","SAvatar":"string","SScore":0,"SContent":"string","SCreationTime":"2019-05-07T08:38:55.108Z","PremisesName":"string","Picture":"string","Province":"string","City":"string","Region":"string","RegionalLocation":"string","Address":"string","ConstructionArea":"string","State":"string","PropertyType":"string","AveragePrice":0,"TotalPrice":"string","IsActive":true,"HouseTypes":["string"],"Characteristics":["string"],"Score":0,"CommentCount":0,"Row":0,"Sort":0,"Topping":true,"Id":0}
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

    public static class DataBean {
        /**
         * NickName : string
         * UAvatar : string
         * UScore : 0
         * UContent : string
         * UCreationTime : 2019-05-07T08:38:55.108Z
         * BusinessCardName : string
         * SAvatar : string
         * SScore : 0
         * SContent : string
         * SCreationTime : 2019-05-07T08:38:55.108Z
         * PremisesName : string
         * Picture : string
         * Province : string
         * City : string
         * Region : string
         * RegionalLocation : string
         * Address : string
         * ConstructionArea : string
         * State : string
         * PropertyType : string
         * AveragePrice : 0
         * TotalPrice : string
         * IsActive : true
         * HouseTypes : ["string"]
         * Characteristics : ["string"]
         * Score : 0
         * CommentCount : 0
         * Row : 0
         * Sort : 0
         * Topping : true
         * Id : 0
         */

        private String NickName;
        private String UAvatar;
        private float UScore;
        private String UContent;
        private String UCreationTime;
        private String BusinessCardName;
        private String SAvatar;
        private float SScore;
        private String SContent;
        private String SCreationTime;
        private String PremisesName;
        private String Picture;
        private String Province;
        private String City;
        private String Region;
        private String RegionalLocation;
        private String Address;
        private String ConstructionArea;
        private String State;
        private String PropertyType;
        private int AveragePrice;
        private String TotalPrice;
        private boolean IsActive;
        private int Score;
        private int CommentCount;
        private int Row;
        private int Sort;
        private boolean Topping;
        private int Id;
        private List<String> HouseTypes;
        private List<String> Characteristics;

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getUAvatar() {
            return UAvatar;
        }

        public void setUAvatar(String UAvatar) {
            this.UAvatar = UAvatar;
        }

        public float getUScore() {
            return UScore;
        }

        public void setUScore(float UScore) {
            this.UScore = UScore;
        }

        public String getUContent() {
            return UContent;
        }

        public void setUContent(String UContent) {
            this.UContent = UContent;
        }

        public String getUCreationTime() {
            if (!TextUtils.isEmpty(UCreationTime)) {
                UCreationTime = TimeUtil.getStringToDate3(UCreationTime);
            }
            return UCreationTime;
        }

        public void setUCreationTime(String UCreationTime) {
            this.UCreationTime = UCreationTime;
        }

        public String getBusinessCardName() {
            return BusinessCardName;
        }

        public void setBusinessCardName(String BusinessCardName) {
            this.BusinessCardName = BusinessCardName;
        }

        public String getSAvatar() {
            return SAvatar;
        }

        public void setSAvatar(String SAvatar) {
            this.SAvatar = SAvatar;
        }

        public float getSScore() {
            return SScore;
        }

        public void setSScore(float SScore) {
            this.SScore = SScore;
        }

        public String getSContent() {
            return SContent;
        }

        public void setSContent(String SContent) {
            this.SContent = SContent;
        }

        public String getSCreationTime() {
            if (!TextUtils.isEmpty(SCreationTime)) {
                SCreationTime = TimeUtil.getStringToDate3(SCreationTime);
            }
            return SCreationTime;
        }

        public void setSCreationTime(String SCreationTime) {
            this.SCreationTime = SCreationTime;
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

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getRegionalLocation() {
            return RegionalLocation;
        }

        public void setRegionalLocation(String RegionalLocation) {
            this.RegionalLocation = RegionalLocation;
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

        public int getAveragePrice() {
            return AveragePrice;
        }

        public void setAveragePrice(int AveragePrice) {
            this.AveragePrice = AveragePrice;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public boolean isIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public int getRow() {
            return Row;
        }

        public void setRow(int Row) {
            this.Row = Row;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public boolean isTopping() {
            return Topping;
        }

        public void setTopping(boolean Topping) {
            this.Topping = Topping;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<String> getHouseTypes() {
            return HouseTypes;
        }

        public void setHouseTypes(List<String> HouseTypes) {
            this.HouseTypes = HouseTypes;
        }

        public List<String> getCharacteristics() {
            return Characteristics;
        }

        public void setCharacteristics(List<String> Characteristics) {
            this.Characteristics = Characteristics;
        }
    }
}
