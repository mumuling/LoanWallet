package com.pingxun.daishangqianbao.ui.activity.other;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.ProductListRecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.ProductListBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.VerticalItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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



    private ProductListRecyclerViewAdapter mAdapter;
    private ProductListBean mBean;
    private List<ProductListBean.DataBean.ContentBean> mListBean;//产品集合
    private List<ProductListBean.DataBean.SortBean> mSortListBean;//以后会用到
    private View notDataView;//无数据View
    private View errorView;//网络异常View

    private static final int REFRESH = 1;//下拉刷新标识
    private static final int LOADMORE = 2;//上拉加载标识

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initData() {
        initTopView("产品超市");
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        onRefresh();
        initAdapter();
    }

    @OnClick({R.id.rl_money, R.id.rl_time, R.id.rl_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_money://贷款金额
                break;
            case R.id.rl_time://贷款期限
                break;
            case R.id.rl_type://贷款类型
                break;
        }
    }

    /**
     * 产品搜索
     */
    private void postProductSearch() {
        HashMap<String, String> params = new HashMap<>();
        params.put("period", "");//期限
        params.put("dateCycle", "");//期限周期
        params.put("amount", "");//借款金额
        params.put("channelNo", InitDatas.CHANNEL_NO);//渠道类型：ios,android,wechat
        params.put("appName", InitDatas.APP_NAME);
        JSONObject jsonObject = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_FIND_BY_CONDITION,jsonObject, REFRESH);
    }


    private void initAdapter() {
        notDataView = me.getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRv.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRv.getParent(), false);

        mRv.setLayoutManager(new LinearLayoutManager(me));
        mRv.addItemDecoration(new VerticalItemDecoration(me, 1));
        mAdapter = new ProductListRecyclerViewAdapter(R.layout.rv_item_product_list,mListBean);
//      mAdapter.setOnLoadMoreListener(this, mRv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRv.setAdapter(mAdapter);
        mRv.setHasFixedSize(true);

        mRv.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(InitDatas.PROUDUCT_ID,String.valueOf(mListBean.get(position).getId()));
                ActivityUtil.goForward(me,ProductInfoActivity.class,bundle,false);
            }
        });

        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

    }


    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag){
            case REFRESH://下拉刷新返回数据的回调
                mRv.setVisibility(View.VISIBLE);
                mBean= Convert.fromJson(jsonStr,ProductListBean.class);
                if (mBean==null||!mBean.isSuccess()){
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                }
                if (mBean.isSuccess()){
                    mListBean=mBean.getData().getContent();
                    mAdapter.setNewData(mListBean);
                }

                break;
            case LOADMORE://上拉加载发挥数据的回调

                break;
        }

    }

    @Override
    public void onError(int flag) {

    }

    /**
     * 刷新控件接口
     */
    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);
        mRv.setVisibility(View.GONE);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        postProductSearch();
                        mSwipeLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 上拉加载接口
     */
    @Override
    public void onLoadMoreRequested() {

    }
}
