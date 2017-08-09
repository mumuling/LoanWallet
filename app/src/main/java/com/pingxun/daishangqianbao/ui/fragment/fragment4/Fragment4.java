package com.pingxun.daishangqianbao.ui.fragment.fragment4;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.other.EventMessage;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.ui.activity.common.AboutUsActivity;
import com.pingxun.daishangqianbao.ui.activity.common.LoginActivity;
import com.pingxun.daishangqianbao.ui.view.F4DialogPopupView;
import com.pingxun.daishangqianbao.ui.view.F4QuitDialogPopupView;
import com.pingxun.daishangqianbao.ui.view.XCRoundImageView;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.AppUtils;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Lh on 2017/7/31.
 * 我的fragment
 */

public class Fragment4 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_head)
    XCRoundImageView mIvHead;//头像
    @BindView(R.id.tv_name)
    TextView mTvName;//用户名
    @BindView(R.id.mine_apply)
    RelativeLayout mMineApply;//申请记录
    @BindView(R.id.mine_about_us)
    RelativeLayout mMineAboutUs;//关于我们
    @BindView(R.id.mine_contact_us)
    RelativeLayout mMineContactUs;//联系客服
    @BindView(R.id.tv_version)
    TextView mTvVersion;//版本号
    @BindView(R.id.mine_version)
    RelativeLayout mMineVersion;//版本号
    @BindView(R.id.btn_quit)
    TextView mBtnQuit;//退出登录
    @BindView(R.id.lin_mine_parent)
    LinearLayout mLinMineParent;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;


    @Override
    protected int getRootLayoutResID() {
        EventBus.getDefault().register(this);//绑定事件接受
        return R.layout.fragment_4;
    }


    @Override
    protected void initData() {
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @OnClick({R.id.mine_apply, R.id.mine_about_us, R.id.mine_contact_us, R.id.mine_version, R.id.btn_quit, R.id.lin_mine_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_head://头像
//
//                break;
            case R.id.mine_apply://申请记录
                if (NetUtil.getNetWorkState(mActivity)!=-1){
                    if (!SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                        ActivityUtil.goForward(mActivity, LoginActivity.class, null, false);
                    } else {
                        ActivityUtil.goForward(mActivity, ApplyListActivity.class, null, false);
                    }
                }else {
                    ToastUtils.showToast(mActivity,"网络未连接，请检查您的网络设置!");
                }

                break;
            case R.id.mine_about_us://关于我们
                ActivityUtil.goForward(mActivity, AboutUsActivity.class, null, false);
                break;

            case R.id.mine_contact_us://联系我们
                F4DialogPopupView f4DialogPopupView = new F4DialogPopupView(mActivity);
                f4DialogPopupView.setPopupWindowFullScreen(true);
                f4DialogPopupView.showPopupWindow();
                break;
            case R.id.mine_version://版本号

                break;
            case R.id.btn_quit://退出登录
                //Logger.d(InitDatas.latitude + ":" + InitDatas.longitude);
                F4QuitDialogPopupView f4QuitDialogPopupView = new F4QuitDialogPopupView(mActivity);
                f4QuitDialogPopupView.setPopupWindowFullScreen(true);
                f4QuitDialogPopupView.showPopupWindow();
                break;
            case R.id.lin_mine_parent:
                if (!SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                    ActivityUtil.goForward(mActivity, LoginActivity.class, null, false);
                } else {
                    ToastUtils.showToast(mActivity, "您已登录");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销事件接受
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(EventMessage message) {
        if (message.message.equals("update_UserName")) {
            initData();
        }
    }


    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mTvVersion.setText("版本号" + AppUtils.getVersionName(mActivity));
                        if (!SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                            mTvName.setText("未登录");
                            mBtnQuit.setVisibility(View.GONE);
                        } else {
                            String phoneStr = SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserPhone, "");
                            String inputStr = phoneStr.substring(0, 4) + "****" + phoneStr.substring(8);
                            mTvName.setText(inputStr);
                            mBtnQuit.setVisibility(View.VISIBLE);
                        }
                        mSwipeLayout.setRefreshing(false);
                    }
                });
    }
}
