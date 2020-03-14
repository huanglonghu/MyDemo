package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.io.Serializable;

public class NewsDetailBean {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Title":"周六清远线","Picture":"NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg","NewsInfoType":1,"NewsInfoTypeName":"推荐","RichTextContent":"<p><img class=\"imglc\" src=\"https://pages.anjukestatic.com/xinfang/img/kft/liucheng2.jpg\" alt=\"\"><\/p><ol class=\"lc\"><li>1、报名成功后，会收到确认短信。<\/li><li>2、在活动前2-4天，工作人员会电话通知集合时间及地点，确认参加后会发送通知短信。<\/li><li>3、报名截止时间为活动前一天22:00。<\/li><li>4、看房流程：在规定时间内到达指定地点签到上车→按照安居客安排线路参观项目→返回集合地点。<\/li><li>5、本次活动最终解释权归安居客所有。<\/li><\/ol>","Number":2,"CreationTime":"2019-03-26T12:01:48.833","Id":1}
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

    public static class DataBean implements Serializable {
        /**
         * Title : 周六清远线
         * Picture : NewsInfo/1/de647e0cb5c74da8a33796045f70829d.jpeg
         * NewsInfoType : 1
         * NewsInfoTypeName : 推荐
         * RichTextContent : <p><img class="imglc" src="https://pages.anjukestatic.com/xinfang/img/kft/liucheng2.jpg" alt=""></p><ol class="lc"><li>1、报名成功后，会收到确认短信。</li><li>2、在活动前2-4天，工作人员会电话通知集合时间及地点，确认参加后会发送通知短信。</li><li>3、报名截止时间为活动前一天22:00。</li><li>4、看房流程：在规定时间内到达指定地点签到上车→按照安居客安排线路参观项目→返回集合地点。</li><li>5、本次活动最终解释权归安居客所有。</li></ol>
         * Number : 2
         * CreationTime : 2019-03-26T12:01:48.833
         * Id : 1
         */

        private String Title;
        private String Picture;
        private int NewsInfoType;
        private String NewsInfoTypeName;
        private String RichTextContent;
        private int Number;
        private String CreationTime;
        private int Id;
        private String Author;
        private int CommentNumber;

        public int getCommentNumber() {
            return CommentNumber;
        }

        public void setCommentNumber(int commentNumber) {
            CommentNumber = commentNumber;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String author) {
            Author = author;
        }

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

        public String getRichTextContent() {
            return RichTextContent;
        }

        public void setRichTextContent(String RichTextContent) {
            this.RichTextContent = RichTextContent;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public String getCreationTime() {
            if (!TextUtils.isEmpty(CreationTime)) {
                CreationTime = TimeUtil.getStringToDate3(CreationTime);
            }
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
