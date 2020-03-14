package com.xx.yuefang.bean;

public class RegisterBody {

    /**
     * PhoneNumber : string
     * Code : string
     * NickName : string
     * Avatar : string
     * Sex : true
     * LoginType : 0
     * OpenId : string
     */

    private String PhoneNumber;
    private String Code;
    private String NickName;
    private String Avatar;
    private boolean Sex;
    private Integer LoginType;
    private String OpenId;
    private String Password;


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean Sex) {
        this.Sex = Sex;
    }

    public int getLoginType() {
        return LoginType;
    }

    public void setLoginType(int LoginType) {
        this.LoginType = LoginType;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }
}
