package com.xx.yuefang.bean;

public class GetHouseResourcesList {


    public Long getSalespersonId() {
        return SalespersonId;
    }

    public void setSalespersonId(Long salespersonId) {
        SalespersonId = salespersonId;
    }

    /**
     * SalespersonId : 0
     * Page : 0
     * Limit : 0
     */

    private Long SalespersonId;
    private int Page;
    private int Limit;
    private Long DeveloperId;
    private int Active;
    private String PremisesName;


    public String getPremisesName() {
        return PremisesName;
    }

    public void setPremisesName(String premisesName) {
        PremisesName = premisesName;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public Long getDeveloperId() {
        return DeveloperId;
    }

    public void setDeveloperId(Long developerId) {
        DeveloperId = developerId;
    }


    public int getPage() {
        return Page;
    }

    public void setPage(int Page) {
        this.Page = Page;
    }

    public Integer getLimit() {
        return Limit;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }
}
