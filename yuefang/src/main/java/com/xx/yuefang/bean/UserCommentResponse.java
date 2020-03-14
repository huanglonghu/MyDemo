package com.xx.yuefang.bean;

import java.util.List;

public class UserCommentResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"佳兆业悦峰","UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":3,"Content":"#实地看过#高大上","CreationTime":"2019-11-29T14:38:48.9581142+08:00","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"PremisesCommentDtos":[],"Id":242}
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
