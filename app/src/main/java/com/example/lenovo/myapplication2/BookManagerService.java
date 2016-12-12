package com.example.lenovo.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.lenovo.myapplication2.book.Book;
import com.example.lenovo.myapplication2.book.IBookManager;
import com.example.lenovo.myapplication2.book.IOnNewBookArrived;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lenovo on 2016/12/10.
 */

public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> books =new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrived> listeners = new RemoteCallbackList<IOnNewBookArrived>();
    private IBinder mbinder = new IBookManager.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d("serverInfo",aString);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            Log.d("serverInfo","client search books");
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d("serverInfo","client added a book");
            books.add(book);
            int n = listeners.beginBroadcast();
            for(int i=0;i<n;i++){
                listeners.getBroadcastItem(i).onNewBook(book);
            }
            listeners.finishBroadcast();
        }

        @Override
        public void register(com.example.lenovo.myapplication2.book.IOnNewBookArrived listener) throws RemoteException {
            if(listeners.register(listener))
                 Log.d("serverInfo","reg successfully");
            final int m = listeners.beginBroadcast();
            listeners.finishBroadcast();
            Log.d("serverInfo-reg",Integer.toString(m));
        }

        @Override
        public void unregister(com.example.lenovo.myapplication2.book.IOnNewBookArrived listener) throws RemoteException {
            if(listeners.unregister(listener))
                Log.d("serverInfo","unreg successfully");
            final int m = listeners.beginBroadcast();
            listeners.finishBroadcast();
            Log.d("serverInfo-unr",Integer.toString(m));
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("serverInfo"," service created");
        books.add(new Book(1,"android"));
        books.add(new Book(2,"ios"));
        Log.d("serverInfo","books initialized");
    }
}
