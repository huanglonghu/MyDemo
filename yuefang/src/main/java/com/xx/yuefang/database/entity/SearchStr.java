package com.xx.yuefang.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SearchStr {
    @org.greenrobot.greendao.annotation.Id
    private Long Id;
    private String str;
    @Generated(hash = 1608016990)
    public SearchStr(Long Id, String str) {
        this.Id = Id;
        this.str = str;
    }
    @Generated(hash = 1642741560)
    public SearchStr() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getStr() {
        return this.str;
    }
    public void setStr(String str) {
        this.str = str;
    }

}
