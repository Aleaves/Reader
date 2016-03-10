package com.app.reader.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by llb on 2016/3/10.
 */
public class ReadConfig {

    public static String loadFullFileFromSaveFolder(String fileName){
        String h="";
        try {
            byte[] b = LightCache.loadFile(fileName);
            if(b == null) return "";
            h = new String(b, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return h;
    }
}
