package com.xx.yuefang.bean;

public class QaDetail {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"租房孩子有学位吗？","CreationTime":"2019-04-09T11:28:45.993","Number":1,"QuestionsAnswersDtos":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Content":"您好，租房没有学位的奥，业主才有学位的","CreationTime":"2019-04-09T11:30:26.947","Id":3}],"Id":2}
     */

    private int Errcode;
    private String Message;
    private QABean Data;

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

    public QABean getData() {
        return Data;
    }

    public void setData(QABean Data) {
        this.Data = Data;
    }


}
