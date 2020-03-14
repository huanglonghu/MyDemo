package com.xx.yuefang.bean;

import java.util.List;

public class AppointSalepersonBean {


    /**
     * PremisesBaseId : 0
     * SalespersonIds : [0]
     */

    private int PremisesBaseId;
    private List<Integer> SalespersonIds;

    public int getPremisesBaseId() {
        return PremisesBaseId;
    }

    public void setPremisesBaseId(int PremisesBaseId) {
        this.PremisesBaseId = PremisesBaseId;
    }

    public List<Integer> getSalespersonIds() {
        return SalespersonIds;
    }

    public void setSalespersonIds(List<Integer> SalespersonIds) {
        this.SalespersonIds = SalespersonIds;
    }
}
