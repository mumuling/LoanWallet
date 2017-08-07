package com.pingxun.daishangqianbao.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.F3CardListBean;
import com.pingxun.daishangqianbao.utils.GlideImgManager;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * F3推荐信用卡列表Adapter
 */

public class F3_Card_RecyclerViewAdapter extends BaseQuickAdapter<F3CardListBean.DataBean.ContentBean, BaseViewHolder> {


    public F3_Card_RecyclerViewAdapter(int layoutResId, List<F3CardListBean.DataBean.ContentBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, F3CardListBean.DataBean.ContentBean item) {
        GlideImgManager.glideLoader(mContext,item.getImg(),R.mipmap.tu_jiazai,R.mipmap.tu_jiazai,(ImageView)helper.getView(R.id.iv),1);

        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_sub_title,item.getCardDesc());
        SpannableString spannableString = new SpannableString("申请人数"+String.valueOf(item.getClickNum())+"人");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 4,spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        helper.setText(R.id.tv_num, spannableString);
    }



}