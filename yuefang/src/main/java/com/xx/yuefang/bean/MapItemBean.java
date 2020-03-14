package com.xx.yuefang.bean;

public class MapItemBean {

    private boolean selected;

    private int img;

    private String name;

    private int count;

    public MapItemBean(String name, int img) {
        this.img = img;
        this.name = name;

    }

    public MapItemBean() {
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
