package com.xx.yuefang.bean;

public class GetReservationBody {


    /**
     * ConditionType : 0
     * Condition : string
     * StartRTime : 2019-12-19T08:30:01.453Z
     * EndRTime : 2019-12-19T08:30:01.453Z
     */

    private int ConditionType;
    private String Condition;
    private String StartRTime;
    private String EndRTime;

    public int getConditionType() {
        return ConditionType;
    }

    public void setConditionType(int ConditionType) {
        this.ConditionType = ConditionType;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    public String getStartRTime() {
        return StartRTime;
    }

    public void setStartRTime(String StartRTime) {
        this.StartRTime = StartRTime;
    }

    public String getEndRTime() {
        return EndRTime;
    }

    public void setEndRTime(String EndRTime) {
        this.EndRTime = EndRTime;
    }
}
