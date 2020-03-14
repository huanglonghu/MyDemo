package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class NewsList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":2,"Data":[{"Title":"周六清远线","Picture":"NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg","NewsInfoType":2,"NewsInfoTypeName":"2019预测","RichTextContent":null,"Number":6,"CreationTime":"2019-03-26T12:01:48.833","Id":3},{"Title":"周六清远线","Picture":"NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg","NewsInfoType":2,"NewsInfoTypeName":"2019预测","RichTextContent":null,"Number":2,"CreationTime":"2019-03-26T12:01:48.833","Id":2}]}
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
         * Data : [{"Title":"周六清远线","Picture":"NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg","NewsInfoType":2,"NewsInfoTypeName":"2019预测","RichTextContent":null,"Number":6,"CreationTime":"2019-03-26T12:01:48.833","Id":3},{"Title":"周六清远线","Picture":"NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg","NewsInfoType":2,"NewsInfoTypeName":"2019预测","RichTextContent":null,"Number":2,"CreationTime":"2019-03-26T12:01:48.833","Id":2}]
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
             * Title : 周六清远线
             * Picture : NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg
             * NewsInfoType : 2
             * NewsInfoTypeName : 2019预测
             * RichTextContent : null
             * Number : 6
             * CreationTime : 2019-03-26T12:01:48.833
             * Id : 3
             */

            private String Title;
            private String Picture;
            private int NewsInfoType;
            private String NewsInfoTypeName;
            private Object RichTextContent;
            private int Number;
            private String CreationTime;
            private int Id;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getPicture() {
                return Picture;
            }

            public void setPicture(String Picture) {
                this.Picture = Picture;
            }

            public int getNewsInfoType() {
                return NewsInfoType;
            }

            public void setNewsInfoType(int NewsInfoType) {
                this.NewsInfoType = NewsInfoType;
            }

            public String getNewsInfoTypeName() {
                return NewsInfoTypeName;
            }

            public void setNewsInfoTypeName(String NewsInfoTypeName) {
                this.NewsInfoTypeName = NewsInfoTypeName;
            }

            public Object getRichTextContent() {
                return RichTextContent;
            }

            public void setRichTextContent(Object RichTextContent) {
                this.RichTextContent = RichTextContent;
            }

            public int getNumber() {
                return Number;
            }

            public void setNumber(int Number) {
                this.Number = Number;
            }

            public String getCreationTime() {
                String time="";
                if(!TextUtils.isEmpty(CreationTime)){
                    time= TimeUtil.getNewsDate(CreationTime);
                }
                return time;
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
}
