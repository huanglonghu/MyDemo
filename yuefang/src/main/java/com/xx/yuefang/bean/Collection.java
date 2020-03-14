package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class Collection {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":2,"Data":[{"CollectionTime":"2019-04-28T14:33:21.073","PremisesBaseId":6,"DeveloperId":5,"PremisesName":"再生时代大厦","Picture":"PremisesBase/6/d5a360572bca4a1c9e0b8de0a4ed0a28.jpeg","Province":"广东省","City":"珠海市","Region":"香洲区","RegionalLocation":"113.500978,22.231835","Address":"屏北二路55号  ","ConstructionArea":"6.04万","State":"在售","PropertyType":"写字楼","AveragePrice":0,"TotalPrice":"待定","IsActive":true,"HouseTypes":["两室","三室"],"Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Score":3.63,"CommentCount":19,"Row":0,"Sort":0,"Topping":false,"Id":19},{"CollectionTime":"2019-04-22T14:27:42.637","PremisesBaseId":7,"DeveloperId":13,"PremisesName":"中泰天境花园","Picture":"PremisesBase/1/1.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.499785,23.400163","Address":"凤凰三路","ConstructionArea":"暂无","State":"在售","PropertyType":"别墅","AveragePrice":0,"TotalPrice":"暂无","IsActive":true,"HouseTypes":[],"Characteristics":[],"Score":0,"CommentCount":0,"Row":0,"Sort":0,"Topping":false,"Id":5}]}
     */

    private int Errcode;
    private String Message;
    private DataBeanX Data;

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

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * Count : 2
         * Data : [{"CollectionTime":"2019-04-28T14:33:21.073","PremisesBaseId":6,"DeveloperId":5,"PremisesName":"再生时代大厦","Picture":"PremisesBase/6/d5a360572bca4a1c9e0b8de0a4ed0a28.jpeg","Province":"广东省","City":"珠海市","Region":"香洲区","RegionalLocation":"113.500978,22.231835","Address":"屏北二路55号  ","ConstructionArea":"6.04万","State":"在售","PropertyType":"写字楼","AveragePrice":0,"TotalPrice":"待定","IsActive":true,"HouseTypes":["两室","三室"],"Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Score":3.63,"CommentCount":19,"Row":0,"Sort":0,"Topping":false,"Id":19},{"CollectionTime":"2019-04-22T14:27:42.637","PremisesBaseId":7,"DeveloperId":13,"PremisesName":"中泰天境花园","Picture":"PremisesBase/1/1.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.499785,23.400163","Address":"凤凰三路","ConstructionArea":"暂无","State":"在售","PropertyType":"别墅","AveragePrice":0,"TotalPrice":"暂无","IsActive":true,"HouseTypes":[],"Characteristics":[],"Score":0,"CommentCount":0,"Row":0,"Sort":0,"Topping":false,"Id":5}]
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

        public static class DataBean {
            /**
             * CollectionTime : 2019-04-28T14:33:21.073
             * PremisesBaseId : 6
             * DeveloperId : 5
             * PremisesName : 再生时代大厦
             * Picture : PremisesBase/6/d5a360572bca4a1c9e0b8de0a4ed0a28.jpeg
             * Province : 广东省
             * City : 珠海市
             * Region : 香洲区
             * RegionalLocation : 113.500978,22.231835
             * Address : 屏北二路55号
             * ConstructionArea : 6.04万
             * State : 在售
             * PropertyType : 写字楼
             * AveragePrice : 0.0
             * TotalPrice : 待定
             * IsActive : true
             * HouseTypes : ["两室","三室"]
             * Characteristics : ["品牌开发商","多轨交","大型社区","大平层"]
             * Score : 3.63
             * CommentCount : 19
             * Row : 0
             * Sort : 0
             * Topping : false
             * Id : 19
             */

            private String CollectionTime;
            private int PremisesBaseId;
            private int DeveloperId;
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
            private double AveragePrice;
            private String TotalPrice;
            private boolean IsActive;
            private double Score;
            private int CommentCount;
            private int Row;
            private int Sort;
            private boolean Topping;
            private int Id;
            private List<String> HouseTypes;
            private List<String> Characteristics;

            public String getCollectionTime() {
                if (!TextUtils.isEmpty(CollectionTime)) {
                    CollectionTime = TimeUtil.getStringToDate3(CollectionTime);
                }
                return CollectionTime;
            }

            public void setCollectionTime(String CollectionTime) {

                this.CollectionTime = CollectionTime;
            }

            public int getPremisesBaseId() {
                return PremisesBaseId;
            }

            public void setPremisesBaseId(int PremisesBaseId) {
                this.PremisesBaseId = PremisesBaseId;
            }

            public int getDeveloperId() {
                return DeveloperId;
            }

            public void setDeveloperId(int DeveloperId) {
                this.DeveloperId = DeveloperId;
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

            public boolean isIsActive() {
                return IsActive;
            }

            public void setIsActive(boolean IsActive) {
                this.IsActive = IsActive;
            }

            public double getScore() {
                return Score;
            }

            public void setScore(double Score) {
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
}
