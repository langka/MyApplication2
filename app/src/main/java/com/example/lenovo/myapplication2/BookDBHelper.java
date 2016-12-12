package com.example.lenovo.myapplication2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2016/12/11.
 */

public class BookDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_BOOK = "create table if not exists book (_id integer primary key,name text)";
    private static final String CREATE_USER = "create table if not exists user (_id integer primary key,name text,value integer)";
    private static final String DB_NAME = "bookstore.db";
    public static final String BOOK_TABLE = "book";
    public static final String USER_TABLE="user";
    private static final int DB_VERSION=1;
    public BookDBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_USER);
        db.execSQL("insert into book values(1,'Android');");
        db.execSQL("insert into book values(2,'IOS');");
        db.execSQL("insert into user values(1,'jack',20);");
        db.execSQL("insert into user values(2,'tom',25);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
