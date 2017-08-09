package com.pingxun.daishangqianbao.ui.activity.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.utils.AppManager;
import com.pingxun.daishangqianbao.utils.ArrayUtils;
import com.pingxun.daishangqianbao.utils.GDlocationUtil;

import butterknife.BindView;

/**
 * 主Activity
 */
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(R.id.my_tabhost)
    FragmentTabHost mTabHost;
    private long mLastPressBackTime;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

    @Override
    protected int getLayoutId() {
        return R.layout.ui_home;
    }

    @Override
    protected void initData() {
        GDlocationUtil.init(getApplication());
        GDlocationUtil.getCurrentLocation(new GDlocationUtil.MyLocationListener() {
            @Override
            public void result(AMapLocation location) {
                InitDatas.sLocation=location;
            }
        });
        initTabs();
//        getAppUpdate();
    }



    @SuppressLint("ObsoleteSdkInt")
    private void initTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        MainTab[] tabs = MainTab.values();
        if (ArrayUtils.isEmpty(tabs)) {
            return;
        }

        for (MainTab mainTab : tabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(mainTab.getResName()));
            @SuppressLint("InflateParams") ViewGroup indicator = (ViewGroup) getLayoutInflater().inflate(R.layout.tab_indicator, null, false);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            title.setText(getString(mainTab.getResName()));
            ImageView icon = (ImageView) indicator.findViewById(R.id.tab_icon);
           // icon.getHierarchy().setPlaceholderImage(mainTab.getResIcon());
            icon.setImageResource(mainTab.getResIcon());
            tabSpec.setIndicator(indicator);
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(getApplicationContext());
                }
            });
            mTabHost.addTab(tabSpec, mainTab.getClazz(), null);

        }
        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String s) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_BACK) {
            long current = System.currentTimeMillis();

            if (current - mLastPressBackTime > 2000) {
                mLastPressBackTime = current;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                AppManager.AppExit(me);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    private void getAppUpdate() {
//        OkGo.<String>get(Urls.URL_GET_FIND_PRODUCT_VERSION)
//                .tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Logger.json(response.body());
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });
//
//    }




}
