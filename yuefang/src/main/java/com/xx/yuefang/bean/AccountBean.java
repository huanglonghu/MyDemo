package com.xx.yuefang.bean;

import java.io.Serializable;

public class AccountBean implements Serializable {

    private String name;

    private String key;

    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}