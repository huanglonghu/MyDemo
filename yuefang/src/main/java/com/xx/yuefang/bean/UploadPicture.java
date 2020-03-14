package com.xx.yuefang.bean;

public class UploadPicture {


    /**
     * Errcode : 0
     * Message : success
     * Data : User/13076737863\b996b72921d7427ab14e0703c456dbca.form-data
     */

    private int Errcode;
    private String Message;
    private String Data;

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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
