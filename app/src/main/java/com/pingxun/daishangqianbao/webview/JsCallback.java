package com.pingxun.daishangqianbao.webview;

import android.util.Log;
import android.webkit.WebView;

/**
 * 异步回调页面JS函数管理对象
 * <p/>
 * Created by luoyang on 15/11/23.
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:HostApp.callback(%d, %d %s);";
    private int index;
    private WebView webView;
    private int isPermanent;

    public JsCallback(WebView view, int index) {
        this.index = index;
        this.webView = view;
    }

    public void apply(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                sb.append("\"");
            }
            sb.append(String.valueOf(arg));
            if (isStrArg) {
                sb.append("\"");
            }
        }
        String execJs = String.format(CALLBACK_JS_FORMAT, index, isPermanent, sb.toString());
        Log.d("JsCallBack", execJs);
        webView.loadUrl(execJs);
    }

    public void setPermanent(boolean value) {
        isPermanent = value ? 1 : 0;
    }
}