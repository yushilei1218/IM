package com.yushilei.im.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.yushilei.im.R;
import com.yushilei.im.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @auther by yushilei.
 * @time 2017/4/5-16:29
 * @desc
 */

public class ChatRecyAdapter extends RecyclerView.Adapter<ChatRecyAdapter.ChatVH> {
    private List<EMMessage> data = new LinkedList<>();
    private Context context;

    public ChatRecyAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<EMMessage> d) {
        data.clear();
        if (!CollectionUtil.isEmpty(d)) {
            data.addAll(d);
        }
        notifyDataSetChanged();
    }

    public void add(List<EMMessage> d) {
        if (!CollectionUtil.isEmpty(d)) {
            data.addAll(d);
        }
        notifyDataSetChanged();
    }

    public void add(EMMessage m) {
        data.add(m);
        notifyDataSetChanged();
    }

    @Override
    public ChatVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat1, parent, false);
        ChatVH tag = new ChatVH(view);
        view.setTag(tag);

        return tag;
    }

    @Override
    public void onBindViewHolder(ChatVH holder, int position) {
        EMMessage emMessage = data.get(position);
        holder.userTv.setText(emMessage.getFrom());
        holder.msgTv.setText(((EMTextMessageBody) emMessage.getBody()).getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static final class ChatVH extends RecyclerView.ViewHolder {

        private final TextView userTv;
        private final TextView msgTv;

        public ChatVH(View itemView) {
            super(itemView);
            msgTv = (TextView) itemView.findViewById(R.id.item_chat1_msg);
            userTv = (TextView) itemView.findViewById(R.id.item_chat1_user);
        }
    }
}
