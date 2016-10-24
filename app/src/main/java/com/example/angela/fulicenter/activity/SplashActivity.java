package com.example.angela.fulicenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.angela.fulicenter.FuLiCenterApplication;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.User;
import com.example.angela.fulicenter.dao.UserDao;
import com.example.angela.fulicenter.utlis.L;
import com.example.angela.fulicenter.utlis.MFGT;

public class SplashActivity extends AppCompatActivity {
    private final long sleepTime=2000;
    SplashActivity mContext;

    private static final  String TAG = SplashActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiCenterApplication.getUser();
                L.e(TAG,"fuliCenter,user= "+user);
                if(user==null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser("jcy1234567890");
                    L.e(TAG,"database,user= "+user);
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },sleepTime);


    }
}
