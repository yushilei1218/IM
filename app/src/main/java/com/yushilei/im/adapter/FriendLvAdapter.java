package com.yushilei.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yushilei.im.R;
import com.yushilei.im.entity.User;
import com.yushilei.im.ui.ChatActivity;
import com.yushilei.im.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @auther by yushilei.
 * @time 2017/4/5-14:35
 * @desc
 */

public class FriendLvAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<User> data = new LinkedList<>();

    public FriendLvAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<User> list) {
        data.clear();
        if (!CollectionUtil.isEmpty(list))
            data.addAll(list);
        notifyDataSetChanged();
    }

    public boolean contains(User u) {
        return data.contains(u);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_friend, viewGroup, false);
            view.setTag(new VH(view));
        }
        User user = data.get(i);
        VH tag = (VH) view.getTag();
        tag.pos = i;
        tag.userTv.setText(user.getName());
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(ChatActivity.CHAT_USER, data.get(((VH) view.getTag()).pos));
        context.startActivity(intent);
    }

    private static final class VH {
        TextView userTv;
        int pos;

        public VH(View view) {
            userTv = (TextView) view.findViewById(R.id.friend_user);
        }
    }
}
