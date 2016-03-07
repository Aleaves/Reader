package com.app.reader.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */

public class FileListName {
    private File file;
    private File[] files = null;
    private String type,path;
    public FileListName(String type,String path){
        this.path = path;
        this.type = type;
    }

    public File[] getfilelist()
    {
        file = new File(path);
        files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                // TODO Auto-generated method stub
                if(filename.endsWith(type))
                    return true;
                return false;
            }
        });
        return files;
    }

    public List<String> getfilelistpath()
    {
        List<String> list = new ArrayList<String>();
        file = new File(path);
        files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                // TODO Auto-generated method stub
                if(filename.endsWith(type))
                    return true;
                return false;
            }
        });
        for(int i = 0;i<files.length;i++){
            list.add(files[i].getPath());
        }
        return list;
    }
}
