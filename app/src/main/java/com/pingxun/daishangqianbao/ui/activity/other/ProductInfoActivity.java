package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.ProductInfoBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.GlideRoundTransform;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 产品详情页
 */
public class ProductInfoActivity extends BaseActivity implements G_api.OnResultHandler {

    @BindView(R.id.iv_product)
    ImageView mIvProduct;//产品图片
    @BindView(R.id.tv_title)
    TextView mTvTitle;//产品标题
    @BindView(R.id.tv_quota)
    TextView mTvQuota;//额度范围
    @BindView(R.id.tv_time)
    TextView mTvTime;//额度期限
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;//最快放款
    @BindView(R.id.tv_rate_explain)
    TextView mTvRateExplain;//利率说明
    @BindView(R.id.tv_process)
    TextView mTvProcess;//申请流程
    @BindView(R.id.tv_condition)
    TextView mTvCondition;//申请条件
    @BindView(R.id.tv_datum)
    TextView mTvDatum; //申请材料
    @BindView(R.id.btn_enter)
    Button mBtnEnter;//立即借款

    private ProductInfoBean mProductInfoBean;
    private String sId;
    private static final int GET_FIND_BY_ID = 1;//获取产品详情

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_info;
    }

    @Override
    protected void initData() {
        initTopView("产品详情");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sId = bundle.getString(InitDatas.PROUDUCT_ID);
            Map<String, String> params = new HashMap<>();

            params.put("id", sId);
            G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_GET_FIND_BY_ID, this, params, GET_FIND_BY_ID);
        }
    }

    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag) {
            case GET_FIND_BY_ID:
                mProductInfoBean = Convert.fromJson(jsonStr, ProductInfoBean.class);

                if (mProductInfoBean == null || !mProductInfoBean.isSuccess()) {
                    ToastUtils.showToast(me, "获取产品详情失败");
                    return;
                }
                if (mProductInfoBean.isSuccess()) {
                    Glide.with(me).load(mProductInfoBean.getData().getDetailImg()).crossFade().transform(new GlideRoundTransform(me, 30)).into(mIvProduct);
                    mTvTitle.setText(mProductInfoBean.getData().getName());//产品名称
                    mTvQuota.setText("额度范围   "+initTvQuota(mProductInfoBean.getData().getStartAmount(),mProductInfoBean.getData().getEndAmount()));
                    mTvTime.setText("额度期限   "+mProductInfoBean.getData().getStartPeriod()+"~"+mProductInfoBean.getData().getEndPeriod()+mProductInfoBean.getData().getPeriodTypeStr());
                    mTvSpeed.setText("最快放款   "+mProductInfoBean.getData().getLoanDay()+"天");
                    mTvRateExplain.setText(mProductInfoBean.getData().getRateMemo());
                    mTvProcess.setText(mProductInfoBean.getData().getApplyFlow());
                    mTvCondition.setText(mProductInfoBean.getData().getApplyCondition());
                    mTvDatum.setText(mProductInfoBean.getData().getApplyMaterial());

                    break;
                }

                break;
        }

    }

    @Override
    public void onError(int flag) {

    }


    public String initTvQuota(Double start, Double end) {
        String s = String.valueOf((start / 10000)) + "-" + String.valueOf((end / 10000))+"万元";
        return s;
    }


    @OnClick(R.id.btn_enter)
    public void onViewClicked() {
        ToastUtils.showToast(me, "点击了立即申请按钮!");

    }

}
