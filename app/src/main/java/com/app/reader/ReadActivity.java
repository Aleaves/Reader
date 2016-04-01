package com.app.reader;

import android.util.Log;

import com.app.reader.View.ReadView;
import com.app.reader.utils.LightCache;
import com.app.reader.utils.ReaderConstants;

import butterknife.Bind;

/**
 * Created by llb on 2016/3/10.
 * 阅读界面
 */


public class ReadActivity extends BaseActivity{
    private String filePath;
    @Bind(R.id.readView)
    ReadView mReadView;
    @Override
    protected void init() {
        filePath=getIntent().getStringExtra(ReaderConstants.FILE_PATH);
        //String str= ReadConfig.loadFullFileFromSaveFolder(filePath);
        String str= LightCache.readTxtFile(filePath);
        mReadView.setContent(str);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read;
    }

}
