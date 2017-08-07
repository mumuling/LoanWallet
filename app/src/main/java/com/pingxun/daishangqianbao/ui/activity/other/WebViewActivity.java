package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.webview.BaseWebView;

import butterknife.BindView;

/**
 * WebView界面
 */

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.webView)
    BaseWebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.web_view;
    }

    @Override
    protected void initData() {

        //新页面接收数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //接收data值
            String url = bundle.getString("url");
            String productName = bundle.getString("productName");
            initTopView(productName);

            mWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress==100){
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }else {
                        if (View.INVISIBLE==mProgressBar.getVisibility()){
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });
            mWebView.loadUrl(url);
        }

    }

}
