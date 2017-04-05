package com.yushilei.im.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jiechic.library.android.widget.MultiStateView;
import com.yushilei.im.BaseActivity;
import com.yushilei.im.R;
import com.yushilei.im.constant.Constant;
import com.yushilei.im.entity.User;
import com.yushilei.im.util.Json;
import com.yushilei.im.util.SpUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity {

    private MultiStateView multiV;
    private EditText mNameEt;
    private EditText mPawEt;
    private View mLoginTv;
    private View mSignTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initViews() {
        multiV = f(R.id.activity_sign_in);
        mNameEt = f(R.id.activity_sign_in_user);
        mPawEt = f(R.id.activity_sign_in_psw);
        mLoginTv = f(R.id.activity_sign_in_login);
        mSignTv = f(R.id.activity_sign_in_sign);
    }

    @Override
    protected void initListener() {
        setOnClick(mLoginTv);
        setOnClick(mSignTv);
    }

    @Override
    public void processOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_sign_in_login:
                loginInConfirm();
                break;
            case R.id.activity_sign_in_sign:
                signJump();
                break;
        }
    }

    private void signJump() {
        startActivity(new Intent(this, SignActivity.class));
    }

    private void loginInConfirm() {
        final String name = mNameEt.getText().toString();
        final String psw = mPawEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "密码空", Toast.LENGTH_SHORT).show();
            return;
        }
        multiV.setState(MultiStateView.ContentState.LOADING);

        EMClient.getInstance().login(name, psw, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");

                Observable.just("登录聊天服务器成功！").observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        multiV.setState(MultiStateView.ContentState.CONTENT);
                        showToast(s);
                        SpUtil.save(Constant.CUR_USER, Json.toJson(new User(name, psw)));
                        startActivity(new Intent(LoginActivity.this, FriendsActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

                Observable.just("登录聊天服务器失败！").observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        multiV.setState(MultiStateView.ContentState.CONTENT);
                        showToast(s);
                    }
                });
            }
        });
    }
}
