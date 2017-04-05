package com.yushilei.im;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private SparseArray<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();

        setContentView(getLayoutId());

        initViews();
        initListener();
        initData();
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    protected void initViews() {
    }

    public <E extends View> void setOnClick(E v) {
        v.setOnClickListener(this);
    }


    protected abstract int getLayoutId();

    public <E extends View> E f(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        processOnClick(view);
    }

    public abstract void processOnClick(View view);

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
