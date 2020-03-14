package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.util.TimeUtil;

import java.io.Serializable;
import java.util.List;

public class PremisesDetail {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"再生时代大厦","VideoUrl":"PremisesBase/6/ac25ca722dc2498c98430a80e3c46f8f.mp4","VRList":[{"ParentId":0,"PremisesARName":"A","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":1,"PremisesARName":"A-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":4,"PremisesARName":"A-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":6}],"Id":4},{"ParentId":1,"PremisesARName":"A-2","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":5},{"ParentId":1,"PremisesARName":"卧室","Picture":"PremisesAR/6/19cc5d205dd24769be4891449011fac8.jpeg","Lists":[],"Id":11}],"Id":1},{"ParentId":0,"PremisesARName":"B","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":3,"PremisesARName":"B-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":7,"PremisesARName":"B-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":8,"PremisesARName":"B-1-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":9},{"ParentId":8,"PremisesARName":"B-1-1-2","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":10}],"Id":8}],"Id":7}],"Id":3}],"Picture":"PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg","Rise":3,"IsRise":false,"IsCollection":false,"IsBindBroker":false,"UnitPrice":"待定","TotalPrice":"待定","State":"在售","PropertyType":"写字楼","BuildingType":"高层","Characteristics":["多轨交","大型社区","大平层","品牌开发商"],"RegionalLocation":"113.500978,22.231835","Address":"屏北二路55号  ","ConstructionArea":"6.04万","OpeningTime":"2019-05-07","DeliveryTime":"预计2020年5月1日交房","RenovationType":"精装修","HouseTypes":["三室","两室"],"HouseTypeInfos":[{"HouseType":"两室","HouseName":"01户型","Price":25000,"Area":38.75,"TotalPrice":96.87,"State":"在售","Orientation":"","Characteristic":"","Picture":"HouseType/6/728f4ca4058847fe99284934e249d036.jpeg","Describe":"\n\n","VRList":[],"Id":1},{"HouseType":"三室","HouseName":"天峯3区10 3室2厅2卫","Price":10000,"Area":102,"TotalPrice":102,"State":"在售","Orientation":"朝南","Characteristic":"大户型,双卫生间,主卧套房","Picture":"PHouseType/6/b.jpg","Describe":"127㎡户型， 3室2厅2卫1厨， 建筑面积约127.00平米","VRList":[{"PremisesARName":"卧室","Picture":"PremisesAR/6/19cc5d205dd24769be4891449011fac8.jpeg"}],"Id":2}],"PremisesComments":[{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"#未实地看过#Happy happy ","CreationTime":"2019-06-11T14:36:14.777","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"PremisesCommentDtos":[],"Id":34},{"PremisesName":"再生时代大厦","UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":2,"IsRise":false,"Fall":0,"IsFall":false,"Score":2,"Content":"#未实地看过#Wo. Mu. Ji a","CreationTime":"2019-05-23T10:57:33.863","Number":8,"GradeType":"铜牌","LeadSee":9,"Turnover":1,"PremisesCommentDtos":[{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Aa","CreationTime":"2019-06-11T14:29:54.5","GradeType":"","LeadSee":0,"Turnover":0,"Id":33},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Gg","CreationTime":"2019-06-11T14:28:01.697","GradeType":"","LeadSee":0,"Turnover":0,"Id":32},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Niu","CreationTime":"2019-06-11T14:25:08.33","GradeType":"","LeadSee":0,"Turnover":0,"Id":31},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Ll","CreationTime":"2019-06-11T14:17:10.953","GradeType":"","LeadSee":0,"Turnover":0,"Id":30},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Gaga","CreationTime":"2019-06-11T14:16:10.933","GradeType":"","LeadSee":0,"Turnover":0,"Id":29},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Cesi","CreationTime":"2019-06-11T14:14:20.047","GradeType":"","LeadSee":0,"Turnover":0,"Id":28},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Tinghaode","CreationTime":"2019-06-11T14:07:41.183","GradeType":"","LeadSee":0,"Turnover":0,"Id":27},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"我","CreationTime":"2019-05-31T09:54:01.43","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":26}],"Id":24},{"PremisesName":"再生时代大厦","UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"#实地看过#Just so so .So expensive","CreationTime":"2019-05-23T10:30:44.987","Number":0,"GradeType":"铜牌","LeadSee":9,"Turnover":1,"PremisesCommentDtos":[],"Id":23}],"QuestionsAnswers":[{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"Why this room so expensive?","CreationTime":"2019-06-11T15:06:39.127","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[],"Id":33},{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"How to get money?","CreationTime":"2019-06-11T15:02:26.09","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[],"Id":32},{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"I have no money","CreationTime":"2019-05-24T18:20:20.823","Number":14,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Gg","CreationTime":"2019-06-11T14:50:31.99","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":30},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Aefdasf","CreationTime":"2019-06-11T14:50:16.953","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":29},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Jkl","CreationTime":"2019-06-11T14:49:27.547","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":28},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Sss","CreationTime":"2019-06-11T14:49:11.11","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":27},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Jj","CreationTime":"2019-06-11T14:47:10.723","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":26},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Bucuo","CreationTime":"2019-06-11T14:47:02.277","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":25},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"haha","CreationTime":"2019-06-11T14:45:51.81","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":24},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Keel","CreationTime":"2019-06-11T14:45:26.943","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":23},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"112345","CreationTime":"2019-06-11T14:43:40.767","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":22},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Qwe","CreationTime":"2019-06-11T14:42:51.36","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":21}],"Id":15}],"PremisesActivity":{"PremisesBaseId":6,"PremisesName":"再生时代大厦","ActivityName":"大湾区未来·地铁18号线通达珠江新城","IsActive":true,"Picture":"PremisesActivity/6/82231135246947e3b1b7ea7708d91b22.jpeg","Number":1,"CreationTime":"2019-03-20T11:04:24.22","Id":1},"PremisesNewsInfos":[{"PremisesBaseId":6,"Title":"亚运城推售5栋和9栋产品","Picture":"PNewsInfo\\6\\745640cd1d5247d9941006af484cd9c0.jpeg","CreationTime":"2019-03-13T10:42:30.88","RichTextContent":"<p><span>2019年3月11日讯：亚运城在售天峯组团推售5栋和9栋产品，共50层，为3梯6户设计，户型涵盖建面89㎡两房两厅一卫、建面103㎡两房两厅两卫、建面127㎡三房两厅两卫及建面135㎡四房两厅两卫户型，带装修价格约25000-28000元/㎡，预计2021年6月交房。<\/span><\/p>","Id":1}],"PremisesLists":[{"Score":0,"CommentCount":0,"PremisesName":"星汉·港湾国际","Picture":"PremisesBase/9/7535e0b473234d969781312caba4e397.jpeg","Region":"香洲区","Address":"富林西路与联峰路交叉路口北110米  ","ConstructionArea":"暂无","State":"在售","PropertyType":"写字楼","AveragePrice":23800,"TotalPrice":"暂无","Characteristics":["品牌开发商"],"Id":9},{"Score":0,"CommentCount":0,"PremisesName":"斯越云谷","Picture":"PremisesBase/8/a3f841e89c0348ef89a865aaf6388c06.jpeg","Region":"香洲区","Address":"环岛东路与长隆大道交汇处","ConstructionArea":"0-120","State":"在售","PropertyType":"住宅","AveragePrice":10000,"TotalPrice":"0-120","Characteristics":["轨交房","装修交付","大平层"],"Id":8},{"Score":0,"CommentCount":0,"PremisesName":"星汉·港湾国际","Picture":"PremisesBase/9/7535e0b473234d969781312caba4e397.jpeg","Region":"香洲区","Address":"富林西路与联峰路交叉路口北110米  ","ConstructionArea":"暂无","State":"在售","PropertyType":"写字楼","AveragePrice":23800,"TotalPrice":"暂无","Characteristics":["品牌开发商"],"Id":9},{"Score":0,"CommentCount":0,"PremisesName":"斯越云谷","Picture":"PremisesBase/8/a3f841e89c0348ef89a865aaf6388c06.jpeg","Region":"香洲区","Address":"环岛东路与长隆大道交汇处","ConstructionArea":"0-120","State":"在售","PropertyType":"住宅","AveragePrice":10000,"TotalPrice":"0-120","Characteristics":["轨交房","装修交付","大平层"],"Id":8},{"Score":0,"CommentCount":0,"PremisesName":"江畔雅苑","Picture":"PremisesBase/1/3.png","Region":"天河","Address":"岐山路183号","ConstructionArea":"暂无","State":"在售","PropertyType":"别墅","AveragePrice":0,"TotalPrice":"暂无","Characteristics":[],"Id":3}],"Id":6}
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
         * PremisesName : 再生时代大厦
         * VideoUrl : PremisesBase/6/ac25ca722dc2498c98430a80e3c46f8f.mp4
         * VRList : [{"ParentId":0,"PremisesARName":"A","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":1,"PremisesARName":"A-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":4,"PremisesARName":"A-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":6}],"Id":4},{"ParentId":1,"PremisesARName":"A-2","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":5},{"ParentId":1,"PremisesARName":"卧室","Picture":"PremisesAR/6/19cc5d205dd24769be4891449011fac8.jpeg","Lists":[],"Id":11}],"Id":1},{"ParentId":0,"PremisesARName":"B","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":3,"PremisesARName":"B-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":7,"PremisesARName":"B-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":8,"PremisesARName":"B-1-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":9},{"ParentId":8,"PremisesARName":"B-1-1-2","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":10}],"Id":8}],"Id":7}],"Id":3}]
         * Picture : PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg
         * Rise : 3
         * IsRise : false
         * IsCollection : false
         * IsBindBroker : false
         * UnitPrice : 待定
         * TotalPrice : 待定
         * State : 在售
         * PropertyType : 写字楼
         * BuildingType : 高层
         * Characteristics : ["多轨交","大型社区","大平层","品牌开发商"]
         * RegionalLocation : 113.500978,22.231835
         * Address : 屏北二路55号
         * ConstructionArea : 6.04万
         * OpeningTime : 2019-05-07
         * DeliveryTime : 预计2020年5月1日交房
         * RenovationType : 精装修
         * HouseTypes : ["三室","两室"]
         * HouseTypeInfos : [{"HouseType":"两室","HouseName":"01户型","Price":25000,"Area":38.75,"TotalPrice":96.87,"State":"在售","Orientation":"","Characteristic":"","Picture":"HouseType/6/728f4ca4058847fe99284934e249d036.jpeg","Describe":"\n\n","VRList":[],"Id":1},{"HouseType":"三室","HouseName":"天峯3区10 3室2厅2卫","Price":10000,"Area":102,"TotalPrice":102,"State":"在售","Orientation":"朝南","Characteristic":"大户型,双卫生间,主卧套房","Picture":"PHouseType/6/b.jpg","Describe":"127㎡户型， 3室2厅2卫1厨， 建筑面积约127.00平米","VRList":[{"PremisesARName":"卧室","Picture":"PremisesAR/6/19cc5d205dd24769be4891449011fac8.jpeg"}],"Id":2}]
         * PremisesComments : [{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"#未实地看过#Happy happy ","CreationTime":"2019-06-11T14:36:14.777","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"PremisesCommentDtos":[],"Id":34},{"PremisesName":"再生时代大厦","UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":2,"IsRise":false,"Fall":0,"IsFall":false,"Score":2,"Content":"#未实地看过#Wo. Mu. Ji a","CreationTime":"2019-05-23T10:57:33.863","Number":8,"GradeType":"铜牌","LeadSee":9,"Turnover":1,"PremisesCommentDtos":[{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Aa","CreationTime":"2019-06-11T14:29:54.5","GradeType":"","LeadSee":0,"Turnover":0,"Id":33},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Gg","CreationTime":"2019-06-11T14:28:01.697","GradeType":"","LeadSee":0,"Turnover":0,"Id":32},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Niu","CreationTime":"2019-06-11T14:25:08.33","GradeType":"","LeadSee":0,"Turnover":0,"Id":31},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Ll","CreationTime":"2019-06-11T14:17:10.953","GradeType":"","LeadSee":0,"Turnover":0,"Id":30},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Gaga","CreationTime":"2019-06-11T14:16:10.933","GradeType":"","LeadSee":0,"Turnover":0,"Id":29},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Cesi","CreationTime":"2019-06-11T14:14:20.047","GradeType":"","LeadSee":0,"Turnover":0,"Id":28},{"UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"Tinghaode","CreationTime":"2019-06-11T14:07:41.183","GradeType":"","LeadSee":0,"Turnover":0,"Id":27},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Content":"我","CreationTime":"2019-05-31T09:54:01.43","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":26}],"Id":24},{"PremisesName":"再生时代大厦","UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Rise":0,"IsRise":false,"Fall":0,"IsFall":false,"Score":2.5,"Content":"#实地看过#Just so so .So expensive","CreationTime":"2019-05-23T10:30:44.987","Number":0,"GradeType":"铜牌","LeadSee":9,"Turnover":1,"PremisesCommentDtos":[],"Id":23}]
         * QuestionsAnswers : [{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"Why this room so expensive?","CreationTime":"2019-06-11T15:06:39.127","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[],"Id":33},{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"How to get money?","CreationTime":"2019-06-11T15:02:26.09","Number":0,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[],"Id":32},{"PremisesName":"再生时代大厦","UserName":"1858888你好","Avatar":"User/7\\a58fc6c404654094bd987e6d4b44ba69.jpg","Content":"I have no money","CreationTime":"2019-05-24T18:20:20.823","Number":14,"GradeType":"","LeadSee":0,"Turnover":0,"QuestionsAnswersDtos":[{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Gg","CreationTime":"2019-06-11T14:50:31.99","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":30},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Aefdasf","CreationTime":"2019-06-11T14:50:16.953","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":29},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Jkl","CreationTime":"2019-06-11T14:49:27.547","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":28},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Sss","CreationTime":"2019-06-11T14:49:11.11","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":27},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Jj","CreationTime":"2019-06-11T14:47:10.723","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":26},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Bucuo","CreationTime":"2019-06-11T14:47:02.277","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":25},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"haha","CreationTime":"2019-06-11T14:45:51.81","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":24},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Keel","CreationTime":"2019-06-11T14:45:26.943","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":23},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"112345","CreationTime":"2019-06-11T14:43:40.767","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":22},{"UserName":"销售经理2","Avatar":"Salesperson/712dd1121c7b43c9a40d031288a4855a.jpg","Content":"Qwe","CreationTime":"2019-06-11T14:42:51.36","GradeType":"铜牌","LeadSee":9,"Turnover":1,"Id":21}],"Id":15}]
         * PremisesActivity : {"PremisesBaseId":6,"PremisesName":"再生时代大厦","ActivityName":"大湾区未来·地铁18号线通达珠江新城","IsActive":true,"Picture":"PremisesActivity/6/82231135246947e3b1b7ea7708d91b22.jpeg","Number":1,"CreationTime":"2019-03-20T11:04:24.22","Id":1}
         * PremisesNewsInfos : [{"PremisesBaseId":6,"Title":"亚运城推售5栋和9栋产品","Picture":"PNewsInfo\\6\\745640cd1d5247d9941006af484cd9c0.jpeg","CreationTime":"2019-03-13T10:42:30.88","RichTextContent":"<p><span>2019年3月11日讯：亚运城在售天峯组团推售5栋和9栋产品，共50层，为3梯6户设计，户型涵盖建面89㎡两房两厅一卫、建面103㎡两房两厅两卫、建面127㎡三房两厅两卫及建面135㎡四房两厅两卫户型，带装修价格约25000-28000元/㎡，预计2021年6月交房。<\/span><\/p>","Id":1}]
         * PremisesLists : [{"Score":0,"CommentCount":0,"PremisesName":"星汉·港湾国际","Picture":"PremisesBase/9/7535e0b473234d969781312caba4e397.jpeg","Region":"香洲区","Address":"富林西路与联峰路交叉路口北110米  ","ConstructionArea":"暂无","State":"在售","PropertyType":"写字楼","AveragePrice":23800,"TotalPrice":"暂无","Characteristics":["品牌开发商"],"Id":9},{"Score":0,"CommentCount":0,"PremisesName":"斯越云谷","Picture":"PremisesBase/8/a3f841e89c0348ef89a865aaf6388c06.jpeg","Region":"香洲区","Address":"环岛东路与长隆大道交汇处","ConstructionArea":"0-120","State":"在售","PropertyType":"住宅","AveragePrice":10000,"TotalPrice":"0-120","Characteristics":["轨交房","装修交付","大平层"],"Id":8},{"Score":0,"CommentCount":0,"PremisesName":"星汉·港湾国际","Picture":"PremisesBase/9/7535e0b473234d969781312caba4e397.jpeg","Region":"香洲区","Address":"富林西路与联峰路交叉路口北110米  ","ConstructionArea":"暂无","State":"在售","PropertyType":"写字楼","AveragePrice":23800,"TotalPrice":"暂无","Characteristics":["品牌开发商"],"Id":9},{"Score":0,"CommentCount":0,"PremisesName":"斯越云谷","Picture":"PremisesBase/8/a3f841e89c0348ef89a865aaf6388c06.jpeg","Region":"香洲区","Address":"环岛东路与长隆大道交汇处","ConstructionArea":"0-120","State":"在售","PropertyType":"住宅","AveragePrice":10000,"TotalPrice":"0-120","Characteristics":["轨交房","装修交付","大平层"],"Id":8},{"Score":0,"CommentCount":0,"PremisesName":"江畔雅苑","Picture":"PremisesBase/1/3.png","Region":"天河","Address":"岐山路183号","ConstructionArea":"暂无","State":"在售","PropertyType":"别墅","AveragePrice":0,"TotalPrice":"暂无","Characteristics":[],"Id":3}]
         * Id : 6
         */

        private String PremisesName;
        private String VideoUrl;
        private String Picture;
        private String VRUrl;
        private int Rise;
        private boolean IsRise;
        private String ListPicture;
        private boolean IsCollection;
        private String UnitPrice;
        private String TotalPrice;
        private String State;
        private String PropertyType;
        private String BuildingType;
        private String RegionalLocation;
        private String Address;
        private String ConstructionArea;
        private String OpeningTime;
        private String DeliveryTime;
        private String RenovationType;
        private PremisesActivityBean PremisesActivity;
        private int Id;
        private List<VRListBean> VRList;
        private List<String> Characteristics;
        private List<String> HouseTypes;
        private List<HouseTypeInfosBean> HouseTypeInfos;
        private List<Comment> PremisesComments;
        private List<QABean> QuestionsAnswers;
        private List<PremisesNewsInfosBean> PremisesNewsInfos;
        private List<PremisesBean> PremisesLists;
        private int Total;
        private int Index;
        private int DeveloperId;
        private String PosterPicture;
        private String VRPicture;
        private int CommentNum;
        private int QuestionsAnswerNum;


        public int getCommentNum() {
            return CommentNum;
        }

        public void setCommentNum(int commentNum) {
            CommentNum = commentNum;
        }

        public int getQuestionsAnswerNum() {
            return QuestionsAnswerNum;
        }

        public void setQuestionsAnswerNum(int questionsAnswerNum) {
            QuestionsAnswerNum = questionsAnswerNum;
        }

        public String getVRUrl() {
            return VRUrl;
        }

        public void setVRUrl(String VRUrl) {
            this.VRUrl = VRUrl;
        }

        public String getListPicture() {
            return ListPicture;
        }

        public void setListPicture(String listPicture) {
            ListPicture = listPicture;
        }


        public String getVRPicture() {
            return VRPicture;
        }

        public void setVRPicture(String VRPicture) {
            this.VRPicture = VRPicture;
        }

        public String getPosterPicture() {
            return PosterPicture;
        }

        public void setPosterPicture(String posterPicture) {
            PosterPicture = posterPicture;
        }

        public int getDeveloperId() {
            return DeveloperId;
        }

        public void setDeveloperId(int developerId) {
            DeveloperId = developerId;
        }


        public int getTotal() {
            return Total;
        }

        public void setTotal(int total) {
            Total = total;
        }

        public int getIndex() {
            return Index;
        }

        public void setIndex(int index) {
            Index = index;
        }

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
        }

        public String getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(String VideoUrl) {
            this.VideoUrl = VideoUrl;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
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

        public boolean isIsCollection() {
            return IsCollection;
        }

        public void setIsCollection(boolean IsCollection) {
            this.IsCollection = IsCollection;
        }

        public String getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(String UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getPropertyType() {
            return PropertyType;
        }

        public void setPropertyType(String PropertyType) {
            this.PropertyType = PropertyType;
        }

        public String getBuildingType() {
            return BuildingType;
        }

        public void setBuildingType(String BuildingType) {
            this.BuildingType = BuildingType;
        }

        public String getRegionalLocation() {
            return RegionalLocation;
        }

        public void setRegionalLocation(String RegionalLocation) {
            this.RegionalLocation = RegionalLocation;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getConstructionArea() {
            return ConstructionArea;
        }

        public void setConstructionArea(String ConstructionArea) {
            this.ConstructionArea = ConstructionArea;
        }

        public String getOpeningTime() {
            return OpeningTime;
        }

        public void setOpeningTime(String OpeningTime) {
            this.OpeningTime = OpeningTime;
        }

        public String getDeliveryTime() {
            return DeliveryTime;
        }

        public void setDeliveryTime(String DeliveryTime) {
            this.DeliveryTime = DeliveryTime;
        }

        public String getRenovationType() {
            return RenovationType;
        }

        public void setRenovationType(String RenovationType) {
            this.RenovationType = RenovationType;
        }

        public PremisesActivityBean getPremisesActivity() {
            return PremisesActivity;
        }

        public void setPremisesActivity(PremisesActivityBean PremisesActivity) {
            this.PremisesActivity = PremisesActivity;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<VRListBean> getVRList() {
            return VRList;
        }

        public void setVRList(List<VRListBean> VRList) {
            this.VRList = VRList;
        }

        public List<String> getCharacteristics() {
            return Characteristics;
        }

        public void setCharacteristics(List<String> Characteristics) {
            this.Characteristics = Characteristics;
        }

        public List<String> getHouseTypes() {
            return HouseTypes;
        }

        public void setHouseTypes(List<String> HouseTypes) {
            this.HouseTypes = HouseTypes;
        }

        public List<HouseTypeInfosBean> getHouseTypeInfos() {
            return HouseTypeInfos;
        }

        public void setHouseTypeInfos(List<HouseTypeInfosBean> HouseTypeInfos) {
            this.HouseTypeInfos = HouseTypeInfos;
        }

        public List<Comment> getPremisesComments() {
            return PremisesComments;
        }

        public void setPremisesComments(List<Comment> PremisesComments) {
            this.PremisesComments = PremisesComments;
        }

        public List<QABean> getQuestionsAnswers() {
            return QuestionsAnswers;
        }

        public void setQuestionsAnswers(List<QABean> QuestionsAnswers) {
            this.QuestionsAnswers = QuestionsAnswers;
        }

        public List<PremisesNewsInfosBean> getPremisesNewsInfos() {
            return PremisesNewsInfos;
        }

        public void setPremisesNewsInfos(List<PremisesNewsInfosBean> PremisesNewsInfos) {
            this.PremisesNewsInfos = PremisesNewsInfos;
        }

        public List<PremisesBean> getPremisesLists() {
            return PremisesLists;
        }

        public void setPremisesLists(List<PremisesBean> PremisesLists) {
            this.PremisesLists = PremisesLists;
        }

        public static class PremisesActivityBean {
            /**
             * PremisesBaseId : 6
             * PremisesName : 再生时代大厦
             * ActivityName : 大湾区未来·地铁18号线通达珠江新城
             * IsActive : true
             * Picture : PremisesActivity/6/82231135246947e3b1b7ea7708d91b22.jpeg
             * Number : 1
             * CreationTime : 2019-03-20T11:04:24.22
             * Id : 1
             */

            private int PremisesBaseId;
            private String PremisesName;
            private String ActivityName;
            private boolean IsActive;
            private String Picture;
            private int Number;
            private String CreationTime;
            private int Id;

            public int getPremisesBaseId() {
                return PremisesBaseId;
            }

            public void setPremisesBaseId(int PremisesBaseId) {
                this.PremisesBaseId = PremisesBaseId;
            }

            public String getPremisesName() {
                return PremisesName;
            }

            public void setPremisesName(String PremisesName) {
                this.PremisesName = PremisesName;
            }

            public String getActivityName() {
                return ActivityName;
            }

            public void setActivityName(String ActivityName) {
                this.ActivityName = ActivityName;
            }

            public boolean isIsActive() {
                return IsActive;
            }

            public void setIsActive(boolean IsActive) {
                this.IsActive = IsActive;
            }

            public String getPicture() {
                return Picture;
            }

            public void setPicture(String Picture) {
                this.Picture = Picture;
            }

            public int getNumber() {
                return Number;
            }

            public void setNumber(int Number) {
                this.Number = Number;
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

        public static class VRListBean {
            /**
             * ParentId : 0
             * PremisesARName : A
             * Picture : PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg
             * Lists : [{"ParentId":1,"PremisesARName":"A-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[{"ParentId":4,"PremisesARName":"A-1-1","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":6}],"Id":4},{"ParentId":1,"PremisesARName":"A-2","Picture":"PremisesAR/6/b658c31e50f845f4abe15cb1fb2f2f44.jpeg","Lists":[],"Id":5},{"ParentId":1,"PremisesARName":"卧室","Picture":"PremisesAR/6/19cc5d205dd24769be4891449011fac8.jpeg","Lists":[],"Id":11}]
             * Id : 1
             */

            private int ParentId;
            private String PremisesARName;
            private String Picture;
            private int Id;
            private boolean selected;
            private List<VRListBean> Lists;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public String getPremisesARName() {
                return PremisesARName;
            }

            public void setPremisesARName(String PremisesARName) {
                this.PremisesARName = PremisesARName;
            }

            public String getPicture() {
                return Picture;
            }

            public void setPicture(String Picture) {
                this.Picture = Picture;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public List<VRListBean> getLists() {
                return Lists;
            }

            public void setLists(List<VRListBean> Lists) {
                this.Lists = Lists;
            }


        }


        public static class HouseTypeInfosBean implements Serializable {


            /**
             * HouseType : 三室
             * HouseName : 3室2厅2卫，A1户型
             * Price : 20000.0
             * Area : 105.0
             * TotalPrice : 210.0
             * State : 在售
             * Orientation : 南北通透
             * Characteristic : 大四房,高楼层,光线充裕
             * Picture : PHouseType/10/f2ca997f999b43519a148745fe155657.jpeg
             * Describe : [{"Key":"首期3成","Value":"5万"},{"Key":"首付1成","Value":"10万"},{"Key":"贷款7成总额","Value":"100万"},{"Key":"利率","Value":"5.8"},{"Key":"5年月供","Value":"10000"},{"Key":"10年月供","Value":"9000"},{"Key":"15年月供","Value":"8000"},{"Key":"20年月供","Value":"7000"},{"Key":"25年月供","Value":"6000"},{"Key":"30年月供","Value":"5000"},{"Key":"契税","Value":"12000"},{"Key":"物業维修基金","Value":"5000"},{"Key":"律师费","Value":"1500"},{"Key":"其他手续费","Value":"0"}]
             * VRUrl : https://720yun.com/t/ea6jvdmmtw3?scene_id=18408151
             * Id : 5
             */

            private String HouseType;
            private String HouseName;
            private double Price;
            private double Area;
            private double TotalPrice;
            private String State;
            private String Orientation;
            private String Characteristic;
            private String Picture;
            private String Describe;
            private String VRUrl;
            private int Id;

            public String getHouseType() {
                return HouseType;
            }

            public void setHouseType(String HouseType) {
                this.HouseType = HouseType;
            }

            public String getHouseName() {
                return HouseName;
            }

            public void setHouseName(String HouseName) {
                this.HouseName = HouseName;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public double getArea() {
                return Area;
            }

            public void setArea(double Area) {
                this.Area = Area;
            }

            public double getTotalPrice() {
                return TotalPrice;
            }

            public void setTotalPrice(double TotalPrice) {
                this.TotalPrice = TotalPrice;
            }

            public String getState() {
                return State;
            }

            public void setState(String State) {
                this.State = State;
            }

            public String getOrientation() {
                return Orientation;
            }

            public void setOrientation(String Orientation) {
                this.Orientation = Orientation;
            }

            public String getCharacteristic() {
                return Characteristic;
            }

            public void setCharacteristic(String Characteristic) {
                this.Characteristic = Characteristic;
            }

            public String getPicture() {
                return Picture;
            }

            public void setPicture(String Picture) {
                this.Picture = Picture;
            }

            public String getDescribe() {
                return Describe;
            }

            public void setDescribe(String Describe) {
                this.Describe = Describe;
            }

            public String getVRUrl() {
                return VRUrl;
            }

            public void setVRUrl(String VRUrl) {
                this.VRUrl = VRUrl;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }
        }


        public static class PremisesNewsInfosBean {
            /**
             * PremisesBaseId : 6
             * Title : 亚运城推售5栋和9栋产品
             * Picture : PNewsInfo\6\745640cd1d5247d9941006af484cd9c0.jpeg
             * CreationTime : 2019-03-13T10:42:30.88
             * RichTextContent : <p><span>2019年3月11日讯：亚运城在售天峯组团推售5栋和9栋产品，共50层，为3梯6户设计，户型涵盖建面89㎡两房两厅一卫、建面103㎡两房两厅两卫、建面127㎡三房两厅两卫及建面135㎡四房两厅两卫户型，带装修价格约25000-28000元/㎡，预计2021年6月交房。</span></p>
             * Id : 1
             */

            private int PremisesBaseId;
            private String Title;
            private String Picture;
            private String CreationTime;
            private String RichTextContent;
            private int Id;

            public int getPremisesBaseId() {
                return PremisesBaseId;
            }

            public void setPremisesBaseId(int PremisesBaseId) {
                this.PremisesBaseId = PremisesBaseId;
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

            public String getCreationTime() {
                if (!TextUtils.isEmpty(CreationTime)) {
                    CreationTime = TimeUtil.getStringToDate3(CreationTime);
                }
                return CreationTime;
            }

            public void setCreationTime(String CreationTime) {
                this.CreationTime = CreationTime;
            }

            public String getRichTextContent() {
                return RichTextContent;
            }

            public void setRichTextContent(String RichTextContent) {
                this.RichTextContent = RichTextContent;
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
