package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.F1ProductTypeBean;
import com.pingxun.daishangqianbao.utils.GlideImgManager;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * 产品分类adapter Fragment1
 */

public class F1_Type_RecyclerViewAdapter extends BaseQuickAdapter<F1ProductTypeBean.DataBean, BaseViewHolder> {


    public F1_Type_RecyclerViewAdapter(int layoutResId, List<F1ProductTypeBean.DataBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, F1ProductTypeBean.DataBean item) {
      //  Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.tu_jiazai).crossFade().transform(new GlideRoundTransform(mContext,10)).into((ImageView) helper.getView(R.id.iv));
        GlideImgManager.glideLoader(mContext,item.getImg(),R.mipmap.tu_jiazai,R.mipmap.tu_jiazai,(ImageView) helper.getView(R.id.iv),1);
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_sub_title,item.getDescription());

    }



}