package com.xx.yuefang.bean;

import java.io.Serializable;
import java.util.List;

public class PremisesAr {

    /**
     * Errcode : 0
     * Message : success
     * Data : {"Count":3,"Data":[{"PremisesName":"再生时代大厦","DeveloperId":5,"CompanyName":"腾讯","DeveloperAvatar":"Developer/4fbbabe672ec4d558ca5e9f474a7c880.jpg","Region":"香洲区","State":"在售","PropertyType":"写字楼","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":3,"IsRise":false,"IsCollection":false,"AveragePrice":0,"TotalPrice":"待定","VideoUrl":"PremisesBase/6/ac25ca722dc2498c98430a80e3c46f8f.mp4","Address":"屏北二路55号  ","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":"PremisesBase/1/1.png,PremisesBase/1/2.png,PremisesBase/1/3.png,PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg","Sort":1,"Id":1}],"Id":6},{"PremisesName":"斯越云谷","DeveloperId":13,"CompanyName":"jingfeng","DeveloperAvatar":"Developer/a525dbb08bf24faaabbfdecc66034374.jpg","Region":"香洲区","State":"在售","PropertyType":"住宅","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":0,"IsRise":false,"IsCollection":false,"AveragePrice":10000,"TotalPrice":"0-120","VideoUrl":null,"Address":"环岛东路与长隆大道交汇处","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":null,"Sort":10,"Id":1}],"Id":8},{"PremisesName":"星汉·港湾国际","DeveloperId":13,"CompanyName":"jingfeng","DeveloperAvatar":"Developer/a525dbb08bf24faaabbfdecc66034374.jpg","Region":"香洲区","State":"在售","PropertyType":"写字楼","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":1,"IsRise":false,"IsCollection":false,"AveragePrice":23800,"TotalPrice":"暂无","VideoUrl":"PremisesBase/9/d72c36c073c944678e197dd86094d9f3.mp4","Address":"富林西路与联峰路交叉路口北110米  ","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":"PremisesBase/9/f4ccfa0bf00542f7bb5c1989da2cd0f4.jpeg","Sort":0,"Id":1}],"Id":9}]}
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
         * Count : 3
         * Data : [{"PremisesName":"再生时代大厦","DeveloperId":5,"CompanyName":"腾讯","DeveloperAvatar":"Developer/4fbbabe672ec4d558ca5e9f474a7c880.jpg","Region":"香洲区","State":"在售","PropertyType":"写字楼","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":3,"IsRise":false,"IsCollection":false,"AveragePrice":0,"TotalPrice":"待定","VideoUrl":"PremisesBase/6/ac25ca722dc2498c98430a80e3c46f8f.mp4","Address":"屏北二路55号  ","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":"PremisesBase/1/1.png,PremisesBase/1/2.png,PremisesBase/1/3.png,PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg","Sort":1,"Id":1}],"Id":6},{"PremisesName":"斯越云谷","DeveloperId":13,"CompanyName":"jingfeng","DeveloperAvatar":"Developer/a525dbb08bf24faaabbfdecc66034374.jpg","Region":"香洲区","State":"在售","PropertyType":"住宅","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":0,"IsRise":false,"IsCollection":false,"AveragePrice":10000,"TotalPrice":"0-120","VideoUrl":null,"Address":"环岛东路与长隆大道交汇处","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":null,"Sort":10,"Id":1}],"Id":8},{"PremisesName":"星汉·港湾国际","DeveloperId":13,"CompanyName":"jingfeng","DeveloperAvatar":"Developer/a525dbb08bf24faaabbfdecc66034374.jpg","Region":"香洲区","State":"在售","PropertyType":"写字楼","Characteristics":["品牌开发商","多轨交","大型社区","大平层"],"Rise":1,"IsRise":false,"IsCollection":false,"AveragePrice":23800,"TotalPrice":"暂无","VideoUrl":"PremisesBase/9/d72c36c073c944678e197dd86094d9f3.mp4","Address":"富林西路与联峰路交叉路口北110米  ","PremisesARs":[{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":"PremisesBase/9/f4ccfa0bf00542f7bb5c1989da2cd0f4.jpeg","Sort":0,"Id":1}],"Id":9}]
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
             * PremisesName : 再生时代大厦
             * DeveloperId : 5
             * CompanyName : 腾讯
             * DeveloperAvatar : Developer/4fbbabe672ec4d558ca5e9f474a7c880.jpg
             * Region : 香洲区
             * State : 在售
             * PropertyType : 写字楼
             * Characteristics : ["品牌开发商","多轨交","大型社区","大平层"]
             * Rise : 3
             * IsRise : false
             * IsCollection : false
             * AveragePrice : 0.0
             * TotalPrice : 待定
             * VideoUrl : PremisesBase/6/ac25ca722dc2498c98430a80e3c46f8f.mp4
             * Address : 屏北二路55号
             * PremisesARs : [{"PremisesBaseId":6,"PremisesName":"再生时代大厦","PremisesARName":"客厅","Picture":"PremisesBase/1/1.png,PremisesBase/1/2.png,PremisesBase/1/3.png,PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg","Sort":1,"Id":1}]
             * Id : 6
             */

            private String PremisesName;
            private int DeveloperId;
            private String CompanyName;
            private String DeveloperAvatar;
            private String Region;
            private String State;
            private String PropertyType;
            private int Rise;
            private boolean IsRise;
            private boolean IsCollection;
            private double AveragePrice;
            private String TotalPrice;
            private String VideoUrl;
            private String Address;
            private int Id;
            private List<String> Characteristics;
            private List<PremisesARsBean> PremisesARs;

            public String getPremisesName() {
                return PremisesName;
            }

            public void setPremisesName(String PremisesName) {
                this.PremisesName = PremisesName;
            }

            public int getDeveloperId() {
                return DeveloperId;
            }

            public void setDeveloperId(int DeveloperId) {
                this.DeveloperId = DeveloperId;
            }

            public String getCompanyName() {
                return CompanyName;
            }

            public void setCompanyName(String CompanyName) {
                this.CompanyName = CompanyName;
            }

            public String getDeveloperAvatar() {
                return DeveloperAvatar;
            }

            public void setDeveloperAvatar(String DeveloperAvatar) {
                this.DeveloperAvatar = DeveloperAvatar;
            }

            public String getRegion() {
                return Region;
            }

            public void setRegion(String Region) {
                this.Region = Region;
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

            public double getAveragePrice() {
                return AveragePrice;
            }

            public void setAveragePrice(double AveragePrice) {
                this.AveragePrice = AveragePrice;
            }

            public String getTotalPrice() {
                return TotalPrice;
            }

            public void setTotalPrice(String TotalPrice) {
                this.TotalPrice = TotalPrice;
            }

            public String getVideoUrl() {
                return VideoUrl;
            }

            public void setVideoUrl(String VideoUrl) {
                this.VideoUrl = VideoUrl;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public List<String> getCharacteristics() {
                return Characteristics;
            }

            public void setCharacteristics(List<String> Characteristics) {
                this.Characteristics = Characteristics;
            }

            public List<PremisesARsBean> getPremisesARs() {
                return PremisesARs;
            }

            public void setPremisesARs(List<PremisesARsBean> PremisesARs) {
                this.PremisesARs = PremisesARs;
            }

            public static class PremisesARsBean {
                /**
                 * PremisesBaseId : 6
                 * PremisesName : 再生时代大厦
                 * PremisesARName : 客厅
                 * Picture : PremisesBase/1/1.png,PremisesBase/1/2.png,PremisesBase/1/3.png,PremisesBase/6/64486b5f1818476cba143252b20a42f7.jpeg,PremisesBase/6/fe733abf042f4e1780446382d0a1a53e.jpeg
                 * Sort : 1
                 * Id : 1
                 */

                private int PremisesBaseId;
                private String PremisesName;
                private String PremisesARName;
                private String Picture;
                private int Sort;
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

                public int getSort() {
                    return Sort;
                }

                public void setSort(int Sort) {
                    this.Sort = Sort;
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
}
