package com.app.reader;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.app.reader.utils.ReaderConstants;
import com.app.reader.utils.SPUtils;

/**
 * Created by llb on 2016/3/3.
 */

public class WelcomeActivity extends BaseActivity{

    @Override
    protected void init() {
        if((Boolean)SPUtils.get(this,ReaderConstants.IS_FIRST_IN,true)){
            mHandler.sendEmptyMessageDelayed(ReaderConstants.TO_GUIDEACTIVITY,ReaderConstants.SPLASH_DELAY_MILLIS);
        }else{
            mHandler.sendEmptyMessageDelayed(ReaderConstants.TO_MAINACTIVITY,ReaderConstants.SPLASH_DELAY_MILLIS);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ReaderConstants.TO_MAINACTIVITY:
                    toMainActivity();
                    break;
                case ReaderConstants.TO_GUIDEACTIVITY:
                    toGuideActivity();
                    break;
            }
        }
    };

    private void toMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toGuideActivity(){
        Intent intent=new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

}
