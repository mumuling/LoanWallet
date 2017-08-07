package com.pingxun.daishangqianbao.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.F1ProductRecommendBean;
import com.pingxun.daishangqianbao.utils.GlideImgManager;

import java.util.List;

/**
 * Created by LH on 2017/8/4.
 * 首页产品推荐adapter
 */

public class F1_Recommend_RecyclerViewAdapter extends BaseQuickAdapter<F1ProductRecommendBean.DataBean,BaseViewHolder>{

    public F1_Recommend_RecyclerViewAdapter(int layoutResId, List<F1ProductRecommendBean.DataBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }
    @Override
    protected void convert(BaseViewHolder helper, F1ProductRecommendBean.DataBean item) {
        GlideImgManager.glideLoader(mContext,item.getImg(),R.mipmap.tu_jiazai,R.mipmap.tu_jiazai,(ImageView) helper.getView(R.id.iv),1);
        helper.setText(R.id.tv_title,item.getName());
    }
}
