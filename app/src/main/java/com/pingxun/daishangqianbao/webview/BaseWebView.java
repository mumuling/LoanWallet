package com.pingxun.daishangqianbao.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.webkit.WebSettings;


/**
 * Created by luoyang on 15/11/23.
 *
 */
public class BaseWebView extends NestWebView implements SwipeRefreshLayout.CanScrollUpListener {

    boolean mIgnoreTouchCancel;

    public BaseWebView(Context context) {
        this(context, null);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setDatabaseEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setNeedInitialFocus(true);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setDefaultTextEncodingName("utf-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webSetting.setLoadsImagesAutomatically(true);
        } else {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            webSetting.setLoadsImagesAutomatically(false);
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ViewParent parent = this;
        do {
            if (parent instanceof SwipeRefreshLayout) {
                ((SwipeRefreshLayout) parent).setCanScrollUpListener(this);
                break;
            }
        } while ((parent = parent.getParent()) != null);
    }

    public void ignoreTouchCancel(boolean val) {
        mIgnoreTouchCancel = val;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
                break;

        }
        if (action == MotionEvent.ACTION_DOWN) {
            onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
        }
        // 第一种不完美解决方案
            /*
            boolean ret = super.onTouchEvent(ev);
            if (mPreventParentTouch) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        requestDisallowInterceptTouchEvent(true);
                        ret = true;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        requestDisallowInterceptTouchEvent(false);
                        mPreventParentTouch = false;
                        break;
                }
            }
            return ret;
             */
        // 第二种完美解决方案
        return action == MotionEvent.ACTION_CANCEL && mIgnoreTouchCancel || super.onTouchEvent(ev);
    }

    @Override
    public boolean canScrollUp() {
        return getScrollY() > 0;
    }


}
