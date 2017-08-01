package com.pingxun.daishangqianbao.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import butterknife.ButterKnife;


/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity;

    /*** Activity对象**/
    protected App mApp;
    protected String TAG;
    protected Activity me;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mApp = App.getInstance();//保存Activity栈
        this.me = this;//引用me自己，便于子类调用

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置横屏
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData();
    }

    protected abstract int getLayoutId();
    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    /**
     * 初始化头部返回的View
     */
    public void initTopView(String titleStr) {
//        ImageView back = (ImageView) findViewById(R.id.iv_topview_back);
//        TextView title = (TextView) findViewById(R.id.tv_topview_title);
//        if (back != null) {
//            back.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  closeActivtiy();
//                }
//            });
//        }
//        if (title != null) {
//            title.setText(titleStr);
//        }

    }




    public void closeActivtiy() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
