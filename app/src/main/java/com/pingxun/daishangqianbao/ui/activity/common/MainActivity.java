package com.pingxun.daishangqianbao.ui.activity.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
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
import com.pingxun.daishangqianbao.utils.ArrayUtils;
import com.pingxun.daishangqianbao.utils.GDlocationUtil;

import butterknife.BindView;


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
//                location.getAddress();
                location.getErrorInfo();
            }
        });
        initTabs();

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int p0 = ContextCompat.checkSelfPermission(this, permissions[0]);
            int p1 = ContextCompat.checkSelfPermission(this, permissions[1]);
            int p2 = ContextCompat.checkSelfPermission(this, permissions[2]);
            int p3 = ContextCompat.checkSelfPermission(this, permissions[3]);
            int p4 = ContextCompat.checkSelfPermission(this, permissions[4]);
            int p5 = ContextCompat.checkSelfPermission(this, permissions[5]);
            int p = PackageManager.PERMISSION_GRANTED;
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (p0 != p || p1 != p || p2 != p || p3 != p || p4 != p||p5 != p) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
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
//                MasterManager.getInstance().stopApp();
               closeActivtiy();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("由于带上钱包需要获取存储空间，地理位置等权限；\n否则，您将无法正常使用我们的产品!")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }


    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b0 = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b0) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting("存储");
                    } else
                        finish();
                } else if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    boolean b2 = shouldShowRequestPermissionRationale(permissions[2]);
                    if (!b2) {
                        showDialogTipUserGoToAppSettting("定位");
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting(String permissionsName) {

        new AlertDialog.Builder(this)
                .setTitle(permissionsName + "权限不可用")
                .setMessage("请在-应用设置-权限-中，允许准点+使用" + permissionsName + "权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }


}
