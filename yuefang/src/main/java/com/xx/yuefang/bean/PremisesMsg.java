package com.xx.yuefang.bean;

import java.util.List;

public class PremisesMsg {


    /**
     * Errcode : 0
     * Message : success
     * Data : [{"PremisesName":"亚运城","Id":1},{"PremisesName":"亚运城","Id":2},{"PremisesName":"江畔雅苑","Id":3},{"PremisesName":"再生时代大厦","Id":6},{"PremisesName":"斯越云谷","Id":8},{"PremisesName":"星汉·港湾国际","Id":9}]
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
         * PremisesName : 亚运城
         * Id : 1
         */

        private String PremisesName;
        private int Id;

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
