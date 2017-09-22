package com.liglig.myscript;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.liglig.myscript.base.BaseActivity;
import com.liglig.myscript.custom.FloatView;
import com.liglig.myscript.util.Logger;


public class MainActivity extends BaseActivity {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;
    private ImageView mLayout;
    boolean isRun = false;

    private float x;
    private float y;
    private float mTouchStartX;
    private float mTouchStartY;
    private float beginX,endX,beginY,endY;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FloatView.CLICK:
                    //属性动画--旋转
                    Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.property_animator);
                    animator.setTarget(mLayout);
                    animator.start();
                    if (isRun) {
                        Logger.e("收到click，开启");
                        isRun = false;
                        mLayout.setBackgroundResource(R.drawable.ic_on);
                    } else {
                        Logger.e("收到click，关闭");
                        isRun = true;
                        mLayout.setBackgroundResource(R.drawable.ic_off);
                    }

                    break;
            }
        }
    };

    @Override
    protected int getViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        /**
         * Android6.0以上系统增加了权限管理，所以需要添加如下代码，来让用户选择打开桌面浮窗的权限
         */
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
        showView();

    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY()/*-25*/;   //25是系统状态栏的高度

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                //获取相对View的坐标，即以此View左上角为原点
                beginX = endX = mTouchStartX =  event.getX();
                beginY = endY = mTouchStartY =  event.getY();

                Logger.e("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE://拖动
//                updateViewPosition();
                Logger.e("ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP://放开
//                updateViewPosition();
                Logger.e("ACTION_UP");

                endX = event.getX();
                endY = event.getY();

                if (endX - beginX == 0 && endY - beginY == 0){
                    //点击事件
                    Logger.e("点击事件");

//                    Message msg = Message.obtain();
//                    msg.what = CLICK;
//                    msg.obj = "点击事件";
//                    mHandler.sendMessage(msg);

                }else {
                    //发生了拖拽
                    Logger.e("发生了拖拽");
                }


                break;
            case MotionEvent.ACTION_OUTSIDE:
                Logger.e("ACTION_OUTSIDE");
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private void showView() {

        mLayout = new FloatView(getApplicationContext(), mHandler);

        mLayout.setBackgroundResource(R.drawable.ic_off);

        //获取WindowManager
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        param = ((AppContext) getApplication()).getMywmParams();

        param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示类型,重要（These windows are always on top of application windows）
        param.format = 1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 表示Window不需要获取焦点
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;//可以监听MotionEvent的ACTION_OUTSIDE事件
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版限制--即允许在可见的屏幕之外

        param.alpha = 1.0f;

        param.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        param.x = 0;
        param.y = 0;

        //设置悬浮窗口长宽数据
        param.width = 50;
        param.height = 50;

        //显示myFloatView图像
        mWindowManager.addView(mLayout, param);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在程序退出(Activity销毁）时销毁悬浮窗口
        mWindowManager.removeView(mLayout);
    }
}
