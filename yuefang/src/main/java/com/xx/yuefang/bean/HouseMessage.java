package com.xx.yuefang.bean;

import android.text.TextUtils;

import com.xx.yuefang.ui.widget.datepicker.DateUtils;
import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class HouseMessage {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"PremisesName":"佳兆业悦峰","AveragePrice":20000,"TotalPrice":"200-300","PropertyType":"住宅","RenovationType":"毛坯","PropertyRightYears":"70年","Region":"金湾区","Address":"珠海市三灶镇金海岸大道229号","State":"在售","SalesOfficePhone":"400 819 2651","SalesOfficeAddress":"珠海市三灶镇金海岸大道229号（接待时间 9:00 - 18:00）","SellNumber":"","HouseTypes":"三室,四室","OpeningTime":"2019-11-20T00:00:00","DeliveryTime":"预计2021-6","PExtends":[{"Key":"占地面积","Value":"21,970㎡"},{"Key":"总建筑面积","Value":"101,844㎡"},{"Key":"规划户数","Value":"700"},{"Key":"容积率","Value":"3.36"},{"Key":"绿化率","Value":"35%"},{"Key":"停车位","Value":"地上车位数40 ； 地下车位数699"},{"Key":"物业管理费","Value":"3元/m²/月"},{"Key":"物业公司","Value":"佳兆业物业集团"}],"PremisesLicences":[{"Certificate":"广00001","IssuingTime":"2019-08-15T00:00:00","BoundBuilding":"#12","Id":1}],"Id":13}
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
        /**
         * PremisesName : 佳兆业悦峰
         * AveragePrice : 20000.0
         * TotalPrice : 200-300
         * PropertyType : 住宅
         * RenovationType : 毛坯
         * PropertyRightYears : 70年
         * Region : 金湾区
         * Address : 珠海市三灶镇金海岸大道229号
         * State : 在售
         * SalesOfficePhone : 400 819 2651
         * SalesOfficeAddress : 珠海市三灶镇金海岸大道229号（接待时间 9:00 - 18:00）
         * SellNumber :
         * HouseTypes : 三室,四室
         * OpeningTime : 2019-11-20T00:00:00
         * DeliveryTime : 预计2021-6
         * PExtends : [{"Key":"占地面积","Value":"21,970㎡"},{"Key":"总建筑面积","Value":"101,844㎡"},{"Key":"规划户数","Value":"700"},{"Key":"容积率","Value":"3.36"},{"Key":"绿化率","Value":"35%"},{"Key":"停车位","Value":"地上车位数40 ； 地下车位数699"},{"Key":"物业管理费","Value":"3元/m²/月"},{"Key":"物业公司","Value":"佳兆业物业集团"}]
         * PremisesLicences : [{"Certificate":"广00001","IssuingTime":"2019-08-15T00:00:00","BoundBuilding":"#12","Id":1}]
         * Id : 13
         */

        private String PremisesName;
        private double AveragePrice;
        private String TotalPrice;
        private String PropertyType;
        private String RenovationType;
        private String PropertyRightYears;
        private String Region;
        private String Address;
        private String State;
        private String SalesOfficePhone;
        private String SalesOfficeAddress;
        private String SellNumber;
        private String HouseTypes;
        private String OpeningTime;
        private String DeliveryTime;
        private int Id;
        private List<PExtendsBean> PExtends;
        private List<PremisesLicencesBean> PremisesLicences;

        public String getPremisesName() {
            return PremisesName;
        }

        public void setPremisesName(String PremisesName) {
            this.PremisesName = PremisesName;
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

        public String getPropertyType() {
            return PropertyType;
        }

        public void setPropertyType(String PropertyType) {
            this.PropertyType = PropertyType;
        }

        public String getRenovationType() {
            return RenovationType;
        }

        public void setRenovationType(String RenovationType) {
            this.RenovationType = RenovationType;
        }

        public String getPropertyRightYears() {
            return PropertyRightYears;
        }

        public void setPropertyRightYears(String PropertyRightYears) {
            this.PropertyRightYears = PropertyRightYears;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getSalesOfficePhone() {
            return SalesOfficePhone;
        }

        public void setSalesOfficePhone(String SalesOfficePhone) {
            this.SalesOfficePhone = SalesOfficePhone;
        }

        public String getSalesOfficeAddress() {
            return SalesOfficeAddress;
        }

        public void setSalesOfficeAddress(String SalesOfficeAddress) {
            this.SalesOfficeAddress = SalesOfficeAddress;
        }

        public String getSellNumber() {
            return SellNumber;
        }

        public void setSellNumber(String SellNumber) {
            this.SellNumber = SellNumber;
        }

        public String getHouseTypes() {
            return HouseTypes;
        }

        public void setHouseTypes(String HouseTypes) {
            this.HouseTypes = HouseTypes;
        }

        public String getOpeningTime() {
            if (!TextUtils.isEmpty(OpeningTime)) {
                OpeningTime = TimeUtil.getStringToDate3(OpeningTime);
            }
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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<PExtendsBean> getPExtends() {
            return PExtends;
        }

        public void setPExtends(List<PExtendsBean> PExtends) {
            this.PExtends = PExtends;
        }

        public List<PremisesLicencesBean> getPremisesLicences() {
            return PremisesLicences;
        }

        public void setPremisesLicences(List<PremisesLicencesBean> PremisesLicences) {
            this.PremisesLicences = PremisesLicences;
        }

        public static class PExtendsBean {
            /**
             * Key : 占地面积
             * Value : 21,970㎡
             */

            private String Key;
            private String Value;

            public String getKey() {
                return Key;
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

        public static class PremisesLicencesBean {
            /**
             * Certificate : 广00001
             * IssuingTime : 2019-08-15T00:00:00
             * BoundBuilding : #12
             * Id : 1
             */

            private String Certificate;
            private String IssuingTime;
            private String BoundBuilding;
            private int Id;

            public String getCertificate() {
                return Certificate;
            }

            public void setCertificate(String Certificate) {
                this.Certificate = Certificate;
            }

            public String getIssuingTime() {
                if (!TextUtils.isEmpty(IssuingTime)) {
                    IssuingTime = TimeUtil.getStringToDate3(IssuingTime);
                }
                return IssuingTime;
            }

            public void setIssuingTime(String IssuingTime) {
                this.IssuingTime = IssuingTime;
            }

            public String getBoundBuilding() {
                return BoundBuilding;
            }

            public void setBoundBuilding(String BoundBuilding) {
                this.BoundBuilding = BoundBuilding;
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
