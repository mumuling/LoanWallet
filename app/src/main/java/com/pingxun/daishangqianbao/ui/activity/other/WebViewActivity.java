package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;

import butterknife.BindView;

/**
 * 网页浏览控件
 */
//@EActivity(R.layout.web_view)
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.progressBar1)
    ProgressBar mProgressBar1;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.web_view;
    }

    @Override
    protected void initData() {

        //新页面接收数据
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            //接收data值
            String url = bundle.getString("url");
            String productName = bundle.getString("productName");
            initTopView(productName);
            loadUrlWithWebView(url);
        }

    }

    /**
     * 加载webView
     * @param url
     */
    private void loadUrlWithWebView(String url) {
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);  //开启javascript
        settings.setDomStorageEnabled(true);  //开启DOM
        settings.setDefaultTextEncodingName("utf-8");//设置编码
        // web页面处理
        settings.setAllowFileAccess(true);// 支持文件流
        settings.setSupportZoom(true);// 支持缩放
        settings.setBuiltInZoomControls(true);// 支持缩放
        settings.setUseWideViewPort(true);// 调整到适合webview大小
        settings.setLoadWithOverviewMode(true);//调整到适合webview大小
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        settings.setBlockNetworkImage(false);
        //开启缓存机制
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        //根据当前网页连接状态
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(new WebViewClient());
        //WebView加载web资源
        mWebView.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar1.setVisibility(View.GONE);
                } else {
                    mProgressBar1.setVisibility(View.VISIBLE);
                    mProgressBar1.setProgress(newProgress);
                }
//                super.onProgressChanged(view, newProgress);
            }
        });
        loadUrl(mWebView,url);
    }

    public void loadUrl(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(mWebView.canGoBack()){
                mWebView.goBack();//返回上一页面
                return true;
            }else{
                super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.getSettings().setJavaScriptEnabled(false);
    }
}
