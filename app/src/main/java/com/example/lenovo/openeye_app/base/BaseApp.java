package com.example.lenovo.openeye_app.base;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by Lenovo on 2019/5/15.
 */

public class BaseApp extends Application {
    private static BaseApp app;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Fresco.initialize(this);//初始化Fresco类
        sharedPreferences = getSharedPreferences("userTag", MODE_PRIVATE);
        editor = sharedPreferences.edit();

       /* UMConfigure.init(this, "5cc7e8a63fc1951299000ac6"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//5cc7e8a63fc1951299000ac6


        UMConfigure.setLogEnabled(true);

        //todo 修改各个平台的ak
        //初始化接口可以参照文档接下来需要设置各个平台的appkey：
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        //PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setSinaWeibo("2603666688", "814182f751f4a49abf14496d352e1550",
                "http://sns.whalecloud.com");

        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
*/

        if (LeakCanary.isInAnalyzerProcess(this)) {//1 // This process is dedicated to LeakCanary for heap analysis. // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }

    public static BaseApp getApp() {
        return app;
    }
}
