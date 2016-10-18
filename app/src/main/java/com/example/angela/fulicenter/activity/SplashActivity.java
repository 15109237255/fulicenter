package com.example.angela.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.utlis.MFGT;

public class SplashActivity extends AppCompatActivity {
    private final long sleepTime=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AlphaAnimation animation=new AlphaAnimation(0.3f,1.0f);
        animation.setDuration(1500);
        RelativeLayout mActivitySplash= (RelativeLayout) findViewById(R.id.activity_splash);
        mActivitySplash.startAnimation(animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            long start = System.currentTimeMillis();
            //create db
            long costTime = System.currentTimeMillis() - start;
            @Override
            public void run() {

                if (sleepTime-costTime>0){
                    try{
                        Thread.sleep(sleepTime-costTime);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }

        }).start();

    }
}
