package com.pingxun.daishangqianbao.ui.fragment.fragment2;


import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.F2JobDataBean;
import com.pingxun.daishangqianbao.data.F2ParamBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.other.ProductListActivity;
import com.pingxun.daishangqianbao.ui.view.OnRulerChangeListener;
import com.pingxun.daishangqianbao.ui.view.RulerView;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by LH on 2017/7/31.
 * 精准主页
 */

public class Fragment2 extends BaseFragment implements G_api.OnResultHandler {
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;//借款金额
    @BindView(R.id.rulerView)
    RulerView mRulerView;//借款刻度尺
    @BindView(R.id.loanUnitButton)
    ImageButton mLoanUnitButton;//借款单位(日:月)
    @BindView(R.id.limitDateStart)
    TextView mLimitDateStart;//贷款最低时间
    @BindView(R.id.loanTimeSeekBar)
    SeekBar mLoanTimeSeekBar;//贷款拖动条
    @BindView(R.id.limitDateEnd)
    TextView mLimitDateEnd;//贷款最大时间
    @BindView(R.id.searchButton)
    Button mSearchButton;//搜索按钮
    @BindView(R.id.homePageContentScroll)
    ScrollView mHomePageContentScroll;//scrollView
    @BindView(R.id.tv_hkqx)
    TextView mTvHkqx;//还款期限
    @BindView(R.id.tv_mqyh)
    TextView mTvMqyh;//每期应还
    @BindView(R.id.chooseJobText)
    TextView mChooseJobText;//职业选择
    @BindView(R.id.chooseJobLine)
    LinearLayout mChooseJobLine;//职业选择



    private boolean isDay = true;//借款单位是否是日单位
    private String cycleStr = "";//借款周期需要传的参数:M or D
    private String termStr = "";//期限参数
    private F2ParamBean mF2ParamBean;

    private static final int URL_GET_FIND_PARAMETER = 5;//获取贷款参数标识
    private static final int URL_GET_FIND_BY_TYPE = 6;//获取职业标识


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_2;
    }

    @Override
    protected void initData() {
        getParameter();
        getJobData();

    }

    /**
     * 获取职业集合
     */
    private void getJobData() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "job");
        G_api.getInstance().setHandleInterface(Fragment2.this).getRequest(Urls.URL_GET_FIND_BY_TYPE, mActivity, map, URL_GET_FIND_BY_TYPE);
    }

    /**
     * 获取贷款参数
     */
    private void getParameter() {
        G_api.getInstance().setHandleInterface(Fragment2.this).getRequest(Urls.URL_GET_FIND_PARAMETER, mActivity, null, URL_GET_FIND_PARAMETER);
    }


    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag) {

            case URL_GET_FIND_PARAMETER://获取贷款参数
                mF2ParamBean = Convert.fromJson(jsonStr, F2ParamBean.class);
                if (mF2ParamBean.getCode().equals("000000")) {
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
                }
                //刷新首页数据
                runinUI(InitDatas.limitLowAmount, InitDatas.limitHeightAmount, InitDatas.dayStart, InitDatas.dayEnd,InitDatas.jobTypeData);
                break;

            case URL_GET_FIND_BY_TYPE://获取职业集合
                F2JobDataBean mBean = Convert.fromJson(jsonStr, F2JobDataBean.class);
                if (mBean.getCode().equals("000000")) {
                    List<F2JobDataBean.DataBean> mList = mBean.getData();
                }

                break;

        }
    }

    @Override
    public void onError(int flag) {

    }

    @OnClick({R.id.loanUnitButton, R.id.searchButton,R.id.chooseJobLine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loanUnitButton://切换还款单位
                if (isDay) {//天
                    mLoanUnitButton.setBackgroundResource(R.mipmap.icon_date_month);
                    InitDatas.loanUnit = 1;

                    isDay = false;
                    limitDateUIThread(InitDatas.mounthStart, InitDatas.mounthEnd);
                } else {//月
                    mLoanUnitButton.setBackgroundResource(R.mipmap.icon_date_day);
                    InitDatas.loanUnit = 0;

                    isDay = true;
                    limitDateUIThread(InitDatas.dayStart, InitDatas.dayEnd);
                }
                calculateRepayInfo(InitDatas.progressData);

                break;
            case R.id.chooseJobLine:

                break;
            case R.id.searchButton://搜索按钮
                Log.e("期限==>>", InitDatas.loanDate+"");
                Log.e("期限周期==>>", cycleStr);
                Log.e("借款金额==>>",InitDatas.loanAmount + "");

                HashMap<String, String> params = new HashMap<>();
                params.put("period", InitDatas.loanDate+"");//期限
                params.put("dateCycle", cycleStr);//期限周期
                params.put("amount", InitDatas.loanAmount + "");//借款金额
                params.put("channelNo", "android");//渠道类型：ios,android,wechat
                params.put("appName", InitDatas.APP_NAME);

                ActivityUtil.goForward(mActivity, ProductListActivity.class,null,false);

                break;
        }
    }

    /**
     * 初始化数据UI刷新
     *  @param startAmount
     * @param endAmount
     * @param dayStart
     * @param dayEnd
     * @param jobTypeData

     */
    @UiThread
    void runinUI(int startAmount, int endAmount, String dayStart, String dayEnd, List<F2JobDataBean.DataBean> jobTypeData) {
        mLimitDateStart.setText(dayStart);
        mLimitDateEnd.setText(dayEnd);
        mRulerView.setCurrLocation(13000);
        mTvTotalMoney.setText("￥ " + mRulerView.getCurrLocation());
        InitDatas.loanAmount = mRulerView.getCurrLocation();
        InitDatas.loanDate = mRulerView.getCurrLocation();
        mRulerView.setOnRulerChangeListener(new OnRulerChangeListener() {
            @Override
            public void onChanged(int newValue) {
                mTvTotalMoney.setText("￥ " + newValue + "");

                InitDatas.loanAmount = newValue;
                calculateRepayInfo(InitDatas.progressData);

            }
        });

        mLoanTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
//        if (jobTypeData!=null||jobTypeData.size()!=0){
//            mChooseJobLine.setVisibility(View.VISIBLE);
//        }
//        circlelmage.reDrawByTouchAndMove(null,1.6F,1.0F);
    }




    /**
     * 拖动条最大时间以及最小时间UI刷新
     *
     * @param startData
     * @param endData
     */
    @UiThread
    void limitDateUIThread(String startData, String endData) {
        mLimitDateStart.setText(startData);
        mLimitDateEnd.setText(endData);
//        if (!isDay) {
//            repayInfoLabel.setText("预计每月应还");
//        } else {
//            repayInfoLabel.setText("预计每天应还");
//        }
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
            cycleStr="D";
            loanAmountBigDecimal = new BigDecimal(count).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            count = count / loanTimeInt;
            count = new BigDecimal(count).add(new BigDecimal(count).multiply(InitDatas.rateMounth)).intValue();
            cycleStr="M";
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
        mTvHkqx.setText(loanTimeStr);
        mTvMqyh.setText("￥" + loanAmountBigDecimal);
    }





}
