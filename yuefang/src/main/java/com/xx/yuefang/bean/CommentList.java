package com.xx.yuefang.bean;

import java.util.List;

public class CommentList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":9,"Data":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Score":1,"Content":"原制油气厂地块，环评结果是说土地和地下水受到污染。**的问题是石化厂就在不远啊！无论如何都会有污染的空气飘过来。会对健康产生隐患。这是**的硬伤！石化厂估计要搬也不会那么容易。哎，可惜了！","CreationTime":"2019-03-19T16:06:31.557","Number":1,"PremisesCommentDtos":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Content":"空气不好 遍地工厂 交通不便","CreationTime":"2019-03-19T17:01:35.107","Id":3}],"Id":2},{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"空气不好 遍地工厂 交通不便","CreationTime":"2019-03-19T17:01:35.107","Number":0,"PremisesCommentDtos":[],"Id":3},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":5,"Content":"还不错！","CreationTime":"2019-04-29T17:38:41.887","Number":0,"PremisesCommentDtos":[],"Id":6},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":5,"Content":"还行吧！比较满意","CreationTime":"2019-04-29T18:00:06.057","Number":0,"PremisesCommentDtos":[],"Id":7},{"UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2,"Content":"位置好，价格便宜","CreationTime":"2019-04-15T15:29:44.397","Number":0,"PremisesCommentDtos":[],"Id":4},{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":0,"IsRise":false,"Fall":1,"IsFall":false,"Score":4.5,"Content":"很满意的楼盘","CreationTime":"2019-03-19T09:27:33.113","Number":0,"PremisesCommentDtos":[],"Id":1},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"一般般","CreationTime":"2019-04-28T19:29:51 2019-05-05 16:35:32.800 30634-30634/com.example.yuefang E/part1: .227","Number":2,"PremisesCommentDtos":[{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"房子各方面都挺好","CreationTime":"2019-05-05T16:25:26.713","Id":9},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"哎呦，不错哦","CreationTime":"2019-05-05T15:26:08.857","Id":8}],"Id":5},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"哎呦，不错哦","CreationTime":"2019-05-05T15:26:08.857","Number":0,"PremisesCommentDtos":[],"Id":8},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"房子各方面都挺好","CreationTime":"2019-05-05T16:25:26.713","Number":0,"PremisesCommentDtos":[],"Id":9}]}
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
         * Count : 9
         * Data : [{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Score":1,"Content":"原制油气厂地块，环评结果是说土地和地下水受到污染。**的问题是石化厂就在不远啊！无论如何都会有污染的空气飘过来。会对健康产生隐患。这是**的硬伤！石化厂估计要搬也不会那么容易。哎，可惜了！","CreationTime":"2019-03-19T16:06:31.557","Number":1,"PremisesCommentDtos":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Content":"空气不好 遍地工厂 交通不便","CreationTime":"2019-03-19T17:01:35.107","Id":3}],"Id":2},{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":1,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"空气不好 遍地工厂 交通不便","CreationTime":"2019-03-19T17:01:35.107","Number":0,"PremisesCommentDtos":[],"Id":3},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":5,"Content":"还不错！","CreationTime":"2019-04-29T17:38:41.887","Number":0,"PremisesCommentDtos":[],"Id":6},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":5,"Content":"还行吧！比较满意","CreationTime":"2019-04-29T18:00:06.057","Number":0,"PremisesCommentDtos":[],"Id":7},{"UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2,"Content":"位置好，价格便宜","CreationTime":"2019-04-15T15:29:44.397","Number":0,"PremisesCommentDtos":[],"Id":4},{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Rise":0,"IsRise":false,"Fall":1,"IsFall":false,"Score":4.5,"Content":"很满意的楼盘","CreationTime":"2019-03-19T09:27:33.113","Number":0,"PremisesCommentDtos":[],"Id":1},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"一般般","CreationTime":"2019-04-28T19:29:51 2019-05-05 16:35:32.800 30634-30634/com.example.yuefang E/part1: .227","Number":2,"PremisesCommentDtos":[{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"房子各方面都挺好","CreationTime":"2019-05-05T16:25:26.713","Id":9},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"哎呦，不错哦","CreationTime":"2019-05-05T15:26:08.857","Id":8}],"Id":5},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"哎呦，不错哦","CreationTime":"2019-05-05T15:26:08.857","Number":0,"PremisesCommentDtos":[],"Id":8},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":0.5,"Content":"房子各方面都挺好","CreationTime":"2019-05-05T16:25:26.713","Number":0,"PremisesCommentDtos":[],"Id":9}]
         */

        private int Count;
        private List<Comment> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<Comment> getData() {
            return Data;
        }

        public void setData(List<Comment> Data) {
            this.Data = Data;
        }

    }
}
