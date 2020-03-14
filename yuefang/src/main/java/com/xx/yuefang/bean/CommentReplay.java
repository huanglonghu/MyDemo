package com.xx.yuefang.bean;

public class CommentReplay {

    /**
     * Errcode : 0
     * Message : success
     * Data : {"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":5,"Content":"还不错！","CreationTime":"2019-04-29T17:38:41.887","Number":0,"PremisesCommentDtos":[],"Id":6}
     */

    private int Errcode;
    private String Message;
    private Comment Data;

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

    public Comment getData() {
        return Data;
    }

    public void setData(Comment Data) {
        this.Data = Data;
    }

}
