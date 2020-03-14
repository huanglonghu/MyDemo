package com.example.mydemo;

import android.app.Application;
import android.graphics.Point;
import android.os.Environment;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        String current = getCurrent(2020, 3);
        System.out.println(current);

    }

    public String getCurrent(int year, int month) {
        Calendar calendar = Calendar.getInstance();//获取当前时间
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String last = format.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);//日期设置为一号，就是第一天了
        String first = format.format(calendar.getTime());
        return first+"==========AAAA==========="+last;
    }
}
