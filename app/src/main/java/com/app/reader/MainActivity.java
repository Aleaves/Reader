package com.app.reader;

import android.util.Log;

public class MainActivity extends BaseActivity {


    @Override
    protected void init() {
        Log.i("==========","123");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
