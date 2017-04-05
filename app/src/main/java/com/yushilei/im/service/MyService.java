package com.yushilei.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class MyService extends Service implements EMContactListener {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EMClient.getInstance().contactManager().setContactListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onContactInvited(String username, String reason) {
        Log.i(getClass().getSimpleName(), username);
        //收到好友邀请
        try {
            EMClient.getInstance().contactManager().acceptInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFriendRequestAccepted(String username) {
        //好友请求被同意
    }

    @Override
    public void onFriendRequestDeclined(String username) {
        //好友请求被拒绝
    }

    @Override
    public void onContactDeleted(String username) {
        //被删除时回调此方法
    }


    @Override
    public void onContactAdded(String username) {
        //增加了联系人时回调此方法
    }

}
