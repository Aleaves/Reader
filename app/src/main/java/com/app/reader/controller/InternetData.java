package com.app.reader.controller;

import com.app.reader.listener.DataListener;
import com.app.reader.utils.ReaderConstants;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import java.util.List;

/**
 * Created by llb on 2016/3/9.
 */

public class InternetData {

    public void  getNovelList(DataListener listener){
        getDataParams(ReaderConstants.NOVEL_LIST,listener);
    }

    private void getDataParams(final String dataName, final DataListener listener){
        AVQuery<AVObject> query=new AVQuery<AVObject>(dataName);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    listener.DataSuccess(list,dataName);
                }else{
                    listener.DataFailture();
                }
            }
        });
    }

}
