package com.pingxun.daishangqianbao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    protected ViewGroup mRootView;
    protected int mLayoutResId;
    protected String TAG;
    protected BaseActivity mActivity;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitData(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //这里的判断是起到设置缓存的效果，如果不判断则每次点击Tab都会刷新一次
        if (mRootView==null){
            //mContentView = LayoutInflater.from(mActivity).inflate(getRootLayoutResID(), null);
            mRootView = (ViewGroup) inflater.inflate(mLayoutResId, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            onInitView(mRootView);
            onLoadData(savedInstanceState);
        }else if (mRootView.getParent()!=null){
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    protected void setContentView(int layoutResID) {
        mLayoutResId = layoutResID;
    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isResumed()) {
//            handleOnVisibilityChangedToUser(isVisibleToUser);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getUserVisibleHint()) {
//            handleOnVisibilityChangedToUser(true);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (getUserVisibleHint()) {
//            handleOnVisibilityChangedToUser(false);
//        }
//    }
//
//    /**
//     * 处理对用户是否可见
//     *
//     * @param isVisibleToUser
//     */
//    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
//        if (isVisibleToUser) {
//            // 对用户可见
//
//            if (!mIsLoadedData) {
//                mIsLoadedData = true;
//                onLazyLoadOnce();
//            }
//            onVisibleToUser();
//        } else {
//            // 对用户不可见
//            onInvisibleToUser();
//        }
//    }
//
//    /**
//     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
//     */
//    protected void onLazyLoadOnce() {
//    }
//
//    /**
//     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
//     */
//    protected void onVisibleToUser() {
//    }
//
//    /**
//     * 对用户不可见时触发该方法
//     */
//    protected void onInvisibleToUser() {
//    }

    protected void onInitData(Bundle savedInstanceState) {

    }

    protected void onInitView(View rootView) {

    }

    protected void onLoadData(Bundle savedInstanceState) {

    }

    protected boolean needAnalytics() {
        return true;
    }

    protected String analyticsName() {
        return null;
    }


    @Override
    public void onDestroyView() {
//      unbinder.unbind();
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootView != null) {
            mRootView.removeAllViews();
            mRootView = null;
        }
        OkGo.getInstance().cancelTag(this);
    }


}
