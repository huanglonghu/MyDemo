package com.xx.yuefang.bean;

import java.util.List;

public class GetAllApoint {


    /**
     * States : [0]
     * Page : 0
     * Limit : 0
     */

    private int Page;
    private int Limit;
    private List<Integer> States;

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
