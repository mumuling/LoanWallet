package com.pingxun.daishangqianbao.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.ApplyListBean;
import com.pingxun.daishangqianbao.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * 申请列表adapter
 */

public class ApplyListRecyclerViewAdapter extends BaseQuickAdapter<ApplyListBean.DataBean.ContentBean, BaseViewHolder> {


    public ApplyListRecyclerViewAdapter(int layoutResId, List<ApplyListBean.DataBean.ContentBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }



    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean.DataBean.ContentBean item) {
          helper.setText(R.id.tv_title,item.getProductName());
          helper.setText(R.id.tv_money,initTvQuota(item.getStartAmount(),item.getEndAmount()));
          helper.setText(R.id.tv_time,String.valueOf(item.getStartPeriod())+"~"+String.valueOf(item.getEndPeriod())+item.getPeriodType());
          helper.setText(R.id.tv_lilv,item.getPeriodType()+"利率");
          helper.setText(R.id.tv_rate,String.valueOf(item.getServiceRate())+"%");
          Glide.with(mContext).load(item.getImg()).crossFade().transform(new GlideRoundTransform(mContext,10)).into((ImageView) helper.getView(R.id.iv));
    }

    private String newStr(String s1, String s2, String s3, String s4){
        String str=s1+"  "+s2+"-"+s3+s4;
        return str;
    }
    private String initTvQuota(Double start, Double end) {
        SpannableString spannableString=new SpannableString("万元");
        RelativeSizeSpan sizeSpan=new RelativeSizeSpan(0.1f);
        spannableString.setSpan(sizeSpan,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        String s = String.valueOf((start / 10000)) + "-" + String.valueOf((end / 10000))+ "万元";
        return s;
    }

}