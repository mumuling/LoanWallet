package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.F3CardListBean;
import com.pingxun.daishangqianbao.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * F3信用卡列表
 */

public class F3_Card_RecyclerViewAdapter extends BaseQuickAdapter<F3CardListBean.DataBean.ContentBean, BaseViewHolder> {


    public F3_Card_RecyclerViewAdapter(int layoutResId, List<F3CardListBean.DataBean.ContentBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, F3CardListBean.DataBean.ContentBean item) {
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).crossFade().transform(new GlideRoundTransform(mContext,10)).into((ImageView) helper.getView(R.id.iv));

        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_sub_title,item.getCardDesc());
        helper.setText(R.id.tv_num,"申请人数"+String.valueOf(item.getClickNum()));
    }



}