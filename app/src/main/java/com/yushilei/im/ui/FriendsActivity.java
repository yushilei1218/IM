package com.yushilei.im.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yushilei.im.BaseActivity;
import com.yushilei.im.R;
import com.yushilei.im.adapter.FriendLvAdapter;
import com.yushilei.im.constant.Constant;
import com.yushilei.im.db.Db;
import com.yushilei.im.entity.User;
import com.yushilei.im.fragment.AddFriendFragment;
import com.yushilei.im.service.MyService;
import com.yushilei.im.util.Json;
import com.yushilei.im.util.SpUtil;

import org.xutils.ex.DbException;

import java.util.List;

public class FriendsActivity extends BaseActivity implements AddFriendFragment.FriendAddListener {
    private User curUser;
    private ListView mFriendLv;
    private FriendLvAdapter adapter;
    private View mAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends;
    }

    @Override
    protected void initViews() {
        curUser = Json.jsonToObj(SpUtil.getSP(Constant.CUR_USER, String.class), User.class);
        mFriendLv = f(R.id.activity_friends_lv);
        mAdd = f(R.id.activity_friends_add);
        adapter = new FriendLvAdapter(this);
        mFriendLv.setAdapter(adapter);
        initFriendLv();
        startService(new Intent(this, MyService.class));
    }

    @Override
    protected void initListener() {
        setOnClick(mAdd);
    }

    @Override
    public void processOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_friends_add:
                AddFriendFragment.instance(this).show(getSupportFragmentManager(), "");
                break;
        }
    }

    private void initFriendLv() {
        try {
            List<User> users = Db.getDb().findAll(User.class);
            if (users != null)
                showToast("" + users.size());
            adapter.addAll(users);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        initFriendLv();
    }
}
