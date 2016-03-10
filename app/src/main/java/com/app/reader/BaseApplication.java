package com.app.reader;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/3/9.
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "M37FJghuR9zqFI4FTQXrAWFe-gzGzoHsz", "CDf3JxKbfTmag9yXQgwR50uA");
        LitePalApplication.initialize(this);
    }
}
