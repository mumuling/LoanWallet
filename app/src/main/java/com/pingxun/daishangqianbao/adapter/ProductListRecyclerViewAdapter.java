package com.pingxun.daishangqianbao.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.ProductListBean;
import com.pingxun.daishangqianbao.utils.GlideImgManager;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * 产品列表adapter
 */

public class ProductListRecyclerViewAdapter extends BaseQuickAdapter<ProductListBean.DataBean.ContentBean, BaseViewHolder> {


    public ProductListRecyclerViewAdapter(int layoutResId, List<ProductListBean.DataBean.ContentBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListBean.DataBean.ContentBean item) {
          GlideImgManager.glideLoader(mContext,item.getImg(),R.mipmap.tu_jiazai,R.mipmap.tu_jiazai,(ImageView) helper.getView(R.id.iv),1);
          helper.setText(R.id.tv_title,item.getName());
          helper.setText(R.id.tv_money_range,"额度范围  "+initTvQuota(item.getStartAmount(),item.getEndAmount()));
          helper.setText(R.id.tv_time_range,newStr("期限范围",String.valueOf(item.getStartPeriod()),String.valueOf(item.getEndPeriod()),item.getPeriodType()));
          helper.setText(R.id.tv_interest_rate,item.getPeriodType()+"利率   "+String.valueOf(item.getServiceRate())+"%");

          SpannableString spannableString = new SpannableString("申请人数"+String.valueOf(item.getClickNum())+"人");
          spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 4,spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          helper.setText(R.id.tv_num,spannableString);




    }

    private String newStr(String s1, String s2, String s3, String s4){
        String str=s1+"  "+s2+"-"+s3+s4;
        return str;
    }
    private String initTvQuota(Double start, Double end) {
        String s = String.valueOf((start / 10000)) + "-" + String.valueOf((end / 10000))+"万元";
        return s;
    }

}