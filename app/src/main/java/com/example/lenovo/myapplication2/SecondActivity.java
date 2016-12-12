package com.example.lenovo.myapplication2;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.ref.WeakReference;


/**
 * Created by lenovo on 2016/12/10.
 */

public class SecondActivity extends AppCompatActivity {
    CheckBox box;
    TextView queried;
    Button query;
    Button addValues;
    EditText text;
    Button bindmsg;
    Button sendmsg;
    Messenger sender;
    EditText content;
    EditText _id;
    EditText value;
    Messenger receiver = new Messenger(new ClientHandler(this));
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    static class ClientHandler extends Handler {
        WeakReference<Activity> belong;

        public ClientHandler(Activity owner) {
            belong = new WeakReference<Activity>(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(belong.get(), "服务器端收到了:" + msg.getData().getString("info"), Toast.LENGTH_SHORT).show();
        }
    }
    Uri current;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sender = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sender = null;
            receiver = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        queried = (TextView) findViewById(R.id.queried);
        query = (Button) findViewById(R.id.query);
        addValues = (Button) findViewById(R.id.addValues);
        text = (EditText) findViewById(R.id.content);
        bindmsg = (Button) findViewById(R.id.bindmsg);
        sendmsg = (Button) findViewById(R.id.sendmsg);
        box = (CheckBox) findViewById(R.id.isBookTable);
        _id = (EditText) findViewById(R.id._id);
        value = (EditText) findViewById(R.id.value);
       //content = (EditText) findViewById(R.id.content);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box.isChecked()){
                    Cursor cursor=getContentResolver().query(BookProvider.URI_BOOK,new String[]{"_id","name"},null,null,null);
                    String result="";
                    while(cursor.moveToNext()){
                        result+=cursor.getInt(0)+cursor.getString(1);
                    }
                    queried.setText(result);
                    cursor.close();
                }
                else{
                    Cursor cursor=getContentResolver().query(BookProvider.URI_USER,new String[]{"_id","name","value"},null,null,null);
                    String result="";
                    while(cursor.moveToNext()){
                        result+=cursor.getInt(0)+cursor.getString(1)+cursor.getInt(2);
                    }
                    queried.setText(result);
                    cursor.close();
                }

            }
        });
        addValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(_id.getText().toString());
                String name = text.getText().toString();
                int valu = 0;
                try {
                    valu = Integer.parseInt(value.getText().toString());
                } catch (NumberFormatException e) {

                }
                if(box.isChecked()){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_id",id);
                    contentValues.put("name",name);
                    getContentResolver().insert(BookProvider.URI_BOOK,contentValues);
                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_id",id);
                    contentValues.put("name",name);
                    contentValues.put("value",valu);
                    getContentResolver().insert(BookProvider.URI_BOOK,contentValues);
                }
            }
        });
        bindmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MessengerService.class);
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = text.getText().toString();
                if (sender != null) {
                    Message msg = Message.obtain();
                    msg.replyTo = receiver;
                    Bundle data = new Bundle();
                    data.putString("info", m);
                    msg.setData(data);
                    try {
                        sender.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }
}
