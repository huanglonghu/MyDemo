package com.xx.yuefang.bean;

public class MyAppoint {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"ReservationTime":"2019-03-23T00:00:00","ReservationState":"申请中","PremisesName":"万科春风十里","Picture":"PremisesBase/1/3.png","Province":"广东省","City":"广州市","Region":"增城区","RegionalLocation":"113.758685,23.28532","Address":"G324(福昆线)","HouseTypes":null,"ConstructionArea":"110.00-110.00","State":"在售","PropertyType":"别墅","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"AveragePrice":10000,"Ranking":null,"ActivityNames":null,"Score":null,"CommentCount":null,"TotalPrice":"110.00-110.00","IsActive":true,"Id":7}
     */

    private int Errcode;
    private String Message;
    private AppointBean Data;

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

    public AppointBean getData() {
        return Data;
    }

    public void setData(AppointBean Data) {
        this.Data = Data;
    }


}
