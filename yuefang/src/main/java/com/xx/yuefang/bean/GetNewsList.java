package com.xx.yuefang.bean;

public class GetNewsList {


    /**
     * NewsInfoType : 0
     * Title : string
     * Page : 0
     * Limit : 0
     */

    private int NewsInfoType;
    private String Title;
    private int Page;
    private int Limit;
    private boolean IsDraft;
    private boolean IsDel;

    public boolean isDraft() {
        return IsDraft;
    }

    public void setDraft(boolean draft) {
        IsDraft = draft;
    }

    public boolean isDel() {
        return IsDel;
    }

    public void setDel(boolean del) {
        IsDel = del;
    }

    public int getNewsInfoType() {
        return NewsInfoType;
    }

    public void setNewsInfoType(int NewsInfoType) {
        this.NewsInfoType = NewsInfoType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
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
