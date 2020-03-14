package com.xx.yuefang.bean;

public class GetSellerList {

    /**
     * ConditionType : 0
     * Condition : string
     * DeveloperId : 0
     * CompanyName : string
     * Page : 0
     * Limit : 0
     */

    private int ConditionType;
    private String Condition;
    private int DeveloperId;
    private String CompanyName;
    private int Page;
    private int Limit;

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
}
