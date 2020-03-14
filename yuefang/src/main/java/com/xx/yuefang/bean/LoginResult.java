package com.xx.yuefang.bean;

public class LoginResult {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"NickName":null,"UserType":4,"Token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VyTmFtZSI6bnVsbCwiRXhwaXJlZFRpbWUiOiIyMDE5LTAzLTI1VDE5OjUzOjEyLjQ1NTgxOTYrMDg6MDAiLCJVc2VySWQiOjIsIlVzZXJUeXBlIjo0fQ.orwYWwGlbCB5MTharZ2YFgEeE-BmG-jOTbvy95ozD98","Expired":7200,"Id":2}
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
         * NickName : null
         * UserType : 4
         * Token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VyTmFtZSI6bnVsbCwiRXhwaXJlZFRpbWUiOiIyMDE5LTAzLTI1VDE5OjUzOjEyLjQ1NTgxOTYrMDg6MDAiLCJVc2VySWQiOjIsIlVzZXJUeXBlIjo0fQ.orwYWwGlbCB5MTharZ2YFgEeE-BmG-jOTbvy95ozD98
         * Expired : 7200
         * Id : 2
         */

        private String NickName;
        private int UserType;
        private String Token;
        private int Expired;
        private int Id;

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public int getExpired() {
            return Expired;
        }

        public void setExpired(int Expired) {
            this.Expired = Expired;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
