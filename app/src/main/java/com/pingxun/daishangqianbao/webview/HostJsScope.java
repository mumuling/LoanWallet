package com.pingxun.daishangqianbao.webview;

import android.view.ViewParent;
import android.webkit.WebView;

/**
 * Created by luoyang on 15/11/23.
 */
public class HostJsScope {

    public static void preventParentTouchEvent(WebView view) {
        if (view instanceof BaseWebView) {
            ViewParent parent = view.getParent();
            while (parent != null) {
                if (parent instanceof ParentViewOnViewPager) {
                    ((ParentViewOnViewPager) parent).preventParentTouchEvent(view);
                    break;
                }
                parent = parent.getParent();
            }
        }
    }
}
