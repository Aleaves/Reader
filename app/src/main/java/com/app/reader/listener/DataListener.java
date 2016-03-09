package com.app.reader.listener;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by llb on 2016/3/9.
 */
public interface DataListener {
    /**
     * 数据加载成功
     * @param list
     * @param interfaceType
     */
    void DataSuccess(List<AVObject> list,String interfaceType);

    /**
     * 数据加载失败
     */
    void DataFailture();

    /**
     * 网络连接失败
     */
    void NetError();
}
