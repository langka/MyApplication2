package com.example.lenovo.myapplication2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2016/12/10.
 */

public class User implements Parcelable {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    private  User(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }
}
