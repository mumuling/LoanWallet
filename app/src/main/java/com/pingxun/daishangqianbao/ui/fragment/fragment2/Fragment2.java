package com.pingxun.daishangqianbao.ui.fragment.fragment2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.F2JobDataBean;
import com.pingxun.daishangqianbao.data.F2ParamBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.other.ProductListActivity;
import com.pingxun.daishangqianbao.ui.view.EmptyLayout;
import com.pingxun.daishangqianbao.ui.view.RulerView;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by LH on 2017/7/31.
 * 精准主页
 */

public class Fragment2 extends BaseFragment implements G_api.OnResultHandler, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;//借款金额

    @BindView(R.id.btn_day_or_month)
    ImageButton btnDayOrMonth;//借款单位选择，日或月
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;//借款开始时间
    @BindView(R.id.seekBar)
    SeekBar seekBar;//进度条
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;//借款结束时间
    @BindView(R.id.tv_hkqx)
    TextView tvHkqx;//还款期限
    @BindView(R.id.tv_mqyh)
    TextView tvMqyh;//每期应还
    @BindView(R.id.lin_job_choose)
    LinearLayout linJobChoose;//职业选择
    @BindView(R.id.btn_search)
    Button btnSearch;//搜索按钮
    @BindView(R.id.rulerView)
    RulerView rulerView;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.parent_view)
    LinearLayout mParentView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.homePageContentScroll)
    ScrollView mHomePageContentScroll;



    private boolean isDay = true;//借款单位是否是日单位
    private String cycleStr = "";//借款周期需要传的参数:M or D
    private String termStr = "";//期限参数


    private static final int URL_GET_FIND_PARAMETER = 5;//获取贷款参数标识
    private static final int URL_GET_FIND_BY_TYPE = 6;//获取职业标识
    private ArrayList<String> stringArrayList;

    private OptionsPickerView optionsPickerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_2);
    }

    @Override
    protected void onInitView(View rootView) {
        super.onInitView(rootView);
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        rulerView.setValue(13000.0f, 0.0f, 200000.0f, 1000.0f);//设置选中值、最小值、最大值、单位值
    }

    @Override
    protected void onLoadData(Bundle savedInstanceState) {
        super.onLoadData(savedInstanceState);
        onRefresh();
    }

    //    @Override
//    protected int getRootLayoutResID() {
//        return R.layout.fragment_2;
//    }
//
//    @Override
//    protected void initData() {
//        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
//        mSwipeLayout.setOnRefreshListener(this);
//        rulerView.setValue(13000.0f, 0.0f, 200000.0f, 1000.0f);//设置选中值、最小值、最大值、单位值
//
//        onRefresh();
//    }

    @OnClick({R.id.btn_day_or_month, R.id.lin_job_choose, R.id.btn_search, R.id.empty_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_day_or_month://切换还款单位
                if (isDay) {//天
                    btnDayOrMonth.setBackgroundResource(R.mipmap.icon_date_month);
                    InitDatas.loanUnit = 1;
                    isDay = false;
                    limitDateUIThread(InitDatas.mounthStart, InitDatas.mounthEnd);
                } else {//月
                    btnDayOrMonth.setBackgroundResource(R.mipmap.icon_date_day);
                    InitDatas.loanUnit = 0;
                    isDay = true;
                    limitDateUIThread(InitDatas.dayStart, InitDatas.dayEnd);
                }
                calculateRepayInfo(InitDatas.progressData);

                break;
            case R.id.lin_job_choose://职业选择
                optionsPickerView = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvJob.setText(stringArrayList.get(options1));
                    }
                }).build();
                optionsPickerView.setPicker(stringArrayList);
                optionsPickerView.show();
                break;
            case R.id.btn_search://搜索

//              Log.e("期限==>>", InitDatas.loanDate + "");
//              Log.e("期限周期==>>", cycleStr);
//              Log.e("借款金额==>>", InitDatas.loanAmount + "");

//              HashMap<String, String> params = new HashMap<>();
//              params.put("period", InitDatas.loanDate+"");//期限
//              params.put("dateCycle", cycleStr);//期限周期
//              params.put("amount", InitDatas.loanAmount + "");//借款金额
//              params.put("channelNo", "android");//渠道类型：ios,android,wechat
//              params.put("appName", InitDatas.APP_NAME);

                ActivityUtil.goForward(mActivity, ProductListActivity.class, null, false);
                break;
            case R.id.empty_layout:
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                onRefresh();
                break;
        }
    }


    @Override
    public void onResult(String jsonStr, int flag) {
        mEmptyLayout.setErrorType(EmptyLayout.NO_ERROR);
        mParentView.setVisibility(View.VISIBLE);
        switch (flag) {
            case URL_GET_FIND_PARAMETER://获取贷款参数
                F2ParamBean mF2ParamBean = Convert.fromJson(jsonStr, F2ParamBean.class);
                if (mF2ParamBean == null || !mF2ParamBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取贷款参数失败!");
                    return;
                }
                if (mF2ParamBean.isSuccess()) {
                    // 贷款最低金额
                    InitDatas.limitLowAmount = new BigDecimal(mF2ParamBean.getData().getStartAmount() + "").intValue();
                    //贷款最高金额
                    InitDatas.limitHeightAmount = new BigDecimal(mF2ParamBean.getData().getEndAmount() + "").intValue();
                    //日 单位 起始
                    InitDatas.dayStart = mF2ParamBean.getData().getStartPeriodDay() + "天";
                    //日 单位 结束
                    InitDatas.dayEnd = mF2ParamBean.getData().getEndPeriodDay() + "天";
                    //月 单位 开始
                    InitDatas.mounthStart = mF2ParamBean.getData().getStartPeriodMonth() + "月";
                    //月 单位 结束
                    InitDatas.mounthEnd = mF2ParamBean.getData().getEndPeriodMonth() + "月";
                    //年利率
                    InitDatas.rateYear = BigDecimal.valueOf(mF2ParamBean.getData().getLoanRate());
                    //日利率
                    InitDatas.rateDay = InitDatas.rateYear.divide(new BigDecimal(100)).divide(new BigDecimal(360));
                    //月利率
                    InitDatas.rateMounth = InitDatas.rateDay.multiply(new BigDecimal(30));
//                    Log.e("贷款最低金额==>>", InitDatas.limitLowAmount + "");
//                    Log.e("贷款最高金额==>>", InitDatas.limitHeightAmount + "");
//                    Log.e("日 单位 起始==>>", InitDatas.dayStart);
//                    Log.e("日 单位 结束==>>", InitDatas.dayEnd);
//                    Log.e("月 单位 开始==>>", InitDatas.mounthStart);
//                    Log.e("月 单位 结束==>>", InitDatas.mounthEnd);
//                    Log.e("年利率==>>", String.valueOf(InitDatas.rateYear));
//                    Log.e("日利率==>>", String.valueOf(InitDatas.rateDay));
//                    Log.e("月利率==>>", String.valueOf(InitDatas.rateMounth));
                    runinUI(InitDatas.loanDate);
                }
                break;

            case URL_GET_FIND_BY_TYPE://获取职业集合
                F2JobDataBean mBean = Convert.fromJson(jsonStr, F2JobDataBean.class);
                if (mBean == null || !mBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取职业失败!");
                    return;
                }
                if (mBean.isSuccess()) {
                    List<F2JobDataBean.DataBean> mList = mBean.getData();
                    stringArrayList = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        stringArrayList.add(mList.get(i).getName());
                    }
                }
                break;

        }
    }

    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(mActivity) == -1) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    /**
     * 更新UI
     *
     * @param loanDate 贷款期限
     */
    private void runinUI(int loanDate) {

        tvStartTime.setText(InitDatas.dayStart);
        tvEndTime.setText(InitDatas.dayEnd);
        tvTotalMoney.setText("￥ " + rulerView.getCurrLocation());
        InitDatas.loanAmount = rulerView.getCurrLocation();
        InitDatas.loanDate = loanDate;
        calculateRepayInfo(0);

        rulerView.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
//              mHomePageContentScroll.requestDisallowInterceptTouchEvent(true);
                tvTotalMoney.setText("¥ " + (int) value + "");
                InitDatas.loanAmount = (int) value;
                calculateRepayInfo(InitDatas.progressData);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                calculateRepayInfo(progress);
                InitDatas.progressData = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    /**
     * 计算还款信息
     *
     * @param progress
     */
    private void calculateRepayInfo(int progress) {
        //还款单位 0 天 1 月
        int loanUnit = InitDatas.loanUnit;
        String loanUnitStr = "天";
        //最低期限和最高期限
        int limitLow = 0;
        int limitHeight = 0;
        if (loanUnit == 0) {
            limitLow = Integer.parseInt(InitDatas.dayStart.replaceAll("天", "").trim());
            limitHeight = Integer.parseInt(InitDatas.dayEnd.replaceAll("天", "").trim());
            loanUnitStr = "天";
        } else {
            limitLow = Integer.parseInt(InitDatas.mounthStart.replaceAll("月", "").trim());
            limitHeight = Integer.parseInt(InitDatas.mounthEnd.replaceAll("月", "").trim());
            loanUnitStr = "月";
        }
        //贷款期限
        Float loanTimefloat = limitLow + (limitHeight - limitLow) * (progress / 100F);
        int loanTimeInt = loanTimefloat.intValue();
        InitDatas.loanDate = loanTimeInt;
        BigDecimal loanTimeBigDecimal = new BigDecimal(loanTimeInt).setScale(0, BigDecimal.ROUND_DOWN);

        //贷款期限页面显示数据
        String loanTimeStr = loanTimeInt + loanUnitStr;
        int count = InitDatas.loanAmount;
        //每期应还金额
        int loanAmountBigDecimal;
        if ("天".equals(loanUnitStr)) {
            BigDecimal rateAmount = InitDatas.rateDay.multiply(new BigDecimal(loanTimeInt)).multiply(new BigDecimal(InitDatas.loanAmount));
            count = count + rateAmount.intValue();
            cycleStr = "D";
            loanAmountBigDecimal = new BigDecimal(count).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            count = count / loanTimeInt;
            count = new BigDecimal(count).add(new BigDecimal(count).multiply(InitDatas.rateMounth)).intValue();
            cycleStr = "M";
            loanAmountBigDecimal = new BigDecimal(count).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        repayDataUIThread(loanTimeStr, (int) loanAmountBigDecimal);
    }

    /**
     * 还款信息（贷款期限，还款金额）UI更新
     *
     * @param loanTimeStr
     * @param loanAmountBigDecimal
     */
    @UiThread
    void repayDataUIThread(String loanTimeStr, int loanAmountBigDecimal) {
        tvHkqx.setText(loanTimeStr);
        tvMqyh.setText("￥" + loanAmountBigDecimal);
    }

    /**
     * 拖动条最大时间以及最小时间UI刷新
     *
     * @param startData
     * @param endData
     */
    @UiThread
    void limitDateUIThread(String startData, String endData) {
        tvStartTime.setText(startData);
        tvEndTime.setText(endData);
    }


    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getParameter();
                        getJobData();
                        btnDayOrMonth.setBackgroundResource(R.mipmap.icon_date_day);
                        InitDatas.loanUnit = 0;
                        seekBar.setProgress(0);
                        runinUI(0);
                        mSwipeLayout.setRefreshing(false);
                    }
                });

    }


    /**
     * 获取职业集合
     */
    private void getJobData() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "job");
        G_api.getInstance().setHandleInterface(Fragment2.this).getRequest(Urls.URL_GET_FIND_BY_TYPE, map, URL_GET_FIND_BY_TYPE);
    }

    /**
     * 获取贷款参数
     */
    private void getParameter() {
        G_api.getInstance().setHandleInterface(Fragment2.this).getRequest(Urls.URL_GET_FIND_PARAMETER, null, URL_GET_FIND_PARAMETER);
    }


}
