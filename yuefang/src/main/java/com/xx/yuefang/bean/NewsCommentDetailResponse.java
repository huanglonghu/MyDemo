package com.xx.yuefang.bean;

import android.telecom.Call;
import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class NewsCommentDetailResponse {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"UserName":"金刚葫芦娃","Avatar":"User/6/d57239d693b2450bbfd31016ce989bc4.jpg","Rise":0,"IsRise":false,"Content":"Ffff","CreationTime":"2019-10-24T17:56:05.11","Number":0,"NewsInfoCommentDtos":[{"UserName":"咬金啊啊","Avatar":"User/7/a617261290f7478baa9b062d034384fb.jpg","Rise":0,"IsRise":false,"Content":"是","CreationTime":"2019-10-25T15:29:54.177","NewsInfoCommentDtos":[{"UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Content":"Tt56","CreationTime":"2019-10-25T15:37:52.257","Id":145},{"UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Content":"Rtuu","CreationTime":"2019-10-25T15:37:14.813","Id":144}],"Id":142}],"Id":137}
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

        private String UserName;
        private String Avatar;
        private int Rise;
        private boolean IsRise;
        private String Content;
        private String CreationTime;
        private int Number;
        private boolean IsComment;
        private int Id;
        private CurrentCommentBean CurrentComment;
        public CurrentCommentBean getCurrentComment() {
            return CurrentComment;
        }

        public void setCurrentComment(CurrentCommentBean currentComment) {
            CurrentComment = currentComment;
        }
        public boolean isComment() {
            return IsComment;
        }

        public void setComment(boolean comment) {
            IsComment = comment;
        }

        private List<NewsInfoCommentDtosBeanX> NewsInfoCommentDtos;

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

        public int getRise() {
            return Rise;
        }

        public void setRise(int Rise) {
            this.Rise = Rise;
        }

        public boolean isIsRise() {
            return IsRise;
        }

        public void setIsRise(boolean IsRise) {
            this.IsRise = IsRise;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
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

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<NewsInfoCommentDtosBeanX> getNewsInfoCommentDtos() {
            return NewsInfoCommentDtos;
        }

        public void setNewsInfoCommentDtos(List<NewsInfoCommentDtosBeanX> NewsInfoCommentDtos) {
            this.NewsInfoCommentDtos = NewsInfoCommentDtos;
        }

        public static class NewsInfoCommentDtosBeanX {
            /**
             * UserName : 咬金啊啊
             * Avatar : User/7/a617261290f7478baa9b062d034384fb.jpg
             * Rise : 0
             * IsRise : false
             * Content : 是
             * CreationTime : 2019-10-25T15:29:54.177
             * NewsInfoCommentDtos : [{"UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Content":"Tt56","CreationTime":"2019-10-25T15:37:52.257","Id":145},{"UserName":"丽丽","Avatar":"User/2/b2588d73570445a09e99292f2a6d78d1.jpg","Rise":0,"IsRise":false,"Content":"Rtuu","CreationTime":"2019-10-25T15:37:14.813","Id":144}]
             * Id : 142
             */

            private String UserName;
            private String Avatar;
            private int Rise;
            private boolean IsRise;
            private String Content;
            private String CreationTime;
            private boolean IsComment;
            private int Id;
            private List<NewsItemBean> NewsInfoCommentDtos;

            public boolean isComment() {
                return IsComment;
            }

            public void setComment(boolean comment) {
                IsComment = comment;
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

            public int getRise() {
                return Rise;
            }

            public void setRise(int Rise) {
                this.Rise = Rise;
            }

            public boolean isIsRise() {
                return IsRise;
            }

            public void setIsRise(boolean IsRise) {
                this.IsRise = IsRise;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
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

            public List<NewsItemBean> getNewsInfoCommentDtos() {
                return NewsInfoCommentDtos;
            }

            public void setNewsInfoCommentDtos(List<NewsItemBean> NewsInfoCommentDtos) {
                this.NewsInfoCommentDtos = NewsInfoCommentDtos;
            }


        }
    }
}
