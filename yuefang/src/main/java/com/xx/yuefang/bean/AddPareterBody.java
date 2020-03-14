package com.xx.yuefang.bean;

public class AddPareterBody {
    /**
     * CompanyId : 0
     * PhoneNumber : string
     * Password : string
     * IDCardPositive : string
     * IDCardReverseSide : string
     * Avatar : string
     * Sex : true
     * Telephone : string
     * UserName : string
     * Email : string
     * Profile : string
     * Address : string
     */

    private int CompanyId;
    private String PhoneNumber;
    private String Password;
    private String IDCardPositive;
    private String IDCardReverseSide;
    private String Avatar;
    private boolean Sex;
    private String Telephone;
    private String UserName;
    private String Email;
    private String Profile;
    private String Address;

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
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

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String Profile) {
        this.Profile = Profile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
