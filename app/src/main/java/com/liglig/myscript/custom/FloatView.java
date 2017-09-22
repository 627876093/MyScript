package com.liglig.myscript.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.liglig.myscript.AppContext;
import com.liglig.myscript.util.Logger;


/**
 * Created by Administrator on 2017/9/22 0022.
 */
public class FloatView extends ImageView {
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private float beginX,endX,beginY,endY;

    private WindowManager wm=(WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams wmParams = ((AppContext)getContext().getApplicationContext()).getMywmParams();
    private Context context;
    private Handler mHandler;
    public static final int CLICK = 1;
    public FloatView(Context context, Handler mHandler) {
        super(context);
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY()/*-25*/;   //25是系统状态栏的高度

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取相对View的坐标，即以此View左上角为原点
                beginX = endX = mTouchStartX =  event.getX();
                beginY = endY = mTouchStartY =  event.getY();

                Logger.e("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                Logger.e("ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
//                updateViewPosition();
                Logger.e("ACTION_UP");

                endX = event.getX();
                endY = event.getY();

                if (endX - beginX == 0 && endY - beginY == 0){
                    //点击事件
                    Logger.e("点击事件");

                    Message msg = Message.obtain();
                    msg.what = CLICK;
                    msg.obj = "点击事件";
                    mHandler.sendMessage(msg);

                }else {
                    //发生了拖拽
                    Logger.e("发生了拖拽");
                }

                mTouchStartX=mTouchStartY=0;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Logger.e("ACTION_OUTSIDE"+"   "+"x:"+x+" y:"+y);
                break;
        }
        return true;
    }

    private void updateViewPosition(){
        //更新浮动窗口位置参数
        wmParams.x=(int)( x-mTouchStartX);
        wmParams.y=(int) (y-mTouchStartY);

        Logger.e("x:"+x+" y:"+y);
        Logger.e("开始X坐标:"+mTouchStartX+" 开始Y坐标:"+mTouchStartY);

        wm.updateViewLayout(this, wmParams);

    }

}
