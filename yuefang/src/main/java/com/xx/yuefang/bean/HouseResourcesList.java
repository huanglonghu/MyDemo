package com.xx.yuefang.bean;

import java.util.List;

public class HouseResourcesList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":1,"Data":[{"PremisesName":"万科春风十里","Picture":"PremisesBase/1/3.png","Province":"广东省","City":"广州市","Region":"增城区","RegionalLocation":"113.758685,23.28532","Address":"G324(福昆线)","ConstructionArea":"110.00-110.00","State":"在售","PropertyType":"别墅","AveragePrice":10000,"Ranking":"","TotalPrice":"110.00-110.00","IsActive":true,"HouseTypes":["四室","三室"],"Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"ActivityNames":["1-大湾区未来·地铁18号线通达珠江新城"],"Score":2.75,"CommentCount":3,"Row":0,"Sort":0,"Topping":false,"Id":6}]}
     */

    private int Errcode;
    private String Message;
    private DataBeanX Data;

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

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * Count : 1
         * Data : [{"PremisesName":"万科春风十里","Picture":"PremisesBase/1/3.png","Province":"广东省","City":"广州市","Region":"增城区","RegionalLocation":"113.758685,23.28532","Address":"G324(福昆线)","ConstructionArea":"110.00-110.00","State":"在售","PropertyType":"别墅","AveragePrice":10000,"Ranking":"","TotalPrice":"110.00-110.00","IsActive":true,"HouseTypes":["四室","三室"],"Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"ActivityNames":["1-大湾区未来·地铁18号线通达珠江新城"],"Score":2.75,"CommentCount":3,"Row":0,"Sort":0,"Topping":false,"Id":6}]
         */

        private int Count;
        private List<HouseResourceBean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<HouseResourceBean> getData() {
            return Data;
        }

        public void setData(List<HouseResourceBean> Data) {
            this.Data = Data;
        }


    }
}
