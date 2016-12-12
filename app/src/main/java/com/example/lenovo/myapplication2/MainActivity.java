package com.example.lenovo.myapplication2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.myapplication2.book.Book;
import com.example.lenovo.myapplication2.book.IBookManager;
import com.example.lenovo.myapplication2.book.IOnNewBookArrived;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    IBookManager manager;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             manager = IBookManager.Stub.asInterface(service);
            Log.d("client","bind");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
            Log.d("client","unbind");
        }
    };
    Button bind;
    Button unbind;
    Button add;
    Button search;
    EditText id;
    EditText name;
    TextView area;
    Button reg;
    Button unreg;
    Button start;
    IOnNewBookArrived i = new IOnNewBookArrived() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBook(Book book) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {//作为binder传过去
            return null;
        }
    };
    IOnNewBookArrived lt = new IOnNewBookArrived.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBook(Book book) throws RemoteException {
            Log.d("clientInfo","new Book arrived");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);
        id = (EditText) findViewById(R.id.id);
        name = (EditText)findViewById(R.id.name);
        area = (TextView) findViewById(R.id.area);
        reg = (Button) findViewById(R.id.reg);
        unreg = (Button) findViewById(R.id.unreg);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewShowerActivity.class);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager!=null) {
                    try {
                        manager.register(lt);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        unreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager!=null){
                    try {
                        manager.unregister(lt);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookManagerService.class);
                bindService(intent,conn, Context.BIND_AUTO_CREATE);
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(conn);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager!=null){
                    int i=Integer.parseInt(id.getText().toString());
                    String na = name.getText().toString();
                    Book b = new Book(i,na);
                    try {
                        manager.addBook(b);
                        Log.d("client","add successful");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.d("client","remote error");
                    }
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager!=null){
                    try {
                        List<Book> bookList = manager.getBooks();
                        Log.d("client","search successful");
                        area.setText(bookList.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.d("client","remote error");
                    }
                }
            }
        });
    }
}
