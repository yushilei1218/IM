package com.yushilei.im.ui;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.yushilei.im.BaseActivity;
import com.yushilei.im.R;
import com.yushilei.im.adapter.ChatRecyAdapter;
import com.yushilei.im.entity.User;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChatActivity extends BaseActivity implements EMMessageListener {
    public static final String CHAT_USER = "CHAT_USER";
    private User chatUser;
    private EditText input;
    private View sendV;
    private RecyclerView chatRecycler;
    private ChatRecyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initViews() {
        chatUser = (User) getIntent().getSerializableExtra(CHAT_USER);
        if (chatUser == null) {
            throw new RuntimeException("传入的User为空");
        }
        input = (EditText) f(R.id.activity_chat_input);
        sendV = f(R.id.activity_chat_send);
        chatRecycler = (RecyclerView) f(R.id.activity_chat_recycler);
        adapter = new ChatRecyAdapter(this);
        chatRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void initListener() {
        setOnClick(sendV);
    }

    @Override
    public void processOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_chat_send:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        final String msg = input.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            showToast("发送内容为空");
            return;
        }
        input.setText(null);
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg, chatUser.getName());
        adapter.add(message);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    //------------------ ----------------
    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        //收到消息
        Observable.just(messages).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<EMMessage>>() {
                    @Override
                    public void call(List<EMMessage> emMessages) {
                        adapter.add(emMessages);
                    }
                });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //收到透传消息
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
        //收到已送达回执
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(this);
        super.onDestroy();
    }
}
