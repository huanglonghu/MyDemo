package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class ReViewResponse {


    /**
     * Errcode : Success
     * Message : string
     * Data : {"Count":0,"Data":[{"ApplyrType":"string","UserName":"string","Avatar":"string","PhoneNumber":"string","CreationTime":"2019-12-24T06:35:57.881Z","ApplyTypeStr":"string","Id":0}]}
     */

    private String Errcode;
    private String Message;
    private DataBeanX Data;

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

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * Count : 0
         * Data : [{"ApplyrType":"string","UserName":"string","Avatar":"string","PhoneNumber":"string","CreationTime":"2019-12-24T06:35:57.881Z","ApplyTypeStr":"string","Id":0}]
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

        public static class DataBean {
            /**
             * ApplyrType : string
             * UserName : string
             * Avatar : string
             * PhoneNumber : string
             * CreationTime : 2019-12-24T06:35:57.881Z
             * ApplyTypeStr : string
             * Id : 0
             */

            private String ApplyrType;
            private String UserName;
            private String Avatar;
            private String PhoneNumber;
            private String CreationTime;
            private String ApplyTypeStr;
            private int Id;

            public String getApplyrType() {
                return ApplyrType;
            }

            public void setApplyrType(String ApplyrType) {
                this.ApplyrType = ApplyrType;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getAvatar() {
                return Avatar;
            }

            public void setAvatar(String Avatar) {
                this.Avatar = Avatar;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getCreationTime() {
                if (!TextUtils.isEmpty(CreationTime)) {
                    CreationTime = TimeUtil.getStringToDate(CreationTime);
                }
                return CreationTime;
            }

            public void setCreationTime(String CreationTime) {
                this.CreationTime = CreationTime;
            }

            public String getApplyTypeStr() {
                return ApplyTypeStr;
            }

            public void setApplyTypeStr(String ApplyTypeStr) {
                this.ApplyTypeStr = ApplyTypeStr;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }
        }
    }
}
