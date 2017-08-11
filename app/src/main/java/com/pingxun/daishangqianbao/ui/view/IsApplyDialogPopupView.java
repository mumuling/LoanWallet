package com.pingxun.daishangqianbao.ui.view;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.basepopupview.BasePopupWindow;
import com.pingxun.daishangqianbao.data.BaseBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LH on 2017/8/6.
 * 小调查是否申请Dialog
 */

public class IsApplyDialogPopupView extends BasePopupWindow implements View.OnClickListener,G_api.OnResultHandler{

    private TextView ok;
    private TextView cancel;
    private LinearLayout close;
    private String id;
    private static final int APPLY_TRUE=1;//已申请标识
    private static final int APPLY_FALSE=2;//未申请标识



    public IsApplyDialogPopupView(Activity context,String idStr) {
        super(context);
        close=(LinearLayout)findViewById(R.id.iv_close);
        ok= (TextView) findViewById(R.id.tv_right);
        cancel= (TextView) findViewById(R.id.tv_left);
        id=idStr;
        setViewClickListener(this,ok,cancel,close);
    }


    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_investigation);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }


    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:

                dismiss();

                break;
            case R.id.tv_left:

                Map<String, String> params = new HashMap<>();
                params.put("productId",id);
                params.put("isApply","false");
                JSONObject jsonObject=new JSONObject(params);
                G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_IS_APPLY, jsonObject, APPLY_FALSE);

                break;
            case R.id.tv_right:

                Map<String, String> params2 = new HashMap<>();
                params2.put("productId",id);
                params2.put("isApply","true");
                JSONObject jsonObject1=new JSONObject(params2);
                G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_IS_APPLY, jsonObject1, APPLY_TRUE);

                break;

            default:

                break;
        }


    }

    @Override
    public void onResult(String jsonStr, int flag) {
       switch (flag){
           case APPLY_FALSE:
               BaseBean baseBean= Convert.fromJson(jsonStr,BaseBean.class);
               if (baseBean==null||!baseBean.isSuccess()){
                   ToastUtils.showToast(getContext(),"提交失败!");
               }
               if (baseBean.isSuccess()){
                   ToastUtils.showToast(getContext(),"提交成功!");
               }
               dismiss();
               break;
           case APPLY_TRUE:
               BaseBean baseBean2= Convert.fromJson(jsonStr,BaseBean.class);
               if (baseBean2==null||!baseBean2.isSuccess()){
                   ToastUtils.showToast(getContext(),"提交失败!");
               }
               if (baseBean2.isSuccess()){
                   ToastUtils.showToast(getContext(),"提交成功!");
               }
               dismiss();
               break;

       }


    }

    @Override
    public void onError(int flag) {
        Logger.d(flag);
    }
}
