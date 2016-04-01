package com.app.reader.utils;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Created by llb on 2016/3/10.
 */

public class LightCache {

    /**
     * 文件是否存在
     * @param path
     * @return
     */
    public static boolean testFileExist(String path) {
        File file = new File(path);
        if(file.exists()) {
            if(file.length() == 0)
                deleteFile(path); // 删除无效文件
            else
                return true;
        }
        return false;
    }

    /**
     * 读取文件
     * @param path
     * @return
     */
    public static byte[] loadFile(String path) {
        // 如果文件不存在  返回null
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            // 读取已存在文件
            int fileSize = (int) file.length(); // 获取文件大小
            try {
                FileInputStream in = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(in);

                byte[] bs = new byte[fileSize];
                if(dis.read(bs, 0, fileSize) == -1)
                    return null;

                dis.close();
                in.close();
                return bs;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 删除文件
     * @param filepath
     * @return
     */

    public static boolean deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.delete()) {
            return true;
        } else
            return false;
    }
    public static boolean deleteFile(String path, String fileName) {
        File file = new File(
                path
                        + (path.charAt(path.length() - 1) != File.separatorChar ? File.separator
                        : "") + fileName);
        if (file.delete()) {
            return true;
        } else
            return false;
    }

    public static String readTxtFile(String filePath){
        StringBuilder sb=new StringBuilder();
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    sb.append(lineTxt).append("#");
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }


}
