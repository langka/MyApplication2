package com.example.lenovo.myapplication2;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by lenovo on 2016/12/12.
 */

public class ViewShowerActivity extends AppCompatActivity {
    Button show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boring_layout);
        show = (Button) findViewById(R.id.move);
        show.setX(200f);
        show.setY(400f);
    }


}
