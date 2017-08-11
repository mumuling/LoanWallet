package com.pingxun.daishangqianbao.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.utils.AppManager;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected AppCompatActivity activity;


    protected String TAG;
    protected Activity me;
//    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
//    private int netMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();

        this.me = this;//引用me自己，便于子类调用
//        evevt = this;
//        inspectNet();
        setFullTransparency();//5.0以上的手机设置全透明
//      UltimateBar ultimateBar = new UltimateBar(this);
//      ultimateBar.setColorBar(ContextCompat.getColor(this,R.color.tab_font_bright));//设置状态栏颜色
//      ultimateBar.setImmersionBar();//设置完全透明状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置横屏
        setContentView(getLayoutId());//设置父布局
        AppManager.addActivity(this);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 5.0以上的手机设置全透明状态栏
     */
    private void setFullTransparency() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 初始化时判断有没有网络
     */

//    public boolean inspectNet() {
//        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
//        return isNetConnect();
//
//        // if (netMobile == 1) {
//        // System.out.println("inspectNet：连接wifi");
//        // } else if (netMobile == 0) {
//        // System.out.println("inspectNet:连接移动数据");
//        // } else if (netMobile == -1) {
//        // System.out.println("inspectNet:当前没有网络");
//        //
//        // }
//    }

    /**
     * 网络变化之后的类型
     */
//    @Override
//    public void onNetChange(int netMobile) {
//        TODO Auto-generated method stub
//        this.netMobile = netMobile;
//        isNetConnect();
//
//    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
//    public boolean isNetConnect() {
//        if (netMobile == 1) {
//            return true;
//        } else if (netMobile == 0) {
//            return true;
//        } else if (netMobile == -1) {
//            return false;
//
//        }
//        return false;
//    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
        RelativeLayout back = (RelativeLayout)findViewById(R.id.iv_topview_back);
        TextView title = (TextView) findViewById(R.id.tv_topview_title);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeActivtiy();
                }
            });
        }
        if (title != null) {
            title.setText(titleStr);
        }
    }


    public void closeActivtiy() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        AppManager.finishActivity(me);
    }
}
