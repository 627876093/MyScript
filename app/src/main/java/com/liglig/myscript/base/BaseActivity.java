package com.liglig.myscript.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewResId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        ButterKnife.bind(this);
//        ShareSDK.initSDK(this, "1e89159b28ff8");
//        MobSDK.init(getApplicationContext());
        init();
        loadData();
    }

    protected void init(){}

    protected void loadData(){}

    protected abstract int getViewResId();

    /**
     * 带过场动画的启动Activity的方式
     */
    public void startActivity(Intent intent, int animIn, int animOut){
        super.startActivity(intent);
        overridePendingTransition(animIn, animOut);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
