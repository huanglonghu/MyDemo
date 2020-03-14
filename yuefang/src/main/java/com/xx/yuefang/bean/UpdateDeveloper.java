package com.xx.yuefang.bean;

public class UpdateDeveloper {

    /**
     * CompanyName : string
     * RoleId : 0
     * Avatar : string
     * Introduce : string
     * ReservationDays : 0
     * PhoneNumber : string
     * Code : string
     * Id : 0
     */

    private String CompanyName;
    private int RoleId;
    private String Avatar;
    private String Introduce;
    private int ReservationDays;
    private String PhoneNumber;
    private String Code;
    private long Id;

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String Introduce) {
        this.Introduce = Introduce;
    }

    public int getReservationDays() {
        return ReservationDays;
    }

    public void setReservationDays(int ReservationDays) {
        this.ReservationDays = ReservationDays;
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

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }
}
