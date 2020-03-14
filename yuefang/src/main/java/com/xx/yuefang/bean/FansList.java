package com.xx.yuefang.bean;

import java.io.Serializable;
import java.util.List;

public class FansList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":2,"Data":[{"UserId":15,"PhoneNumber":"18824905263","NickName":"18824905263","Avatar":"images/face.jpg"},{"UserId":14,"PhoneNumber":"13250554787","NickName":"13250554787","Avatar":"images/face.jpg"}]}
     */

    private int Errcode;
    private String Message;
    private DataBeanX Data;

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

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * Count : 2
         * Data : [{"UserId":15,"PhoneNumber":"18824905263","NickName":"18824905263","Avatar":"images/face.jpg"},{"UserId":14,"PhoneNumber":"13250554787","NickName":"13250554787","Avatar":"images/face.jpg"}]
         */

        private int Count;
        private List<DataBean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean implements Serializable {
            /**
             * UserId : 15
             * PhoneNumber : 18824905263
             * NickName : 18824905263
             * Avatar : images/face.jpg
             */

            private int UserId;
            private String PhoneNumber;
            private String NickName;
            private String Avatar;

            public int getUserId() {
                return UserId;
            }

            public void setUserId(int UserId) {
                this.UserId = UserId;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getNickName() {
                return NickName;
            }

            public void setNickName(String NickName) {
                this.NickName = NickName;
            }

            public String getAvatar() {
                return Avatar;
            }

            public void setAvatar(String Avatar) {
                this.Avatar = Avatar;
            }
        }
    }
}
