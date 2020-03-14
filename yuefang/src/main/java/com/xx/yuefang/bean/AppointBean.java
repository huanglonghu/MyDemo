package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.io.Serializable;
import java.util.List;

public class AppointBean implements Serializable {

    /**
     * ReservationTime : 2019-04-06T00:00:00
     * ReservationState : 已完成
     * ReservationType : 6
     * PremisesName : 万科春风十里
     * Picture : PremisesBase/1/3.png
     * Province : 广东省
     * City : 广州市
     * Region : 增城区
     * RegionalLocation : 113.758685,23.28532
     * Address : G324(福昆线)
     * ConstructionArea : 110.00-110.00
     * State : 在售
     * PropertyType : 别墅
     * AveragePrice : 10000.0
     * TotalPrice : 110.00-110.00
     * IsActive : true
     * HouseTypes : null
     * Characteristics : ["品牌开发商","多轨交","大型社区","大平层"]
     * Score : null
     * CommentCount : null
     * Row : 0
     * Sort : 0
     * Topping : false
     * Id : 9
     */

    private String ReservationTime;
    private String ReservationState;
    private int ReservationType;
    private String PremisesName;
    private int PremisesBaseId;
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
    private Object HouseTypes;
    private Object Score;
    private Object CommentCount;
    private int Row;
    private int Sort;
    private boolean Topping;
    private int Id;
    private int UserId;
    private List<String> Characteristics;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getPremisesBaseId() {
        return PremisesBaseId;
    }

    public void setPremisesBaseId(int premisesBaseId) {
        PremisesBaseId = premisesBaseId;
    }

    public String getReservationTime() {
        if(!TextUtils.isEmpty(ReservationTime)){
            ReservationTime= TimeUtil.getStringToDate2(ReservationTime);
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

    public Object getHouseTypes() {
        return HouseTypes;
    }

    public void setHouseTypes(Object HouseTypes) {
        this.HouseTypes = HouseTypes;
    }

    public Object getScore() {
        return Score;
    }

    public void setScore(Object Score) {
        this.Score = Score;
    }

    public Object getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(Object CommentCount) {
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

    public List<String> getCharacteristics() {
        return Characteristics;
    }

    public void setCharacteristics(List<String> Characteristics) {
        this.Characteristics = Characteristics;
    }
}
