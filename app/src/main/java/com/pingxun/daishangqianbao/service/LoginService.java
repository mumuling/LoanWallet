package com.pingxun.daishangqianbao.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.data.LoginBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by LH on 2017/8/7.
 * 后台登录服务
 */

public class LoginService extends Service implements G_api.OnResultHandler{

    private static final int LOGIN=222;//登录
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("======onCreate=====");
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", SharedPrefsUtil.getValue(this,InitDatas.SP_NAME,InitDatas.UserPhone,""));
        params.put("password", SharedPrefsUtil.getValue(this,InitDatas.SP_NAME,InitDatas.UserPw,""));
        params.put("appName", InitDatas.APP_NAME);
        JSONObject loginJsonObject = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_LOGIN,loginJsonObject,LOGIN);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("======onStartCommand=====");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("======onDestroy=====");
    }

    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag){
            case LOGIN:

                LoginBean mLoginBean= Convert.fromJson(jsonStr,LoginBean.class);
                if (mLoginBean==null||!mLoginBean.isSuccess()){
                    SharedPrefsUtil.putValue(this,InitDatas.SP_NAME,InitDatas.UserIsLogin,false);
                    return;
                }
                if (mLoginBean.isSuccess()){
                    SharedPrefsUtil.putValue(this,InitDatas.SP_NAME,InitDatas.UserIsLogin,true);
                }
                break;
        }
    }

    @Override
    public void onError(int flag) {

    }
}
