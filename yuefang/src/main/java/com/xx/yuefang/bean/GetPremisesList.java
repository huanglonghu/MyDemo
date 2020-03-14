package com.xx.yuefang.bean;

import java.util.List;

public class GetPremisesList {


    /**
     * Where : string
     * Region : string
     * StartPrice : 0
     * EndPrice : 0
     * StartTotalPrice : 0
     * EndTotalPrice : 0
     * HouseTypes : [0]
     * CharacteristicTypes : [0]
     * PropertyTypes : [0]
     * StartArea : 0
     * EndArea : 0
     * State : 0
     * OpeningStartTime : 2019-04-17T02:02:53.417Z
     * OpeningEndTime : 2019-04-17T02:02:53.417Z
     * RenovationType : 0
     * UserInitialize : true
     * Sort : 0
     * Page : 0
     * Limit : 0
     */

    private String Where;
    private String Region;
    private int StartPrice;
    private int EndPrice;
    private int StartTotalPrice;
    private int EndTotalPrice;
    private int StartArea;
    private int EndArea;
    private int State;
    private String OpeningStartTime;
    private String OpeningEndTime;
    private int RenovationType;
    private boolean UserInitialize;
    private int Sort;
    private int Page;
    private int Limit;
    private List<Integer> HouseTypes;
    private List<Integer> CharacteristicTypes;
    private List<Integer> PropertyTypes;

    public GetPremisesList() {}

    public String getWhere() {
        return Where;
    }

    public void setWhere(String Where) {
        this.Where = Where;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public int getStartPrice() {
        return StartPrice;
    }

    public void setStartPrice(int StartPrice) {
        this.StartPrice = StartPrice;
    }

    public int getEndPrice() {
        return EndPrice;
    }

    public void setEndPrice(int EndPrice) {
        this.EndPrice = EndPrice;
    }

    public int getStartTotalPrice() {
        return StartTotalPrice;
    }

    public void setStartTotalPrice(int StartTotalPrice) {
        this.StartTotalPrice = StartTotalPrice;
    }

    public int getEndTotalPrice() {
        return EndTotalPrice;
    }

    public void setEndTotalPrice(int EndTotalPrice) {
        this.EndTotalPrice = EndTotalPrice;
    }

    public int getStartArea() {
        return StartArea;
    }

    public void setStartArea(int StartArea) {
        this.StartArea = StartArea;
    }

    public int getEndArea() {
        return EndArea;
    }

    public void setEndArea(int EndArea) {
        this.EndArea = EndArea;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getOpeningStartTime() {
        return OpeningStartTime;
    }

    public void setOpeningStartTime(String OpeningStartTime) {
        this.OpeningStartTime = OpeningStartTime;
    }

    public String getOpeningEndTime() {
        return OpeningEndTime;
    }

    public void setOpeningEndTime(String OpeningEndTime) {
        this.OpeningEndTime = OpeningEndTime;
    }

    public int getRenovationType() {
        return RenovationType;
    }

    public void setRenovationType(int RenovationType) {
        this.RenovationType = RenovationType;
    }

    public boolean isUserInitialize() {
        return UserInitialize;
    }

    public void setUserInitialize(boolean UserInitialize) {
        this.UserInitialize = UserInitialize;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int Page) {
        this.Page = Page;
    }

    public int getLimit() {
        return Limit;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }

    public List<Integer> getHouseTypes() {
        return HouseTypes;
    }

    public void setHouseTypes(List<Integer> HouseTypes) {
        this.HouseTypes = HouseTypes;
    }

    public List<Integer> getCharacteristicTypes() {
        return CharacteristicTypes;
    }

    public void setCharacteristicTypes(List<Integer> CharacteristicTypes) {
        this.CharacteristicTypes = CharacteristicTypes;
    }

    public List<Integer> getPropertyTypes() {
        return PropertyTypes;
    }

    public void setPropertyTypes(List<Integer> PropertyTypes) {
        this.PropertyTypes = PropertyTypes;
    }

}
