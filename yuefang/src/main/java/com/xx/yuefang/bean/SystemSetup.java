package com.xx.yuefang.bean;

public class SystemSetup {
    /**
     * Errcode : Success
     * Message : string
     * Data : {"HotKeywords":"string","UserQuickChat":"string","SalespersonQuickChat":"string","CreationTime":"2019-08-29T09:19:11.694Z","Id":0}
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
         * HotKeywords : string
         * UserQuickChat : string
         * SalespersonQuickChat : string
         * CreationTime : 2019-08-29T09:19:11.694Z
         * Id : 0
         */

        private String HotKeywords;
        private String UserQuickChat;
        private String SalespersonQuickChat;
        private String CreationTime;
        private int Id;

        public String getHotKeywords() {
            return HotKeywords;
        }

        public void setHotKeywords(String HotKeywords) {
            this.HotKeywords = HotKeywords;
        }

        public String getUserQuickChat() {
            return UserQuickChat;
        }

        public void setUserQuickChat(String UserQuickChat) {
            this.UserQuickChat = UserQuickChat;
        }

        public String getSalespersonQuickChat() {
            return SalespersonQuickChat;
        }

        public void setSalespersonQuickChat(String SalespersonQuickChat) {
            this.SalespersonQuickChat = SalespersonQuickChat;
        }

        public String getCreationTime() {
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
