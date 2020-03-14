package com.xx.yuefang.util;

import android.content.Context;

public class ResUtils {

    public  int getResource(Context context,String name, String type) {
        try {
            String packageName = context.getPackageName();
            return context.getResources().getIdentifier(name, type, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private ResUtils() {
    }

    private static ResUtils defaultInstance;

    public static ResUtils getInstance() {
        ResUtils resUtils = defaultInstance;
        if (defaultInstance == null) {
            synchronized (ResUtils.class) {
                resUtils = new ResUtils();
                defaultInstance = resUtils;
            }
        }
        return resUtils;
    }




}
