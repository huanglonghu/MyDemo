package com.xx.yuefang.bean;

import java.util.List;

public class HouseTypeDescribe {


    private List<DescribeBean> Describe;

    public List<DescribeBean> getDescribe() {
        return Describe;
    }

    public void setDescribe(List<DescribeBean> Describe) {
        this.Describe = Describe;
    }

    public static class DescribeBean {
        /**
         * Key : 首期3成
         * Value : 5万
         */

        private String Key;
        private String Value;

        public String getKey() {
            return Key+"：";
        }

        public void setKey(String Key) {
            this.Key = Key;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
    }
}
