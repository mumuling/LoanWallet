package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.ProductInfoBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.view.EmptyLayout;
import com.pingxun.daishangqianbao.ui.view.IsApplyDialogPopupView;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.GlideImgManager;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    @BindView(R.id.parent_view)
    LinearLayout mParentView;
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;


    private String sId;//产品ID
    private static final int GET_FIND_BY_ID = 1;//获取产品详情
    private static final int POST_APPLY = 2;//立即申请

    private String mWebUrls;
    private String mNameStr;

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
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            update();
        }
    }

    @Override
    public void onResult(String jsonStr, int flag) {
        mEmptyLayout.setErrorType(EmptyLayout.NO_ERROR);
        mParentView.setVisibility(View.VISIBLE);
        switch (flag) {
            case GET_FIND_BY_ID:
                Logger.json(jsonStr);
                ProductInfoBean mProductInfoBean = Convert.fromJson(jsonStr, ProductInfoBean.class);
                if (mProductInfoBean == null || !mProductInfoBean.isSuccess()) {
                    ToastUtils.showToast(me, "获取产品详情失败");
                    return;
                }
                if (mProductInfoBean.isSuccess()) {
                    mNameStr = mProductInfoBean.getData().getName();
                    GlideImgManager.glideLoader(me, mProductInfoBean.getData().getDetailImg(), R.mipmap.tu_jiazai, R.mipmap.tu_jiazai, mIvProduct, 1);
                    //Glide.with(me).load(mProductInfoBean.getData().getDetailImg()).crossFade().transform(new GlideRoundTransform(me, 30)).into(mIvProduct);
                    mTvTitle.setText(mNameStr);//产品名称
                    mTvQuota.setText("额度范围   " + initTvQuota(mProductInfoBean.getData().getStartAmount(), mProductInfoBean.getData().getEndAmount()));
                    mTvTime.setText("额度期限   " + mProductInfoBean.getData().getStartPeriod() + "~" + mProductInfoBean.getData().getEndPeriod() + mProductInfoBean.getData().getPeriodTypeStr());
                    mTvSpeed.setText("最快放款   " + mProductInfoBean.getData().getLoanDay() + "天");
                    mTvRateExplain.setText(mProductInfoBean.getData().getRateMemo());
                    mTvProcess.setText(mProductInfoBean.getData().getApplyFlow());
                    mTvCondition.setText(mProductInfoBean.getData().getApplyCondition());
                    mTvDatum.setText(mProductInfoBean.getData().getApplyMaterial());
                    mWebUrls = mProductInfoBean.getData().getUrl();

                    break;
                }

                break;
            case POST_APPLY:
//                Logger.json(jsonStr);
                break;

        }

    }

    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(me)==-1) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    private String initTvQuota(Double start, Double end) {
        String newStr = String.valueOf((start / 10000)) + "-" + String.valueOf((end / 10000)) + "万元";
        return newStr;
    }

    /**
     * 数据埋点
     */
    private void postPoint() {
        //手机型号
        String model = Build.MODEL;
        //设备厂商
        String carrier = Build.MANUFACTURER;

        Map<String, String> params = new HashMap<String, String>();
        params.put("productId", sId);
        params.put("deviceNumber", model + "(" + carrier + ")");
        params.put("applyArea", InitDatas.sLocation.getLatitude() + "；" + InitDatas.sLocation.getLongitude());
        params.put("channelNo", InitDatas.CHANNEL_NO);
        params.put("appName", InitDatas.APP_NAME);
        JSONObject jsonObj = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_APPLY_LOAN, jsonObj, POST_APPLY);

    }


    @OnClick({R.id.btn_enter, R.id.empty_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_enter://立即申请
                postPoint();
                IsApplyDialogPopupView isApplyDialogPopupViewView = new IsApplyDialogPopupView(me, sId);
                isApplyDialogPopupViewView.setPopupWindowFullScreen(true);
                isApplyDialogPopupViewView.showPopupWindow();
                Bundle bundle = new Bundle();
                bundle.putString("url", mWebUrls);
                bundle.putString("productName", mNameStr);

                if (mWebUrls.contains("jie.gomemyf.com")) {
                    ToastUtils.showToast(me, "jie.gomemyf.com");
                } else {
                    ActivityUtil.goForward(me, WebViewActivity.class, bundle, false);
                }
                break;
            case R.id.empty_layout://重连
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                update();
                break;

        }
    }

    private void update() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getInfo();
                    }
                });

    }

    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("id", sId);
        G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_GET_FIND_BY_ID,params, GET_FIND_BY_ID);
    }

}
