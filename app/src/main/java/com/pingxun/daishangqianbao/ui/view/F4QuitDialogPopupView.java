package com.pingxun.daishangqianbao.ui.view;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.basepopupview.BasePopupWindow;
import com.pingxun.daishangqianbao.other.EventMessage;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LH on 2017/8/6.
 * 退出登录Dialog
 */

public class F4QuitDialogPopupView extends BasePopupWindow implements View.OnClickListener{

    private TextView ok;
    private TextView cancel;




    public F4QuitDialogPopupView(Activity context) {
        super(context);
        ok= (TextView) findViewById(R.id.tv_right);
        cancel= (TextView) findViewById(R.id.tv_left);
        setViewClickListener(this,ok,cancel);
    }


    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_quit_login);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set=new AnimationSet(false);
        Animation shakeAnima=new RotateAnimation(0,15,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                dismiss();
                break;
            case R.id.tv_right:
                SharedPrefsUtil.clear(getContext(), InitDatas.SP_NAME);
                EventBus.getDefault().post(new EventMessage("update_UserName"));//event更新用户名
                dismiss();
                break;
            default:

                break;
        }


    }
}
