package com.example.lenovo.myapplication2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.UriPermission;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lenovo on 2016/12/11.
 */

public class BookProvider extends ContentProvider {

    SQLiteDatabase database;
    Context context;
    public static final String authority="com.example.lenovo.myapplication2";
    public static final Uri URI_BOOK = Uri.parse("content://"+authority+"/book");
    public static final Uri URI_USER = Uri.parse("content://"+authority+"/user");
    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(authority,"book",0);
        matcher.addURI(authority,"user",1);
    }
    private String getTableByUri(Uri a){
        switch (matcher.match(a)){
            case 0: return BookDBHelper.BOOK_TABLE;
            case 1: return  BookDBHelper.USER_TABLE;
            default: return null;
        }
    }
    @Override
    public boolean onCreate() {
        context=getContext();
        BookDBHelper helper = new BookDBHelper(context);
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table =getTableByUri(uri);
        if(table!=null){
            return database.query(table,projection,selection,selectionArgs,null,sortOrder,null);
        }
        else {
            Log.d("ProviderError","NOT MATCH URI");
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table =getTableByUri(uri);
        if(table!=null){
            database.insert(table,null,values);
            context.getContentResolver().notifyChange(uri,null);
            return uri;
        }else return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
