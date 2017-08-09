package com.pingxun.daishangqianbao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by LH on 2015/2/6.
 * Fragment基类
 *
 * @author LH
 */
public abstract class BaseFragment extends Fragment {

    protected ViewGroup mContentView;
    protected String TAG;
    protected BaseActivity mActivity;
    protected boolean mIsLoadedData = false;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseActivity) getActivity();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //这里的判断是起到设置缓存的效果，如果不判断则每次点击Tab都会刷新一次
        if (mContentView==null){
            //mContentView = LayoutInflater.from(mActivity).inflate(getRootLayoutResID(), null);
            mContentView = (ViewGroup) inflater.inflate(getRootLayoutResID(), container, false);
            unbinder = ButterKnife.bind(this, mContentView);
            initData();
        }else if (mContentView.getParent()!=null){
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见

            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {
    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {
    }

    /**
     * 获取布局文件根视图
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getRootLayoutResID();

    /**
     * 处理业务逻辑，状态恢复等操作
     */
    protected abstract void initData();



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        OkGo.getInstance().cancelTag(this);
    }


}
