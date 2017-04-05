package com.yushilei.im.fragment;


import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yushilei.im.R;
import com.yushilei.im.db.Db;
import com.yushilei.im.entity.User;

import org.xutils.ex.DbException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendFragment extends DialogFragment implements View.OnClickListener {


    private EditText mAddInput;
    private EditText mReasonInput;
    private View mConfirmV;

    private FriendAddListener listener;

    public AddFriendFragment() {
        // Required empty public constructor
    }

    public static AddFriendFragment instance(FriendAddListener listener) {
        AddFriendFragment friendFragment = new AddFriendFragment();
        friendFragment.listener = listener;
        return friendFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_friend, null);
        mAddInput = (EditText) view.findViewById(R.id.friend_add_et);
        mReasonInput = (EditText) view.findViewById(R.id.friend_add_reason);

        mConfirmV = view.findViewById(R.id.friend_add);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        mConfirmV.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = getContext().getResources().getDrawable(R.drawable.sign_in_input_shape, getContext().getTheme());
        } else {
            drawable = getContext().getResources().getDrawable(R.drawable.sign_in_input_shape);
        }
        window.setBackgroundDrawable(drawable);
        attributes.width = (int) (attributes.width * 0.5);
        window.setAttributes(attributes);
    }

    @Override
    public void onClick(View view) {
        final String name = mAddInput.getText().toString().toLowerCase();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        try {
            User user = Db.getDb().findById(User.class, name);
            if (user != null) {
                Toast.makeText(getContext(), "已添加该好友", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        //参数为要添加的好友的username和添加理由
        Observable.just(mAddInput.getText().toString()).map(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                //参数为要添加的好友的username和添加理由
                boolean isAdd;
                try {
                    EMClient.getInstance().contactManager().addContact(s, mReasonInput.getText().toString());
                    isAdd = true;
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    isAdd = false;
                }
                return isAdd;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {

                            try {
                                Db.getDb().save(new User(name, null));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "添加失败", Toast.LENGTH_SHORT).show();
                        }
                        getDialog().dismiss();
                        if (aBoolean && listener != null) {
                            listener.update();
                        }

                    }
                });
    }

    public interface FriendAddListener {
        void update();
    }
}
