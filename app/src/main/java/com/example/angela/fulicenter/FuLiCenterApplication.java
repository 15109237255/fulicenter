package com.example.angela.fulicenter;

import android.app.Application;

import com.example.angela.fulicenter.bean.User;

/**
 * Created by Angela on 2016/10/17.
 */

public class FuLiCenterApplication extends Application{

    public static FuLiCenterApplication appliction;
    private static FuLiCenterApplication instance;
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appliction=this;
        instance=this;
    }

    public  static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance =new FuLiCenterApplication();
        }
        return instance;
    }
}
