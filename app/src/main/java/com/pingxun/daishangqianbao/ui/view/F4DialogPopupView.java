package com.pingxun.daishangqianbao.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.basepopupview.BasePopupWindow;

/**
 * Created by LH on 2017/8/6.
 * 拨打电话Dialog
 */

public class F4DialogPopupView extends BasePopupWindow implements View.OnClickListener{

    private TextView ok;
    private TextView cancel;




    public F4DialogPopupView(Activity context) {
        super(context);
        ok= (TextView) findViewById(R.id.tv_right);
        cancel= (TextView) findViewById(R.id.tv_left);
        setViewClickListener(this,ok,cancel);
    }


    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_call_phone);
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
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-400-8089"));
                getContext().startActivity(phoneIntent);
                break;
            default:

                break;
        }


    }
}
