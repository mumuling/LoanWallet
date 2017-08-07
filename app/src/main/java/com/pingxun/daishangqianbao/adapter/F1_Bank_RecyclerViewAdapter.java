package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.BankListBean;
import com.pingxun.daishangqianbao.utils.GlideImgManager;

import java.util.List;

/**
 * Created by LH on 2017/8/4.
 * 首页办卡专区adapter
 */

public class F1_Bank_RecyclerViewAdapter extends BaseQuickAdapter<BankListBean.DataBean,BaseViewHolder>{

    public F1_Bank_RecyclerViewAdapter(int layoutResId, List<BankListBean.DataBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }
    @Override
    protected void convert(BaseViewHolder helper, BankListBean.DataBean item) {
        GlideImgManager.glideLoader(mContext,item.getIcon(),R.mipmap.tu_jiazai,R.mipmap.tu_jiazai,(ImageView) helper.getView(R.id.iv),1);
        helper.setText(R.id.tv_title,item.getName());
    }
}
