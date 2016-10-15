package com.example.angela.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.utlils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.d("MainActivity onCreate");
    }
}
