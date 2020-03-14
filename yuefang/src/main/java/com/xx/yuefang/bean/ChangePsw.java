package com.xx.yuefang.bean;

public class ChangePsw {


    /**
     * OldPassword : string
     * Password : string
     * Id : 0
     */

    private String OldPassword;
    private String Password;
    private long Id;

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }
}
