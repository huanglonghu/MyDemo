package com.xx.yuefang.bean;

import java.util.List;

public class PremisesList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":5,"Data":[{"PremisesName":"中泰天境花园","Picture":"PremisesBase\\1\\1.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.499785,23.400163","Address":"广东省广州市黄埔区凤凰三路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"别墅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":7},{"PremisesName":"万科春风十里","Picture":"PremisesBase\\1\\3.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.758721,23.28532","Address":"广东省广州市增城区G324(福昆线)","HouseTypes":["四室","三室"],"ConstructionArea":"110.00-110.00","State":"在售","PropertyType":"别墅","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"AveragePrice":10000,"Ranking":"","ActivityNames":[],"Score":2.75,"CommentCount":3,"TotalPrice":"110.00-110.00","IsActive":true,"Id":6},{"PremisesName":"江畔雅苑","Picture":"PremisesBase\\1\\3.png","Province":"","City":"","Region":"天河","RegionalLocation":"车陂","Address":"岐山路183号","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"别墅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":3},{"PremisesName":"亚运城","Picture":"PremisesBase\\1\\1.png","Province":"","City":"","Region":"番禺","RegionalLocation":"亚运新城","Address":"兴亚二路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"住宅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":1},{"PremisesName":"亚运城","Picture":"PremisesBase\\1\\2.png","Province":"","City":"","Region":"番禺","RegionalLocation":"亚运新城","Address":"兴亚二路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"住宅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":2}]}
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
         * Count : 5
         * Data : [{"PremisesName":"中泰天境花园","Picture":"PremisesBase\\1\\1.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.499785,23.400163","Address":"广东省广州市黄埔区凤凰三路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"别墅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":7},{"PremisesName":"万科春风十里","Picture":"PremisesBase\\1\\3.png","Province":"广东省","City":"广州市","Region":"天河","RegionalLocation":"113.758721,23.28532","Address":"广东省广州市增城区G324(福昆线)","HouseTypes":["四室","三室"],"ConstructionArea":"110.00-110.00","State":"在售","PropertyType":"别墅","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"AveragePrice":10000,"Ranking":"","ActivityNames":[],"Score":2.75,"CommentCount":3,"TotalPrice":"110.00-110.00","IsActive":true,"Id":6},{"PremisesName":"江畔雅苑","Picture":"PremisesBase\\1\\3.png","Province":"","City":"","Region":"天河","RegionalLocation":"车陂","Address":"岐山路183号","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"别墅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":3},{"PremisesName":"亚运城","Picture":"PremisesBase\\1\\1.png","Province":"","City":"","Region":"番禺","RegionalLocation":"亚运新城","Address":"兴亚二路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"住宅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":1},{"PremisesName":"亚运城","Picture":"PremisesBase\\1\\2.png","Province":"","City":"","Region":"番禺","RegionalLocation":"亚运新城","Address":"兴亚二路","HouseTypes":[],"ConstructionArea":null,"State":"在售","PropertyType":"住宅","Characteristics":[],"AveragePrice":0,"Ranking":"","ActivityNames":[],"Score":null,"CommentCount":0,"TotalPrice":null,"IsActive":true,"Id":2}]
         */

        private int Count;
        private List<PremisesBean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<PremisesBean> getData() {
            return Data;
        }

        public void setData(List<PremisesBean> Data) {
            this.Data = Data;
        }


    }
}
