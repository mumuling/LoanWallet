package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.ProductListRecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.base.basepopupview.BasePopupWindow;
import com.pingxun.daishangqianbao.data.F2JobDataBean;
import com.pingxun.daishangqianbao.data.ProductListBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.common.LoginActivity;
import com.pingxun.daishangqianbao.ui.view.EmptyLayout;
import com.pingxun.daishangqianbao.ui.view.ListPopup;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;
import com.pingxun.daishangqianbao.utils.VerticalItemDecoration;

import org.json.JSONObject;

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
 * 产品超市主页列表
 */
public class ProductListActivity extends BaseActivity implements G_api.OnResultHandler,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.tv_money) TextView mTvMoney;
    @BindView(R.id.rl_money) RelativeLayout mRlMoney;//贷款金额选项
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.rl_time) RelativeLayout mRlTime;//贷款期限选项
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.rl_type) RelativeLayout mRlType;//贷款类型选项
    @BindView(R.id.rv) RecyclerView mRv;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout mSwipeLayout;//android自带刷新控件
    @BindView(R.id.view_line) View mViewLine;//线
    @BindView(R.id.iv_money) ImageView mIvMoney;//贷款金额下箭头
    @BindView(R.id.iv_time) ImageView mIvTime;//贷款期限下箭头
    @BindView(R.id.iv_type) ImageView mIvType;//贷款类型下箭头
    @BindView(R.id.parent_view) LinearLayout mParentView;//父布局
    @BindView(R.id.empty_layout) EmptyLayout mEmptyLayout;//空布局

    private ProductListRecyclerViewAdapter mAdapter;
    private ProductListBean mBean;
    private List<ProductListBean.DataBean.ContentBean> mList;//产品集合
    private List<ProductListBean.DataBean.SortBean> mSortListBean;//以后会用到
    private View notDataView;//无数据View
    private View errorView;//网络异常View
    private int page_size = 10;//每一次请求加载的条数
    private int mCurrentCounter = 0;//上一次加载的个数
    private int TOTAL_COUNTER;//总数
    private int page=1;

    private static final int REFRESH = 1;//下拉刷新标识
    private static final int LOADMORE = 2;//上拉加载标识
    private static final int AMOUNT=3;//贷款金额
    private static final int PERIOD=4;//贷款期限
    private static final int TYPE=5;//贷款类型

    private ListPopup mAmountListPopup;
    private ListPopup mPeriodListPopup;
    private ListPopup mTypeListPopup;
    private RotateAnimation showArrowAnima;
    private RotateAnimation dismissArrowAnima;

    private String sTypeId="";



    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initData() {
        initTopView("产品超市");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sTypeId = bundle.getString(InitDatas.PROUDUCT_TYPE_ID);
        }
        buildShowArrowAnima();
        buildDismissArrowAnima();
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        initAdapter();
        onRefresh();

    }

    @OnClick({R.id.rl_money, R.id.rl_time, R.id.rl_type,R.id.empty_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_money://贷款金额
                if (!mAmountListPopup.isShowing()) startShowArrowAnima(mIvMoney);
                mAmountListPopup.showPopupWindow(mViewLine);
                break;
            case R.id.rl_time://贷款期限
                if (!mPeriodListPopup.isShowing())startShowArrowAnima(mIvTime);
                mPeriodListPopup.showPopupWindow(mViewLine);
                break;
            case R.id.rl_type://贷款类型
                if (!mTypeListPopup.isShowing())startShowArrowAnima(mIvType);
                mTypeListPopup.showPopupWindow(mViewLine);
                break;
            case R.id.empty_layout:
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                onRefresh();
                break;
        }
    }


    /**
     * 初始化adapter
     */
    private void initAdapter() {
        notDataView = me.getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRv.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRv.getParent(), false);

        mRv.setLayoutManager(new LinearLayoutManager(me));
        mRv.addItemDecoration(new VerticalItemDecoration(me, 1));
        mAdapter = new ProductListRecyclerViewAdapter(R.layout.rv_item_product_list,mList);
        mAdapter.setOnLoadMoreListener(this, mRv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        mRv.setAdapter(mAdapter);
        mRv.setHasFixedSize(true);
        mRv.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showToast(me,String.valueOf(mList.get(position).getId()));
                isLogin(String.valueOf(mList.get(position).getId()));
            }
        });


        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

    }


    /**
     * 网络请求回调
     * @param jsonStr jsonString
     * @param flag 标识
     */
    @Override
    public void onResult(String jsonStr, int flag) {
        mEmptyLayout.setErrorType(EmptyLayout.NO_ERROR);
        mParentView.setVisibility(View.VISIBLE);
        switch (flag){

            case REFRESH://下拉刷新返回数据的回调

                mRv.setVisibility(View.VISIBLE);
                mBean= Convert.fromJson(jsonStr,ProductListBean.class);
                if (!mBean.isSuccess()){
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                    return;
                }
                if (mBean.isSuccess()){
                    TOTAL_COUNTER=mBean.getData().getTotalElements();
                    mList=mBean.getData().getContent();
                    if (mList.size()==0){
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(notDataView);
                    }else {
                        mAdapter.setNewData(mList);
                        mAdapter.disableLoadMoreIfNotFullPage();
                        mCurrentCounter=mAdapter.getData().size();
                    }
                }

                break;
            case LOADMORE://上拉加载返回数据的回调

                mAdapter.loadMoreComplete();
                mBean = Convert.fromJson(jsonStr, ProductListBean.class);
                if (!mBean.isSuccess()){
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                }
                if (mBean.isSuccess()){
                    List<ProductListBean.DataBean.ContentBean> mListMore;
                    mListMore = mBean.getData().getContent();
                    mAdapter.addData(mListMore);
                    mCurrentCounter = mAdapter.getData().size();
                }

                break;

            case AMOUNT://借款金额
                initAmountListPopup(jsonStr);
                break;
            case PERIOD://借款期限
                initPeriodListPopup(jsonStr);
                break;
            case TYPE://借款类型
                initTypeListPopup(jsonStr);
                break;
        }

    }
    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(me)==-1) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {

        mSwipeLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mRv.setVisibility(View.GONE);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        page=1;
                        postProductSearch(sTypeId,page,REFRESH);
                        getAmount();
                        getPeriod();
                        getType();
                        mSwipeLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 上拉加载接口
     */
    @Override
    public void onLoadMoreRequested() {
        if (mCurrentCounter < page_size) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter>=TOTAL_COUNTER){
                mAdapter.loadMoreEnd(false);
            }else {
                if (NetUtil.getNetWorkState(me)!=-1){
                    Observable.timer(1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    page++;
                                    postProductSearch(sTypeId,page,LOADMORE);
                                }
                            });

                }else {
                    ToastUtils.showToast(me,"网络连接异常!");
                    mAdapter.loadMoreFail();
                }
            }
        }
    }



    /**
     * 产品搜索
     * @param sTypeId 类型ID
     * @param page 请求的页码
     * @param state 下拉或者上拉
     */
    private void postProductSearch(String sTypeId,int page,int state) {
        HashMap<String, String> params = new HashMap<>();
        params.put("period", "");//期限
        params.put("dateCycle", "");//期限周期
        params.put("amount", "");//借款金额
        params.put("loanType",sTypeId);
        params.put("channelNo", InitDatas.CHANNEL_NO);//渠道类型：ios,android,wechat
        params.put("appName", InitDatas.APP_NAME);
        params.put("pageNo",page+"");
        JSONObject jsonObject = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_FIND_BY_CONDITION,jsonObject, state);
    }

    /**
     * 获取贷款类型
     */
    private void getType() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "loanType ");
        G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_GET_FIND_BY_TYPE,map, TYPE);
    }

    /**
     * 获取贷款金额
     */
    private void getAmount() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "loanAmount");
        G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_GET_FIND_BY_TYPE,map, AMOUNT);
    }

    /**
     * 获取贷款期限
     */
    private void getPeriod() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "loanPeriod");
        G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_GET_FIND_BY_TYPE,map, PERIOD);
    }



    /**
     * 判断是否登录，登录了才能跳转到产品详情界面
     * @param sId
     */
    private void isLogin(String sId){
        if (!SharedPrefsUtil.getValue(me, InitDatas.SP_NAME,InitDatas.UserIsLogin,false)){
            ActivityUtil.goForward(me, LoginActivity.class,null,false);
        }else {
            Bundle bundle=new Bundle();
            bundle.putString(InitDatas.PROUDUCT_ID,sId);
            ActivityUtil.goForward(me,ProductInfoActivity.class,bundle,false);
        }
    }



    /**
     * 初始化贷款金额PopupView
     * @param jsonStr
     */
    private void initAmountListPopup(String jsonStr) {
        F2JobDataBean mAmountBean = Convert.fromJson(jsonStr, F2JobDataBean.class);
        if (mAmountBean == null || !mAmountBean.isSuccess()) {
            ToastUtils.showToast(me, "获取贷款金额失败!");
            mRlMoney.setEnabled(false);
            return;
        }
        if (mAmountBean.isSuccess()) {
            final List<F2JobDataBean.DataBean> mList = mAmountBean.getData();
            ListPopup.Builder builder=new ListPopup.Builder(me);
            for (int i=0;i<mList.size();i++){
                builder.addItem(mList.get(i).getName());
            }
            mAmountListPopup=builder.build();
            mAmountListPopup.setOnListPopupItemClickListener(new ListPopup.OnListPopupItemClickListener() {
                @Override
                public void onItemClick(int what) {
                    mAmountListPopup.dismiss();
                    onRefresh();
                }
            });
            mAmountListPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public boolean onBeforeDismiss() {
                    startDismissArrowAnima(mIvMoney);
                    return super.onBeforeDismiss();
                }
                @Override
                public void onDismiss() {

                }
            });
        }
    }

    /**
     * 初始化贷款期限PopupView
     * @param jsonStr
     */
    private void initPeriodListPopup(String jsonStr) {
        F2JobDataBean mPeriodBean = Convert.fromJson(jsonStr, F2JobDataBean.class);
        if (mPeriodBean == null || !mPeriodBean.isSuccess()) {
            ToastUtils.showToast(me, "获取贷款期限集合失败!");
            mRlTime.setEnabled(false);
            return;
        }
        if (mPeriodBean.isSuccess()) {
            final List<F2JobDataBean.DataBean> mList = mPeriodBean.getData();
            ListPopup.Builder builder=new ListPopup.Builder(me);
            for (int i=0;i<mList.size();i++){
                builder.addItem(mList.get(i).getName());
            }
            mPeriodListPopup=builder.build();
            mPeriodListPopup.setOnListPopupItemClickListener(new ListPopup.OnListPopupItemClickListener() {
                @Override
                public void onItemClick(int what) {

                    mPeriodListPopup.dismiss();
                    onRefresh();
                }
            });
            mPeriodListPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
                @Override
                public boolean onBeforeDismiss() {
                    startDismissArrowAnima(mIvTime);
                    return super.onBeforeDismiss();
                }
            });

        }

    }


    /**
     * 初始化贷款类型PopupView
     * @param jsonStr
     */
    private void initTypeListPopup(String jsonStr) {
        final F2JobDataBean mTypeBean = Convert.fromJson(jsonStr, F2JobDataBean.class);
        if (mTypeBean == null || !mTypeBean.isSuccess()) {
            ToastUtils.showToast(me, "获取贷款类型集合失败!");
            mRlType.setEnabled(false);
            return;
        }
        if (mTypeBean.isSuccess()) {
            final List<F2JobDataBean.DataBean> mList = mTypeBean.getData();
            ListPopup.Builder builder=new ListPopup.Builder(me);
            for (int i=0;i<mList.size();i++){
                builder.addItem(mList.get(i).getName());
            }
            mTypeListPopup=builder.build();
            mTypeListPopup.setOnListPopupItemClickListener(new ListPopup.OnListPopupItemClickListener() {
                @Override
                public void onItemClick(int what) {
                    mTypeListPopup.dismiss();
                    onRefresh();
                }
            });
            mTypeListPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }

                @Override
                public boolean onBeforeDismiss() {
                    startDismissArrowAnima(mIvType);
                    return super.onBeforeDismiss();
                }
            });
        }
    }






















    //====================================================箭头动画相关的4个方法======================================//
    private void buildShowArrowAnima() {
        if (showArrowAnima != null) return;
        showArrowAnima = new RotateAnimation(0, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        showArrowAnima.setDuration(450);
        showArrowAnima.setInterpolator(new AccelerateDecelerateInterpolator());
        showArrowAnima.setFillAfter(true);
    }

    private void buildDismissArrowAnima() {
        if (dismissArrowAnima != null) return;
        dismissArrowAnima = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        dismissArrowAnima.setDuration(450);
        dismissArrowAnima.setInterpolator(new AccelerateDecelerateInterpolator());
        dismissArrowAnima.setFillAfter(true);
    }

    private void startShowArrowAnima(ImageView iv) {
        iv.clearAnimation();
        iv.startAnimation(showArrowAnima);
    }

    private void startDismissArrowAnima(ImageView iv) {
        iv.clearAnimation();
        iv.startAnimation(dismissArrowAnima);
    }

}
