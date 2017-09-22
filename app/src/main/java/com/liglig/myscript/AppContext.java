package com.liglig.myscript;

import android.app.Application;
import android.view.WindowManager;

public class AppContext extends Application {

    private int cartCount = 0;
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }
    @Override
    public void onCreate() {
        super.onCreate();

//        OkHttpUtil.initOkHttp();
//        FrescoUtil.initFresco(this, ConfigConstants.getImagePipelineConfig(this));
//        ActiveAndroid.initialize(this);
//        ShareSDK.initSDK(this, "1e89159b28ff8");
//        JPushInterface.setDebugMode(false);//如果时正式版就改成false
//        JPushInterface.init(this);
//        LogCollector.init(this, null, null);

    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    /**
     * app退出时调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
//        ActiveAndroid.dispose();
    }

}
