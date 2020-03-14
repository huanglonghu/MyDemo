package com.xx.yuefang.bean;

public class ReviewNumResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"CheckPendingNum":1,"AuditedNum":0}
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
         * CheckPendingNum : 1
         * AuditedNum : 0
         */

        private int CheckPendingNum;
        private int AuditedNum;

        public int getCheckPendingNum() {
            return CheckPendingNum;
        }

        public void setCheckPendingNum(int CheckPendingNum) {
            this.CheckPendingNum = CheckPendingNum;
        }

        public int getAuditedNum() {
            return AuditedNum;
        }

        public void setAuditedNum(int AuditedNum) {
            this.AuditedNum = AuditedNum;
        }
    }
}
