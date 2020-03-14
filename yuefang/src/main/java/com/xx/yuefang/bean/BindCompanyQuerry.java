package com.xx.yuefang.bean;

public class BindCompanyQuerry {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"CompanyId":0,"CompanyName":null,"ExamineState":0,"Id":0}
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
         * CompanyId : 0
         * CompanyName : null
         * ExamineState : 0
         * Id : 0
         */

        private int CompanyId;
        private String CompanyName;
        private int ExamineState;
        private int Id;

        public int getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(int CompanyId) {
            this.CompanyId = CompanyId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public int getExamineState() {
            return ExamineState;
        }

        public void setExamineState(int ExamineState) {
            this.ExamineState = ExamineState;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
