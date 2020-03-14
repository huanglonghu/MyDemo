package com.xx.yuefang.bean;

import android.text.TextUtils;

import java.util.List;

public class PremisesBean {
    /**
     * PremisesName : 中泰天境花园
     * Picture : PremisesBase\1\1.png
     * Province : 广东省
     * City : 广州市
     * Region : 天河
     * RegionalLocation : 113.499785,23.400163
     * Address : 广东省广州市黄埔区凤凰三路
     * HouseTypes : []
     * ConstructionArea : null
     * State : 在售
     * PropertyType : 别墅
     * Characteristics : []
     * AveragePrice : 0.0
     * Ranking :
     * ActivityNames : []
     * Score : null
     * CommentCount : 0
     * TotalPrice : null
     * IsActive : true
     * Id : 7
     */



    private String PremisesName;
    private String BrowseTime;
    private String Picture;
    private String Province;
    private String City;
    private String Region;
    private int PremisesBaseId;
    private int DeveloperId;
    private String RegionalLocation;
    private String Address;
    private String ConstructionArea;
    private String State;
    private String PropertyType;
    private double AveragePrice;
    private String Ranking;
    private double Score;
    private int CommentCount;
    private String TotalPrice;
    private boolean IsActive;
    private int Id;
    private List<String> HouseTypes;
    private List<String> Characteristics;
    private List<String> ActivityNames;
    private String CollectionTime;


    public int getPremisesBaseId() {
        return PremisesBaseId;
    }

    public void setPremisesBaseId(int premisesBaseId) {
        PremisesBaseId = premisesBaseId;
    }

    public int getDeveloperId() {
        return DeveloperId;
    }

    public void setDeveloperId(int developerId) {
        DeveloperId = developerId;
    }

    public String getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        CollectionTime = collectionTime;
    }

    public String getBrowseTime() {
        return BrowseTime;
    }

    public void setBrowseTime(String browseTime) {
        BrowseTime = browseTime;
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
        if(!TextUtils.isEmpty(ConstructionArea)){
            ConstructionArea = ConstructionArea.replaceAll(".00", "");
        }
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

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String Ranking) {
        this.Ranking = Ranking;
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

    public List<String> getActivityNames() {
        return ActivityNames;
    }

    public void setActivityNames(List<String> ActivityNames) {
        this.ActivityNames = ActivityNames;
    }
}

