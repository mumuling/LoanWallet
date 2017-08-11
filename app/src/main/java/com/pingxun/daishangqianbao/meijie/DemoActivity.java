package com.pingxun.daishangqianbao.meijie;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.pingxun.daishangqianbao.meijielib.bridge.BridgeHandler;
import com.pingxun.daishangqianbao.meijielib.bridge.BridgeInterface;
import com.pingxun.daishangqianbao.meijielib.bridge.CallBackFunction;
import com.pingxun.daishangqianbao.meijielib.bridge.DefaultHandler;
import com.pingxun.daishangqianbao.meijielib.bridge.WebViewBridge;
import com.pingxun.daishangqianbao.meijielib.custom.CustomWebView;
import com.pingxun.daishangqianbao.utils.GDlocationUtil;


/**
 * Created by hbl on 2017/4/26.
 */
public class DemoActivity extends AppCompatActivity implements BridgeInterface {
    CustomWebView customWebView;
    private String path;
    private Location location;
    private String urlHost;
    private BridgeHandler handler;
    private AlertDialog dialog;
    private CallBackFunction function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customWebView = new CustomWebView(this);
        setContentView(customWebView);
        WebSettings s = customWebView.getWebView().getSettings();
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存
        urlHost = getIntent().getStringExtra("url");
//        urlHost = "https://static-sit.gomemyf.com/traffic-access/#/testTool?fromChannel=miaobd&fromChannelId=miaobd-a";
//        urlHost = "https://static-sit.gomemyf.com/flow-h5/#/index?fromChannel=miaobd&fromChannelId=miaobd1";
        WebView webView = customWebView.getWebView();
//        WebViewBridge webViewBridge = new WebViewBridge(this);
//        xml注册
        WebViewBridge webViewBridge = new WebViewBridge(this, "webviewhandler");

        //代码注册
        webViewBridge.registerHandler("gome_getgps", new DefaultHandler() {
            @Override
            public void handler(String data, CallBackFunction function, BridgeInterface bridgeInterface) {
                DemoActivity.this.function=function;
                initLocation();
            }
        });

        webViewBridge.registerHandler("gome_getphoto", new PhotoHandler());
        //绑定WebView
        webViewBridge.bindWebView(webView);
        customWebView.getWebView().loadUrl(urlHost);
    }

    /**
     * 利用系统定位，每次获取新的，添加权限检查
     *
     *
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void initLocation() {
        if (Build.VERSION.SDK_INT >=23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("请开启定位权限，以便能及时获取您的位置");
                    builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        }
                    }).create().show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
                return;
            }
            //针对小米手机的权限检查
            int MODE = MIUIUtil.checkAppops(this, AppOpsManager.OPSTR_COARSE_LOCATION);
            if (MODE == MIUIUtil.MODE_ASK) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else if (MODE == MIUIUtil.MODE_IGNORED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请开启定位权限，以便能及时获取您的位置");
                builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    enterSetting();
                        sysPermissionSetting();
                    }
                }).create().show();
            }
        }
        //请使用其他定位方式（百度，高德），系统定位存在问题，在魅族手机上不好使
        //小米系统只有出现这两句的时候才出现权限授予的弹窗
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        if (location == null) {
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                this.location = location;
//            } else {
//                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                if (location != null) {
//                    this.location = location;
//                } else {
//                    function.onCallBack("北京/北京市,39.964806/116.472753");
//                    return;
//                }
//            }
//        } else {
//            this.location = location;
//        }
//        function.onCallBack("北京/北京市," + location.getLatitude() + "/" + location.getLongitude());
        GDlocationUtil.init(getApplication());
        GDlocationUtil.getCurrentLocation(new GDlocationUtil.MyLocationListener() {
            @Override
            public void result(AMapLocation location) {
                function.onCallBack(location.getProvince() + "/" + location.getCity() + "/" + location.getDistrict() + "," + location.getLatitude() + "/" + location.getLongitude());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    void initStorage(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请开启储存权限，以便能存储数据");
                builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);
                    }
                }).create().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 104);
            }
            return;
        }
        //针对小米手机的权限检查
        int MODE = MIUIUtil.checkAppops(this, AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE);
        if (MODE == MIUIUtil.MODE_ASK) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);
        } else if (MODE == MIUIUtil.MODE_IGNORED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("请开启储存权限，以便能存储数据");
            builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    enterSetting();
                    sysPermissionSetting();
                }
            }).create().show();
        }
    }

    /**
     * 权限检查回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean success = grantResults.length > 0;
        String message = "";
        switch (requestCode) {
            case 101:
                message = "请开启定位权限，以便能及时获取您的位置";
                break;
            case 102:
                message = "请开启相机及存储权限，以便能打开摄像头拍照";
                break;
            case 103:
                message = "请开启相机权限，以便能打开相机";
                break;
            case 104:
                message = "请开启存储权限，以便能保存照片";
                break;
        }
        for (int i : grantResults) {
            success = success && i == PackageManager.PERMISSION_GRANTED;
        }
        if (success) {
            if (requestCode == 101) {
                int MODE = MIUIUtil.checkAppops(this, AppOpsManager.OPSTR_COARSE_LOCATION);
                if (MODE == MIUIUtil.MODE_ASK) {
                    //写着两句是为了出现权限弹窗，小米系统伤不起啊。。
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (MODE == MIUIUtil.MODE_IGNORED) {
                    showDialog(message);
                } else {
                    Toast.makeText(this, "权限授予成功", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "权限授予成功", Toast.LENGTH_LONG).show();
            }
        } else {
            showDialog(message);
        }
    }

    private void showDialog(String message) {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            dialog = builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    enterSetting();
                }
            }).create();
        }
        dialog.setMessage(message);
        dialog.show();
    }

    /**
     * 跳转至当前应用的设置界面
     */
    private void enterSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(localIntent);
    }

    /**
     * 系统设置
     */
    private void sysPermissionSetting(){
        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    /**
     * 拍照回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (handler != null) {
            handler.onActivityResult(requestCode, resultCode, data);
            handler = null;
        }
    }


    @Override
    public void startActivityForResult(BridgeHandler handler, Intent intent, int requestCode) {
        this.handler = handler;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}