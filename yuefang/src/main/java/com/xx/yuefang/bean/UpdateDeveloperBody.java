package com.xx.yuefang.bean;

public class UpdateDeveloperBody {

    private int Id;

    private String Name;


    private String PhoneNumber;

    private String Code;

    private String Avatar;

    private Integer ReservationDays;

    private String Introduce;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public Integer getReservationDays() {
        return ReservationDays;
    }

    public void setReservationDays(Integer reservationDays) {
        ReservationDays = reservationDays;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }
}
