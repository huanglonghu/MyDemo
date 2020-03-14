package com.xx.yuefang.bean;

import java.util.List;

public class QAList {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":3,"Data":[{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"房子怎么样啊","CreationTime":"2019-04-30T16:27:47.337","Number":3,"QuestionsAnswersDtos":[{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"寂寞是因为丝袜","CreationTime":"2019-05-05T18:42:01.997","Id":13},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"销售员很不错","CreationTime":"2019-04-30T16:58:05.773","Id":12},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"房子质量有保障","CreationTime":"2019-04-30T16:29:29","Id":11}],"Id":10},{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"楼盘很垃圾，大家不要买","CreationTime":"2019-04-15T16:59:26.987","Number":5,"QuestionsAnswersDtos":[{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"靠靠靠","CreationTime":"2019-04-28T21:21:22.453","Id":9},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"啦啦啦","CreationTime":"2019-04-28T21:16:50.2","Id":8},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"我靠","CreationTime":"2019-04-28T21:06:48.84","Id":7},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"谁说的，站出来","CreationTime":"2019-04-28T21:04:46.883","Id":6},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"谁说的，打死他","CreationTime":"2019-04-28T20:55:10.393","Id":5}],"Id":4},{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"租房孩子有学位吗？","CreationTime":"2019-04-09T11:28:45.993","Number":1,"QuestionsAnswersDtos":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Content":"您好，租房没有学位的奥，业主才有学位的","CreationTime":"2019-04-09T11:30:26.947","Id":3}],"Id":2}]}
     */

    private int Errcode;
    private String Message;
    private QABeanX Data;

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

    public QABeanX getData() {
        return Data;
    }

    public void setData(QABeanX Data) {
        this.Data = Data;
    }

    public static class QABeanX {
        /**
         * Count : 3
         * Data : [{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"房子怎么样啊","CreationTime":"2019-04-30T16:27:47.337","Number":3,"QuestionsAnswersDtos":[{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"寂寞是因为丝袜","CreationTime":"2019-05-05T18:42:01.997","Id":13},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"销售员很不错","CreationTime":"2019-04-30T16:58:05.773","Id":12},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"房子质量有保障","CreationTime":"2019-04-30T16:29:29","Id":11}],"Id":10},{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"楼盘很垃圾，大家不要买","CreationTime":"2019-04-15T16:59:26.987","Number":5,"QuestionsAnswersDtos":[{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"靠靠靠","CreationTime":"2019-04-28T21:21:22.453","Id":9},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"啦啦啦","CreationTime":"2019-04-28T21:16:50.2","Id":8},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"我靠","CreationTime":"2019-04-28T21:06:48.84","Id":7},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"谁说的，站出来","CreationTime":"2019-04-28T21:04:46.883","Id":6},{"UserName":"华为","Avatar":"Developer/8b32a62e53974bba92683162c2515c75.jpg","Content":"谁说的，打死他","CreationTime":"2019-04-28T20:55:10.393","Id":5}],"Id":4},{"PremisesName":"万科春风十里","UserName":"哦了安分","Avatar":"User/2\\d7f17abe826c41fcbd9a8bd538a3f22a.jpg","Content":"租房孩子有学位吗？","CreationTime":"2019-04-09T11:28:45.993","Number":1,"QuestionsAnswersDtos":[{"UserName":"admin","Avatar":"Operator\\admin\\65b0f9f33b884ed59e51aa9939b22fbc.jpeg","Content":"您好，租房没有学位的奥，业主才有学位的","CreationTime":"2019-04-09T11:30:26.947","Id":3}],"Id":2}]
         */

        private int Count;
        private List<QABean> Data;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<QABean> getData() {
            return Data;
        }

        public void setData(List<QABean> Data) {
            this.Data = Data;
        }


    }
}
