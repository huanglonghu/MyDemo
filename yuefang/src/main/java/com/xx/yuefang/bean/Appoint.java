package com.xx.yuefang.bean;

public class Appoint {


    /**
     * PremisesBaseId : 0
     * SalespersonId : 0
     * UserCall : string
     * PhoneNumber : string
     * Code : string
     * LookHouseNumber : 0
     * IsPickUp : 0
     * ReservationTime : 2019-03-21T09:17:34.682Z
     */

    private int PremisesBaseId;
    private int SalespersonId;
    private String UserCall;
    private String PhoneNumber;
    private String Code;
    private String LookHouseNumber;
    private int IsPickUp;
    private String ReservationTime;

    public int getPremisesBaseId() {
        return PremisesBaseId;
    }

    public void setPremisesBaseId(int PremisesBaseId) {
        this.PremisesBaseId = PremisesBaseId;
    }

    public int getSalespersonId() {
        return SalespersonId;
    }

    public void setSalespersonId(int SalespersonId) {
        this.SalespersonId = SalespersonId;
    }

    public String getUserCall() {
        return UserCall;
    }

    public void setUserCall(String UserCall) {
        this.UserCall = UserCall;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getLookHouseNumber() {
        return LookHouseNumber;
    }

    public void setLookHouseNumber(String LookHouseNumber) {
        this.LookHouseNumber = LookHouseNumber;
    }

    public int getIsPickUp() {
        return IsPickUp;
    }

    public void setIsPickUp(int IsPickUp) {
        this.IsPickUp = IsPickUp;
    }

    public String getReservationTime() {
        return ReservationTime;
    }

    public void setReservationTime(String ReservationTime) {
        this.ReservationTime = ReservationTime;
    }
}
