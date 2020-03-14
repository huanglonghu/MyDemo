package com.xx.yuefang.bean;

public class ParterRegisterBody {


    /**
     * PhoneNumber : string
     * Code : string
     * IDCardPositive : string
     * IDCardReverseSide : string
     * UserName : string
     */

    private String PhoneNumber;
    private String Code;
    private String IDCardPositive;
    private String IDCardReverseSide;
    private String UserName;

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

    public String getIDCardPositive() {
        return IDCardPositive;
    }

    public void setIDCardPositive(String IDCardPositive) {
        this.IDCardPositive = IDCardPositive;
    }

    public String getIDCardReverseSide() {
        return IDCardReverseSide;
    }

    public void setIDCardReverseSide(String IDCardReverseSide) {
        this.IDCardReverseSide = IDCardReverseSide;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
