package com.xx.yuefang.bean;

import com.xx.yuefang.database.entity.UserBean;

public class User {
    /**
     * Errcode : 0
     * Message : success
     * Data : {"PhoneNumber":"13076737863","NickName":null,"Avatar":"","QQOpenId":null,"WeChatOpenId":null,"Balance":0,"Credit":100,"Score":5,"Bank":null,"CardNumber":null,"Householder":null,"Branch":null,"CreationTime":"2019-03-14T11:35:10.857","LastLoginTime":"2019-03-20T16:56:20.247","Id":2}
     */

    private int Errcode;
    private String Message;
    private UserBean Data;

    public int getErrcode() {
        return Errcode;
    }

    public void setErrcode(int Errcode) {
        this.Errcode = Errcode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public UserBean getData() {
        return Data;
    }

    public void setData(UserBean Data) {
        this.Data = Data;
    }


}
