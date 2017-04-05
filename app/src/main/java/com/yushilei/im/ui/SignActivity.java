package com.yushilei.im.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jiechic.library.android.widget.MultiStateView;
import com.yushilei.im.BaseActivity;
import com.yushilei.im.R;
import com.yushilei.im.entity.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SignActivity extends BaseActivity {

    private EditText mUserEt;
    private EditText mPswEt;
    private EditText mConfirmPswEt;
    private View mSignTv;
    private MultiStateView multiV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initViews() {
        multiV = f(R.id.activity_sign);
        mUserEt = f(R.id.activity_sign_psw_user);
        mPswEt = f(R.id.activity_sign_psw);
        mConfirmPswEt = f(R.id.activity_sign_psw_confirm);
        mSignTv = f(R.id.activity_sign_sign);
        mUserEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString().toLowerCase();
                charSequence = s;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initListener() {
        setOnClick(mSignTv);
    }

    @Override
    public void processOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_sign_sign:
                sign();
                break;
        }
    }

    private void sign() {
        if (TextUtils.isEmpty(mUserEt.getText())) {
            showToast("用户名空");
            return;
        }
        if (TextUtils.isEmpty(mPswEt.getText())) {
            showToast("密码空");
            return;
        }
        if (TextUtils.isEmpty(mConfirmPswEt.getText())) {
            showToast("确认密码空");
            return;
        }
        if (!mPswEt.getText().toString().equals(mConfirmPswEt.getText().toString())) {
            showToast("二次密码不一致");
            return;
        }
        multiV.setState(MultiStateView.ContentState.LOADING);
        Observable.just(new User(mUserEt.getText().toString(), mPswEt.getText().toString()))
                .observeOn(Schedulers.io())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        try {
                            EMClient.getInstance().createAccount(user.getName(), user.getPsw());//同步方法
                            processSignResult(true);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            processSignResult(false);
                        }
                    }
                });
    }

    private void processSignResult(boolean isOk) {
        Observable.just(isOk).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                multiV.setState(MultiStateView.ContentState.CONTENT);
                if (aBoolean) {
                    showToast("注册成功");
                    finish();
                } else {
                    showToast("注册失败");
                }
            }
        });
    }
}
