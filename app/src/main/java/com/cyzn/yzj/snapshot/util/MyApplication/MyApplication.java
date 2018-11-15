package com.cyzn.yzj.snapshot.util.MyApplication;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YZJ
 * @description app退出统一管理类
 * @date 2018/11/6 0006
 */
public class MyApplication extends Application {
    public static List<Object> activities = new ArrayList<Object>();
    private static MyApplication instance;

    /**
     * 获取单例模式中唯一的MyApplication实例
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 添加activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.contains(activity);
        }
    }

    /**
     * 退出App时调用该方法
     * 遍历所有activity并且finish。
     */
    public void destory() {
        for (Object activity : activities) {
            ((Activity) activity).finish();
        }
        System.exit(0);
    }
}
