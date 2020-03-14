package com.xx.yuefang.bean;

import java.util.List;

public class QuerryReservationBody {

    /**
     * States : [0]
     * ConditionType : 0
     * Condition : string
     * StartRTime : 2019-12-19T03:36:12.771Z
     * EndRTime : 2019-12-19T03:36:12.771Z
     * Page : 0
     * Limit : 0
     */

    private int ConditionType;
    private String Condition;
    private String StartRTime;
    private String EndRTime;
    private int Page;
    private int Limit;
    private List<Integer> States;

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

    public int getPage() {
        return Page;
    }

    public void setPage(int Page) {
        this.Page = Page;
    }

    public int getLimit() {
        return Limit;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }

    public List<Integer> getStates() {
        return States;
    }

    public void setStates(List<Integer> States) {
        this.States = States;
    }
}
