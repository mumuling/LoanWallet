package com.pingxun.daishangqianbao.ui.activity.common;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.LoginBean;
import com.pingxun.daishangqianbao.other.EventMessage;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.view.XEditText;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.CountDownUtil;
import com.pingxun.daishangqianbao.utils.MyTools;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements G_api.OnResultHandler{
    @BindView(R.id.ed_phone) XEditText mEdPhone;//手机号输入框
    @BindView(R.id.ed_code) XEditText mEdCode;//获取验证码输入框
    @BindView(R.id.tv_get_code) TextView mTvGetCode;//获取验证码
    @BindView(R.id.btn_login) Button mBtnLogin;//登录
    @BindView(R.id.iv_choose) ImageView mIvChoose;//是否同意协议
    @BindView(R.id.tv_agreement) TextView mTvAgreement;//协议

    private boolean isAgree = true;//是否同意协议标识
    private String sCode;//验证码
    private String sPhone;//电话号码

    private static final int GET_CODE = 111;//获取验证码
    private static final int LOGIN=222;//登录
    private LoginBean mLoginBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        initTopView("登录");

    }

    @OnClick({R.id.tv_get_code, R.id.btn_login, R.id.iv_choose, R.id.tv_agreement})
    public void onViewClicked(View view) {
        sPhone = MyTools.getEdittextStr(mEdPhone);
        sCode = MyTools.getEdittextStr(mEdCode);
        switch (view.getId()) {
            case R.id.tv_get_code://获取验证码
                if (!MyTools.isEmpty(sPhone)){
                    ToastUtils.showToast(me,"手机号不能为空!");
                    return;
                }
                if (!MyTools.isMobileNO(sPhone)) {
                    ToastUtils.showToast(mApp, "输入的手机格式不正确");
                    return;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", sPhone);
                params.put("channelNo", InitDatas.CHANNEL_NO);
                params.put("appName", InitDatas.APP_NAME);
                JSONObject jsonObject = new JSONObject(params);

                G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_SEND_SMS,this,jsonObject,GET_CODE);
                CountDownUtil countDownUtil = new CountDownUtil(60000, 1000, mTvGetCode);
                countDownUtil.start();
                break;

            case R.id.btn_login://登录

                if (!MyTools.isEmpty(sPhone)){
                    ToastUtils.showToast(me,"手机号不能为空!");
                    break;
                }
                if (!MyTools.isEmpty(sCode)){
                    ToastUtils.showToast(me,"验证码不能为空!");
                    break;
                }
                if (!MyTools.isMobileNO(sPhone)) {
                    ToastUtils.showToast(mApp, "输入的手机格式不正确");
                    break;
                }

                HashMap<String, String> params2 = new HashMap<>();
                params2.put("userName", sPhone);
                params2.put("password", sCode);
                params2.put("appName", InitDatas.APP_NAME);
                JSONObject loginJsonObject = new JSONObject(params2);

                G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_LOGIN,this,loginJsonObject,LOGIN);

                break;

            case R.id.iv_choose://同意协议选项
                if (isAgree) {
                    mIvChoose.setBackgroundResource(R.mipmap.icon_agree_no);
                    mBtnLogin.setEnabled(false);
                    mBtnLogin.setBackgroundResource(R.drawable.dis_cornerbottom);
                    isAgree = false;
                } else {
                    mIvChoose.setBackgroundResource(R.mipmap.icon_agree_yes);
                    mBtnLogin.setEnabled(true);
                    mBtnLogin.setBackgroundResource(R.drawable.cornerbottom);
                    isAgree = true;
                }
                break;


            case R.id.tv_agreement://注册协议跳转

                break;
        }
    }



    @Override
    public void onResult(String jsonStr, int flag) {
         switch (flag){

             case GET_CODE://获取验证码回调
                 mLoginBean= Convert.fromJson(jsonStr,LoginBean.class);
                 if (mLoginBean==null||!mLoginBean.isSuccess()){
                     ToastUtils.showToast(me,"获取验证码失败");
                     break;
                 }
                 if (mLoginBean.isSuccess()){
                     ToastUtils.showToast(me,"获取验证码成功，请注意查收短信!");
                     break;
                 }
                 break;


             case LOGIN://登录回调
                 mLoginBean= Convert.fromJson(jsonStr,LoginBean.class);
                 if (mLoginBean==null||!mLoginBean.isSuccess()){
                     ToastUtils.showToast(me,"登录失败");
                     return;
                 }
                 if (mLoginBean.isSuccess()){
                     ToastUtils.showToast(me,"登录成功");
                     SharedPrefsUtil.putValue(me,InitDatas.SP_NAME,InitDatas.UserPhone,sPhone);
                     SharedPrefsUtil.putValue(me,InitDatas.SP_NAME,InitDatas.UserPw,sCode);
                     SharedPrefsUtil.putValue(me,InitDatas.SP_NAME,InitDatas.UserIsLogin,true);
                     EventBus.getDefault().post(new EventMessage("update_UserName"));//event更新用户名
                     closeActivtiy();
                     break;
                 }
                 break;

         }
    }

    @Override
    public void onError(int flag) {

    }
}
