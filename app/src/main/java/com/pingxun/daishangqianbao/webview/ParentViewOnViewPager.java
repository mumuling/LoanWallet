package com.pingxun.daishangqianbao.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by luoyang on 15/11/23.
 */
public class ParentViewOnViewPager extends FrameLayout {

    private BaseWebView mDispatchWebView;

    public ParentViewOnViewPager(Context context) {
        super(context);
    }

    public ParentViewOnViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentViewOnViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void preventParentTouchEvent(WebView view) {
        if (view instanceof BaseWebView) {
            mDispatchWebView = (BaseWebView) view;
        }
    }

    //手势事件拦截判断
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /**
         * 当在页面某节点的touchstart事件里调用preventHostViewTouchMove时
         * 此时拦截后续的TouchMove事件给mDispatchWebView
         * 但在这之前mDispatchWebView会收到一个Touch Cancel消息
         * 这时应该让mDispatchWebView暂时忽略这个Cancel事件（在TouchUp后恢复）
         * 避免打断页面DOM节点绑定的事件处理流程而导致节点不跟手或不响应
         * */
        if (ev.getAction() == MotionEvent.ACTION_MOVE && mDispatchWebView != null) {
            mDispatchWebView.ignoreTouchCancel(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDispatchWebView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mDispatchWebView.onTouchEvent(ev);
                    break;
                default:    // 按下结束后（Touch Up 或者 Touch Cancel）则取消独占
                    mDispatchWebView.ignoreTouchCancel(false);
                    mDispatchWebView.onTouchEvent(ev);
                    mDispatchWebView = null;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
