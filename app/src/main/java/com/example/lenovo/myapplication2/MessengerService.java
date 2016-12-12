package com.example.lenovo.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lenovo on 2016/12/11.
 */

public class MessengerService extends Service{
    static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Messenger reply = msg.replyTo;
            String received = msg.getData().getString("info");
            Log.d("MessengerService","received from client"+received);
            Message newmsg = Message.obtain();
            Bundle data = new Bundle();
            data.putString("info",received);
            newmsg.setData(data);
            try {
                reply.send(newmsg);
            } catch (RemoteException e) {
                Log.d("MessengerService","send to client failed");
            }
        }
    }
    Messenger receiver = new Messenger(new MyHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return receiver.getBinder();
    }
}
