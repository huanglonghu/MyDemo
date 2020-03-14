package com.xx.yuefang.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class CustomMessage implements Serializable {

    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"佳兆业悦峰","Address":"珠海市三灶镇金海岸大道229号","ConstructionArea":"21,970","UnitPrice":" 20000","ListPicture":"PremisesBase/10/b070dcf7d616420e8eef1179322161c6.jpeg"}
     */

    private int Errcode;
    private String Message;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * PremisesName : 佳兆业悦峰
         * Address : 珠海市三灶镇金海岸大道229号
         * ConstructionArea : 21,970
         * UnitPrice :  20000
         * ListPicture : PremisesBase/10/b070dcf7d616420e8eef1179322161c6.jpeg
         */

        private String PremisesName;
        private String Address;
        private String ConstructionArea;
        private String UnitPrice;
        private String ListPicture;
        private int Id;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getPremisesName() {
            if (TextUtils.isEmpty(PremisesName)) {
                return "";
            }
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

        public String getAddress() {
            if (TextUtils.isEmpty(Address)) {
                return "";
            }
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getConstructionArea() {
            if (TextUtils.isEmpty(ConstructionArea)) {
                return "";
            }
            return ConstructionArea;
        }

        public void setConstructionArea(String ConstructionArea) {
            this.ConstructionArea = ConstructionArea;
        }

        public String getUnitPrice() {
            if (TextUtils.isEmpty(UnitPrice)) {
                return "";
            }
            return UnitPrice;
        }

        public void setUnitPrice(String UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public String getListPicture() {
            if (TextUtils.isEmpty(ListPicture)) {
                return "";
            }
            return ListPicture;
        }

        public void setListPicture(String ListPicture) {
            this.ListPicture = ListPicture;
        }
    }
}
