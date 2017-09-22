package com.liglig.myscript.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * 所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

//    protected OnDeliverMsgListener listener;
    protected OnCartUpListener cartListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getViewResId(), container, false);
        ButterKnife.bind(this, view);
//        ShareSDK.initSDK(getContext(), "1e89159b28ff8");
//        MobSDK.init(getContext().getApplicationContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
        getData(getArguments());
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void getData(Bundle arguments) {
    }

    protected void init(View view){}

    protected  void loadData(){}

    protected abstract int getViewResId();

    public void setOnCartUpListener(OnCartUpListener cartListener) {
        this.cartListener = cartListener;
    }

    public interface OnCartUpListener {
        void onCartAnim(View view, String url);
        void onCartUp(int count);
    }

//    public void setOnDeliverMsgListener(OnDeliverMsgListener listener){
//        this.listener = listener;
//    }

//    public interface OnDeliverMsgListener{
//        void onDeliverMsg(MsgEntity msg);
//    }

    public void updateUI(String info){}

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
