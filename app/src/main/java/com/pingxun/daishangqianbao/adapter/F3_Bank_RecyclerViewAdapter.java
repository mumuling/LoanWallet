package com.pingxun.daishangqianbao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.data.BankListBean;

import java.util.List;

/**
 * Created by LH on 2017/8/4.
 * 信用卡Icon Adapter(九宫格)
 */

public class F3_Bank_RecyclerViewAdapter extends BaseQuickAdapter<BankListBean.DataBean,BaseViewHolder>{

    public F3_Bank_RecyclerViewAdapter(int layoutResId, List<BankListBean.DataBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }
    @Override
    protected void convert(BaseViewHolder helper, BankListBean.DataBean item) {
              helper.setText(R.id.tv_icon,item.getName());
    }
}
