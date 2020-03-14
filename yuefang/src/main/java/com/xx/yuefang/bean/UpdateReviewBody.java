package com.xx.yuefang.bean;

public class UpdateReviewBody {


    /**
     * State : 0
     * RefuseReason : string
     * Id : 0
     */

    private int State;
    private String RefuseReason;
    private int Id;

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getRefuseReason() {
        return RefuseReason;
    }

    public void setRefuseReason(String RefuseReason) {
        this.RefuseReason = RefuseReason;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
