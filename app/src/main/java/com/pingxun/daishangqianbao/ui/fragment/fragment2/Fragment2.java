package com.pingxun.daishangqianbao.ui.fragment.fragment2;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.ui.view.OnRulerChangeListener;
import com.pingxun.daishangqianbao.ui.view.RulerView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pingxun.daishangqianbao.R.id.limitDateEnd;
import static com.pingxun.daishangqianbao.R.id.limitDateStart;
import static com.pingxun.daishangqianbao.R.id.loanTimeSeekBar;
import static com.pingxun.daishangqianbao.R.id.rulerView;

/**
 * Created by LH on 2017/7/31.
 * 精准主页
 */

public class Fragment2 extends BaseFragment {
    @BindView(R.id.tv_total_money) TextView mTvTotalMoney;//借款金额
    @BindView(rulerView) RulerView mRulerView;//借款刻度尺
    @BindView(R.id.loanUnitButton) ImageButton mLoanUnitButton;//借款单位(日:月)
    @BindView(limitDateStart) TextView mLimitDateStart;//贷款最低时间
    @BindView(loanTimeSeekBar) SeekBar mLoanTimeSeekBar;//贷款拖动条
    @BindView(limitDateEnd) TextView mLimitDateEnd;//贷款最大时间
    @BindView(R.id.searchButton) Button mSearchButton;//搜索按钮
    @BindView(R.id.homePageContentScroll) ScrollView mHomePageContentScroll;//scrollView
    @BindView(R.id.tv_hkqx) TextView mTvHkqx;//还款期限
    @BindView(R.id.tv_mqyh) TextView mTvMqyh;//每期应还

    private boolean isDay = true;//借款单位是否是日单位

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_2;
    }

    @Override
    protected void initData() {
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
        //刷新首页数据
        runinUI(InitDatas.limitLowAmount, InitDatas.limitHeightAmount, InitDatas.dayStart, InitDatas.dayEnd);

    }


    @OnClick({R.id.loanUnitButton, R.id.searchButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loanUnitButton:
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
            case R.id.searchButton:


                break;
        }
    }

    /**
     * 初始化数据UI刷新
     *
     * @param startAmount
     * @param endAmount
     * @param dayStart
     * @param dayEnd
     */
    @UiThread
    void runinUI(int startAmount, int endAmount, String dayStart, String dayEnd) {



        mLimitDateStart.setText(dayStart);
        mLimitDateEnd.setText(dayEnd);


        mRulerView.setCurrLocation(13000);
        mTvTotalMoney.setText("￥ " + mRulerView.getCurrLocation());
        InitDatas.loanAmount = mRulerView.getCurrLocation();
        mRulerView.setOnRulerChangeListener(new OnRulerChangeListener() {
            @Override
            public void onChanged(int newValue) {
                mTvTotalMoney.setText("￥ " + newValue + "");
                InitDatas.loanAmount = mRulerView.getCurrLocation();
                calculateRepayInfo(mLoanTimeSeekBar.getProgress());

            }
        });
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
        float loanTimeInt = limitLow + (limitHeight - limitLow) * (progress / 100F);
        BigDecimal loanTimeBigDecimal = new BigDecimal(loanTimeInt).setScale(0, BigDecimal.ROUND_DOWN);
        //贷款期限页面显示数据
        String loanTimeStr = loanTimeBigDecimal + loanUnitStr;

        //每期应还金额
        int loanAmountBigDecimal = new BigDecimal(InitDatas.loanAmount / loanTimeBigDecimal.intValue()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        repayDataUIThread(loanTimeStr, loanAmountBigDecimal);
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
