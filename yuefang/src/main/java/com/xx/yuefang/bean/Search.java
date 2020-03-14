package com.xx.yuefang.bean;

import java.util.List;

public class Search {


    /**
     * Errcode : 0
     * Message : success
     * Data : {"BuildingTypes":[{"Text":"多层","Value":1},{"Text":"小高层","Value":2},{"Text":"高层","Value":3},{"Text":"超高层","Value":4},{"Text":"花园洋房","Value":5},{"Text":"独栋别墅","Value":6},{"Text":"双拼别墅","Value":7},{"Text":"联排别墅","Value":8},{"Text":"三拼别墅","Value":9},{"Text":"叠加别墅","Value":10},{"Text":"四合院","Value":11},{"Text":"板楼","Value":12},{"Text":"塔楼","Value":13},{"Text":"楼塔组合","Value":14}],"CharacteristicTypes":[{"Text":"品牌开发商","Value":1},{"Text":"今年交房","Value":2},{"Text":"轨交房","Value":3},{"Text":"多轨交","Value":4},{"Text":"装修交付","Value":5},{"Text":"大型社区","Value":6},{"Text":"大平层","Value":7},{"Text":"大阳台","Value":8},{"Text":"南北通透","Value":9},{"Text":"车位充足","Value":10},{"Text":"低容积","Value":11},{"Text":"高绿化率","Value":12},{"Text":"山景地产","Value":13},{"Text":"旅游地产","Value":14},{"Text":"沿街商铺","Value":15},{"Text":"学校","Value":16},{"Text":"银行","Value":17},{"Text":"便利店","Value":18}],"HouseTypes":[{"Text":"一室","Value":1},{"Text":"两室","Value":2},{"Text":"三室","Value":3},{"Text":"四室","Value":4},{"Text":"五室","Value":5},{"Text":"五室以上","Value":6}],"PremisesStates":[{"Text":"在售","Value":1},{"Text":"待售","Value":2},{"Text":"售完","Value":3}],"PropertyTypes":[{"Text":"住宅","Value":1},{"Text":"别墅","Value":2},{"Text":"商铺","Value":3},{"Text":"写字楼","Value":4}],"RenovationTypes":[{"Text":"毛坯","Value":1},{"Text":"简装","Value":2},{"Text":"精装修","Value":3}]}
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
        private List<SearchBean> BuildingTypes;
        private List<SearchBean> CharacteristicTypes;
        private List<SearchBean> HouseTypes;
        private List<SearchBean> PremisesStates;
        private List<SearchBean> RenovationTypes;
        private List<SearchBean> PropertyTypes;

        public List<SearchBean> getBuildingTypes() {
            return BuildingTypes;
        }

        public void setBuildingTypes(List<SearchBean> BuildingTypes) {
            this.BuildingTypes = BuildingTypes;
        }

        public List<SearchBean> getCharacteristicTypes() {
            return CharacteristicTypes;
        }

        public void setCharacteristicTypes(List<SearchBean> CharacteristicTypes) {
            this.CharacteristicTypes = CharacteristicTypes;
        }

        public List<SearchBean> getHouseTypes() {
            return HouseTypes;
        }

        public void setHouseTypes(List<SearchBean> HouseTypes) {
            this.HouseTypes = HouseTypes;
        }

        public List<SearchBean> getPremisesStates() {
            return PremisesStates;
        }

        public void setPremisesStates(List<SearchBean> PremisesStates) {
            this.PremisesStates = PremisesStates;
        }

        public List<SearchBean> getPropertyTypes() {
            return PropertyTypes;
        }

        public void setPropertyTypes(List<SearchBean> PropertyTypes) {
            this.PropertyTypes = PropertyTypes;
        }

        public List<SearchBean> getRenovationTypes() {
            return RenovationTypes;
        }

        public void setRenovationTypes(List<SearchBean> RenovationTypes) {
            this.RenovationTypes = RenovationTypes;
        }




    }
}
