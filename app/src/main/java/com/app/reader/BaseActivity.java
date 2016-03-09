package com.app.reader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.reader.controller.InternetController;

import butterknife.ButterKnife;

/**
 * Created by llb on 2016/3/3.
 */
public abstract class BaseActivity extends AppCompatActivity{
    public InternetController controller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        controller=InternetController.getInstance();
        init();
    }
    protected abstract void init();

    protected abstract int getLayoutId();
}
