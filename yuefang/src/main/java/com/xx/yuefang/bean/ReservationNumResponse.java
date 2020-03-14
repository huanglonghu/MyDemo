package com.xx.yuefang.bean;

public class ReservationNumResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"WaitingNum":0,"InProgressNum":0,"CompletedNum":0,"CancelledNum":2}
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
         * WaitingNum : 0
         * InProgressNum : 0
         * CompletedNum : 0
         * CancelledNum : 2
         */

        private int WaitingNum;
        private int InProgressNum;
        private int CompletedNum;
        private int CancelledNum;

        public int getWaitingNum() {
            return WaitingNum;
        }

        public void setWaitingNum(int WaitingNum) {
            this.WaitingNum = WaitingNum;
        }

        public int getInProgressNum() {
            return InProgressNum;
        }

        public void setInProgressNum(int InProgressNum) {
            this.InProgressNum = InProgressNum;
        }

        public int getCompletedNum() {
            return CompletedNum;
        }

        public void setCompletedNum(int CompletedNum) {
            this.CompletedNum = CompletedNum;
        }

        public int getCancelledNum() {
            return CancelledNum;
        }

        public void setCancelledNum(int CancelledNum) {
            this.CancelledNum = CancelledNum;
        }
    }
}
