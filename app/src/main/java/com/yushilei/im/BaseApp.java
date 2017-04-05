package com.yushilei.im;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;

/**
 * @auther by yushilei.
 * @time 2017/4/5-10:43
 * @desc
 */

public class BaseApp extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //数据库初始化
        x.Ext.init(this);
        if (BuildConfig.DEBUG) {
            x.Ext.setDebug(true); // 开启debug会影响性能
        } else {
            x.Ext.setDebug(false);
        }
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
