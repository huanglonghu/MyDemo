package com.xx.yuefang.bean;

import java.util.List;

public class ADList {


    /**
     * Errcode : 0
     * Message : success
     * Data : [{"AdName":"金融高新区D区住宅产品线丰富","Picture":"AdInfo/1/6a9d0d1b10764a349127161fe9210c25.jpeg","AdInfoType":3,"AdInfoTypeName":"链接","JumpLink":"www.baidu.com","Id":1},{"AdName":"金融高新区D区住宅产品线丰富","Picture":"AdInfo/1/6a9d0d1b10764a349127161fe9210c25.jpeg","AdInfoType":1,"AdInfoTypeName":"楼盘","JumpLink":"6","Id":2},{"AdName":"金融高新区D区住宅产品线丰富","Picture":"AdInfo/1/6a9d0d1b10764a349127161fe9210c25.jpeg","AdInfoType":2,"AdInfoTypeName":"资讯","JumpLink":"1","Id":3}]
     */

    private int Errcode;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * AdName : 金融高新区D区住宅产品线丰富
         * Picture : AdInfo/1/6a9d0d1b10764a349127161fe9210c25.jpeg
         * AdInfoType : 3
         * AdInfoTypeName : 链接
         * JumpLink : www.baidu.com
         * Id : 1
         */

        private String AdName;
        private String Picture;
        private int AdInfoType;
        private String AdInfoTypeName;
        private String JumpLink;
        private int Id;

        public String getAdName() {
            return AdName;
        }

        public void setAdName(String AdName) {
            this.AdName = AdName;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public int getAdInfoType() {
            return AdInfoType;
        }

        public void setAdInfoType(int AdInfoType) {
            this.AdInfoType = AdInfoType;
        }

        public String getAdInfoTypeName() {
            return AdInfoTypeName;
        }

        public void setAdInfoTypeName(String AdInfoTypeName) {
            this.AdInfoTypeName = AdInfoTypeName;
        }

        public String getJumpLink() {
            return JumpLink;
        }

        public void setJumpLink(String JumpLink) {
            this.JumpLink = JumpLink;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
