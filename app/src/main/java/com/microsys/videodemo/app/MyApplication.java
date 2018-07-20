package com.microsys.videodemo.app;

import android.app.Activity;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerAppComponent;
import com.microsys.videodemo.di.module.AppModule;
import com.microsys.videodemo.di.module.NetModule;
import com.microsys.videodemo.utils.CrashHandler;
import com.microsys.videodemo.utils.ScreenUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashSet;
import java.util.Set;

public class MyApplication extends MultiDexApplication {

    private static MyApplication INSTANCE;
    private static int count = 0;
    private static boolean isOnForeground = false;
    private Set<Activity> activities;
    private AppComponent appComponent;


    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;

        CrashHandler crashHandler=CrashHandler.getsInstance();
        crashHandler.init(this);

        initBaiduSdk();
        initBuglySdk();
        initComponent();
        initScreenInfos();
    }

    private void initScreenInfos() {
        ScreenUtils.getInstance().init();
    }

    private void initBuglySdk() {
        CrashReport.initCrashReport(getApplicationContext(), "2baf8ea0a7", false);
    }

    private void initComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .netModule(new NetModule())
                .appModule(new AppModule(INSTANCE))
                .build();
    }

    private void initBaiduSdk() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(INSTANCE);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 管理Activity 添加
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (activities == null) {
            activities = new HashSet<>();
        }
        activities.add(act);
    }

    /**
     * 管理Activity 移除
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (activities != null) {
            activities.remove(act);
        }
    }

    /**
     * 退出APP
     */
    public void removeAllApp() {
        if (activities != null) {
            synchronized (MyApplication.this) {
                for (Activity act : activities) {
                    act.finish();
                }
            }
        }
    }

    /**
     * 退出APP
     */
    public void exitApp() {
        if (activities != null) {
            synchronized (MyApplication.this) {
                for (Activity act : activities) {
                    act.finish();
                }
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @NonNull
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .netModule(new NetModule())
                    .appModule(new AppModule(INSTANCE))
                    .build();
        }
        return appComponent;
    }
}
