package com.xx.yuefang.bean;

public class CompanyRegisterBody {
    /**
     * PhoneNumber : string
     * Code : string
     * BusinessLicense : string
     * CompanyName : string
     * UserName : string
     */

    private String PhoneNumber;
    private String Code;
    private String BusinessLicense;
    private String CompanyName;
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

    public String getBusinessLicense() {
        return BusinessLicense;
    }

    public void setBusinessLicense(String BusinessLicense) {
        this.BusinessLicense = BusinessLicense;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
