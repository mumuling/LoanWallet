package com.pingxun.daishangqianbao.ui.activity.common;


import android.content.Intent;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.service.LoginService;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        Intent serviceIn=new Intent(this, LoginService.class);
        startService(serviceIn);


        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (!SharedPrefsUtil.getValue(me, InitDatas.SP_NAME, InitDatas.UserIsFirstSplash, false)){
                            ActivityUtil.goForward(me,GuidActivity.class,null,true);}
                        if (SharedPrefsUtil.getValue(me, InitDatas.SP_NAME, InitDatas.UserIsFirstSplash, false)){
                            ActivityUtil.goForward(me,MainActivity.class,null,true);
                        }
                    }
                });
    }
}
