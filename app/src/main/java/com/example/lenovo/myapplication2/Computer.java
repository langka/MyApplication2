package com.example.lenovo.myapplication2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2016/12/10.
 */

public class Computer implements Parcelable {
    private User owner;
    private int value;

    protected Computer(Parcel in) {
        owner = in.readParcelable(User.class.getClassLoader());
        value = in.readInt();
    }

    public static final Creator<Computer> CREATOR = new Creator<Computer>() {
        @Override
        public Computer createFromParcel(Parcel in) {
            return new Computer(in);
        }

        @Override
        public Computer[] newArray(int size) {
            return new Computer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(owner, flags);
        dest.writeInt(value);
    }
}

