package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.F1ProductTypeBean;
import com.pingxun.daishangqianbao.utils.GlideRoundTransform;

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
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).crossFade().transform(new GlideRoundTransform(mContext,10)).into((ImageView) helper.getView(R.id.iv));

        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_sub_title,item.getDescription());

    }



}