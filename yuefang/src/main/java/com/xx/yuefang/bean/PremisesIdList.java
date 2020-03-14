package com.xx.yuefang.bean;

import java.util.List;

public class PremisesIdList {


    /**
     * Errcode : Success
     * Message : string
     * Data : {"Total":0,"Index":0,"Ids":[0]}
     */

    private String Errcode;
    private String Message;
    private DataBean Data;

    public String getErrcode() {
        return Errcode;
    }

    public void setErrcode(String Errcode) {
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
         * Total : 0
         * Index : 0
         * Ids : [0]
         */

        private int Total;
        private int Index;
        private List<Integer> Ids;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public int getIndex() {
            return Index;
        }

        public void setIndex(int Index) {
            this.Index = Index;
        }

        public List<Integer> getIds() {
            return Ids;
        }

        public void setIds(List<Integer> Ids) {
            this.Ids = Ids;
        }
    }
}
