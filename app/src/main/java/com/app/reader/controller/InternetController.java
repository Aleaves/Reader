package com.app.reader.controller;

/**
 * Created by llb on 2016/3/9.
 */
public class InternetController {

    public static InternetController controller;


    private InternetData internetData;
    public static InternetController getInstance(){
        if(controller==null){
            controller=new InternetController();
        }
        return controller;
    }

    private InternetController(){
        internetData=new InternetData();
    }

    public InternetData getInternetData(){
        return internetData;
    }
}
