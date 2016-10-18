package com.example.angela.fulicenter;

import android.app.Application;

/**
 * Created by Angela on 2016/10/17.
 */

public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication appliction;
    private static FuLiCenterApplication instance;

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
