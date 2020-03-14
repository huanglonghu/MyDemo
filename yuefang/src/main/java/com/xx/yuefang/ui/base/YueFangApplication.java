package com.xx.yuefang.ui.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.danikula.videocache.HttpProxyCacheServer;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.xx.yuefang.database.DaoMaster;
import com.xx.yuefang.database.DaoSession;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import com.facebook.FacebookSdk;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;

import cn.jpush.im.android.api.JMessageClient;

public class YueFangApplication extends Application {
    private static YueFangApplication application;


    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        YueFangApplication app = (YueFangApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheFilesCount(20)           //20个
                .maxCacheSize(200 * 1024 * 1024) //200M
                .build();
    }



    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this, "8b414ecdc1", true);
        application = this;
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);
        initWindowSize();
        MQConfig.init(this, "d6372cd19cc66439d2fcd36781ba4ed7", new OnInitCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtil.log("========美洽id========" + s);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        setDatabase();
        FacebookSdk.sdkInitialize(getApplicationContext());
        int designWidth = 750;
        new RudenessScreenHelper(this, designWidth).activate();
        JMessageClient.setDebugMode(false);
        JMessageClient.init(getApplicationContext());
        //注册全局事件监听类
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }
            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.log("加载内核是否成功:" + b);
            }
        });

//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale=Locale.TAIWAN;
//        res.updateConfiguration(conf, dm);

    }

    public void initWindowSize() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        windownWidth = metric.widthPixels;
        windowHeight = metric.heightPixels;

    }

    private int windownWidth;
    private int windowHeight;

    public int getWindownWidth() {
        return windownWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * 获取上下文
     */
    public static YueFangApplication getApplication() {
        return application;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private DaoSession mDaoSession;
    private SQLiteDatabase db;

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);

        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
