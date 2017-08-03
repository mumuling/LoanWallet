package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.ProductListBean;

import java.util.List;

/**
 * Created by Administrator on 2017-03-08.
 */


public class ProductRecycViewAdapter extends BaseQuickAdapter<ProductListBean.DataBean.ContentBean, BaseViewHolder> {


    public ProductRecycViewAdapter(int layoutResId, List<ProductListBean.DataBean.ContentBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListBean.DataBean.ContentBean item) {
          helper.setText(R.id.tv_title,item.getName());
          helper.setText(R.id.tv_money_range,newStr("借款额度",String.valueOf(item.getStartAmount()),String.valueOf(item.getEndAmount()),"元"));
          helper.setText(R.id.tv_time_range,newStr("期限范围",String.valueOf(item.getStartPeriod()),String.valueOf(item.getEndPeriod()),item.getPeriodType()));
          helper.setText(R.id.tv_interest_rate,item.getPeriodType()+"利率   "+String.valueOf(item.getServiceRate())+"%");
          Glide.with(mContext).load(item.getImg()).crossFade().into((ImageView) helper.getView(R.id.iv));
    }

    public String newStr(String s1,String s2,String s3,String s4){
        String str=s1+"  "+s2+"-"+s3+s4;
        return str;
    }

}