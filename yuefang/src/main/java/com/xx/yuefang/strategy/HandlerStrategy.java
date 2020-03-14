package com.xx.yuefang.strategy;

import android.graphics.Bitmap;

import java.io.File;

import okhttp3.MultipartBody;

public abstract class HandlerStrategy {

    public void onActivityResult(String text) {

    }

    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
    }

    public void onActivityResult() {
    }

    public void onActivityResult(File file) {
    }
}
