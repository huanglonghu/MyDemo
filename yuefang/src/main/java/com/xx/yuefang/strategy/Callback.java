package com.xx.yuefang.strategy;

import android.graphics.Bitmap;

import java.io.File;

public abstract class Callback {

    public void ontakePhotoBack(File file) {
    }

    public void onSelectPhoto(Bitmap bitmap) {
    }

    public void onGetLocationBack(double latitude, double longitude,String adress) {
    }

}
