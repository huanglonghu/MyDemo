package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class SystemNews {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":2,"Data":[{"NewsType":3,"RelatedId":0,"Context":"10","CreationTime":"2019-04-30T16:29:29","IsRead":false},{"NewsType":3,"RelatedId":0,"Context":"10","CreationTime":"2019-04-30T16:58:05.773","IsRead":false}]}
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
         * Data : [{"NewsType":3,"RelatedId":0,"Context":"10","CreationTime":"2019-04-30T16:29:29","IsRead":false},{"NewsType":3,"RelatedId":0,"Context":"10","CreationTime":"2019-04-30T16:58:05.773","IsRead":false}]
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
             * NewsType : 3
             * RelatedId : 0
             * Context : 10
             * CreationTime : 2019-04-30T16:29:29
             * IsRead : false
             */

            private int NewsType;
            private int RelatedId;
            private String Context;
            private String CreationTime;
            private boolean IsRead;

            public int getNewsType() {
                return NewsType;
            }

            public void setNewsType(int NewsType) {
                this.NewsType = NewsType;
            }

            public int getRelatedId() {
                return RelatedId;
            }

            public void setRelatedId(int RelatedId) {
                this.RelatedId = RelatedId;
            }

            public String getContext() {
                return Context;
            }

            public void setContext(String Context) {
                this.Context = Context;
            }

            public String getCreationTime() {
                if(!TextUtils.isEmpty(CreationTime)){
                    CreationTime= TimeUtil.getStringToDate3(CreationTime);
                }
                return CreationTime;
            }

            public void setCreationTime(String CreationTime) {
                this.CreationTime = CreationTime;
            }

            public boolean isIsRead() {
                return IsRead;
            }

            public void setIsRead(boolean IsRead) {
                this.IsRead = IsRead;
            }
        }
    }
}
