package com.xx.yuefang.bean;

import java.util.List;

public class NewsCommentResponse {

    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":5,"Data":[{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"这是一条好评","CreationTime":"2019-10-11T14:33:44.26","Number":1,"NewsInfoCommentDtos":[{"UserName":"周先生","Avatar":"User/1/10bfa826512e465b83644a7c2eef02ef.jpg","Rise":0,"IsRise":false,"Content":"哈哈","CreationTime":"2019-10-11T14:55:32.263","Id":6}],"Id":5},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"哦哦哦","CreationTime":"2019-10-10T18:39:36.953","Number":1,"NewsInfoCommentDtos":[{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"Ghjj","CreationTime":"2019-10-11T15:02:42.997","Id":7}],"Id":4},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"你理解","CreationTime":"2019-10-10T18:28:55.073","Number":0,"NewsInfoCommentDtos":[],"Id":3},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"呵呵呵","CreationTime":"2019-10-10T18:27:35.203","Number":0,"NewsInfoCommentDtos":[],"Id":2},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"哈哈哈","CreationTime":"2019-10-10T17:37:53.527","Number":0,"NewsInfoCommentDtos":[],"Id":1}]}
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
         * Count : 5
         * Data : [{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"这是一条好评","CreationTime":"2019-10-11T14:33:44.26","Number":1,"NewsInfoCommentDtos":[{"UserName":"周先生","Avatar":"User/1/10bfa826512e465b83644a7c2eef02ef.jpg","Rise":0,"IsRise":false,"Content":"哈哈","CreationTime":"2019-10-11T14:55:32.263","Id":6}],"Id":5},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"哦哦哦","CreationTime":"2019-10-10T18:39:36.953","Number":1,"NewsInfoCommentDtos":[{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"Ghjj","CreationTime":"2019-10-11T15:02:42.997","Id":7}],"Id":4},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"你理解","CreationTime":"2019-10-10T18:28:55.073","Number":0,"NewsInfoCommentDtos":[],"Id":3},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"呵呵呵","CreationTime":"2019-10-10T18:27:35.203","Number":0,"NewsInfoCommentDtos":[],"Id":2},{"UserName":"丽丽","Avatar":"User/2/ab5432bec08e4c76bd6e2e88113821eb.jpg","Rise":0,"IsRise":false,"Content":"哈哈哈","CreationTime":"2019-10-10T17:37:53.527","Number":0,"NewsInfoCommentDtos":[],"Id":1}]
         */

        private int Count;
        private List<NewsCommentBean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<NewsCommentBean> getData() {
            return Data;
        }

        public void setData(List<NewsCommentBean> Data) {
            this.Data = Data;
        }

    }
}
