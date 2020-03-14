package com.xx.yuefang.bean;

public class PremisesNumResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"AllPremisesNum":7,"IsActivePremisesNum":6,"IsNoActivePremisesNum":1}
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
         * AllPremisesNum : 7
         * IsActivePremisesNum : 6
         * IsNoActivePremisesNum : 1
         */

        private int AllPremisesNum;
        private int IsActivePremisesNum;
        private int IsNoActivePremisesNum;

        public int getAllPremisesNum() {
            return AllPremisesNum;
        }

        public void setAllPremisesNum(int AllPremisesNum) {
            this.AllPremisesNum = AllPremisesNum;
        }

        public int getIsActivePremisesNum() {
            return IsActivePremisesNum;
        }

        public void setIsActivePremisesNum(int IsActivePremisesNum) {
            this.IsActivePremisesNum = IsActivePremisesNum;
        }

        public int getIsNoActivePremisesNum() {
            return IsNoActivePremisesNum;
        }

        public void setIsNoActivePremisesNum(int IsNoActivePremisesNum) {
            this.IsNoActivePremisesNum = IsNoActivePremisesNum;
        }
    }
}
