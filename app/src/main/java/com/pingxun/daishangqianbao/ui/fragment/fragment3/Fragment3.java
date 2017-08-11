package com.pingxun.daishangqianbao.ui.fragment.fragment3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.F3_Bank_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.adapter.F3_Card_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.BankListBean;
import com.pingxun.daishangqianbao.data.F3CardListBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.other.WebViewActivity;
import com.pingxun.daishangqianbao.ui.view.EmptyLayout;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

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
 * Created by LH on 2017/7/31.
 * 信用卡Fragment
 */

public class Fragment3 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, G_api.OnResultHandler,BaseQuickAdapter.RequestLoadMoreListener  {
    @BindView(R.id.tv_topview_title)
    TextView mTvTopviewTitle;
    @BindView(R.id.rv1)
    RecyclerView mRv1;//九宫格
    @BindView(R.id.rv2)
    RecyclerView mRv2;//推荐信用卡
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.parent_view)
    LinearLayout mParentView;
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;


    private F3_Bank_RecyclerViewAdapter mBankAdapter;//银行Adapter;
    private F3_Card_RecyclerViewAdapter mCardAdapter;//信用卡Adapter;
    private static final int GET_BANK = 1;

    private static final int APPLY = 3;
    private int TOTAL_COUNTER;//总数
    private static final int REFRESH = 5;//下拉刷新标识
    private static final int LOADMORE = 2;//上拉加载标识
    private int page_size = 10;//每一次请求加载的条数
    private int mCurrentCounter = 0;//上一次加载的个数
    private View notDataView;//无数据View
    private View errorView;//网络异常View
    private int page=1;

    private List<BankListBean.DataBean> mBankList;
    private List<F3CardListBean.DataBean.ContentBean> mCardList;

    private F3CardListBean mCardBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_3);
    }

    @Override
    protected void onInitView(View rootView) {
        super.onInitView(rootView);
        mTvTopviewTitle.setText("信用卡");
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLoadData(Bundle savedInstanceState) {
        super.onLoadData(savedInstanceState);
        initAdapter();
        onRefresh();
    }

    //    @Override
//    protected int getRootLayoutResID() {
//        return R.layout.fragment_3;
//    }
//
//    @Override
//    protected void initData() {
//        mTvTopviewTitle.setText("信用卡");
//        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
//        mSwipeLayout.setOnRefreshListener(this);
//        initAdapter();
//        onRefresh();
//    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        notDataView = mActivity.getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRv2.getParent(), false);
        errorView = mActivity.getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRv2.getParent(), false);

        mBankAdapter = new F3_Bank_RecyclerViewAdapter(R.layout.rv_item_bank, mBankList);
        mCardAdapter = new F3_Card_RecyclerViewAdapter(R.layout.rv_item_credit_card, mCardList);
        mCardAdapter.setOnLoadMoreListener(this, mRv2);

        mRv1.setHasFixedSize(true);
        mRv1.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRv1.setAdapter(mBankAdapter);
        mRv1.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goWebView(mBankList.get(position).getUrl(), mBankList.get(position).getName());
                Map<String, String> params = new HashMap<>();
                params.put("productId", mBankList.get(position).getId() + "");
                JSONObject jsonObject = new JSONObject(params);
                G_api.getInstance().setHandleInterface(Fragment3.this).upJson(Urls.URL_POST_APPLY_CREDIT_CARD, jsonObject, APPLY);
            }
        });

        mRv2.setHasFixedSize(true);
        mRv2.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv2.setAdapter(mCardAdapter);
        mRv2.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Map<String, String> params = new HashMap<>();
                params.put("productId", mCardList.get(position).getId() + "");
                JSONObject jsonObject = new JSONObject(params);
                G_api.getInstance().setHandleInterface(Fragment3.this).upJson(Urls.URL_POST_APPLY_CREDIT_CARD, jsonObject, APPLY);
                goWebView(mCardList.get(position).getUrl(), mCardList.get(position).getName());
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
     * 跳转到webView
     *
     * @param url  url
     * @param name 产品名称
     */
    private void goWebView(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("productName", name);
        if (url.contains("jie.gomemyf.com")) {
            ToastUtils.showToast(mActivity, "jie.gomemyf.com");
        } else {
            ActivityUtil.goForward(mActivity, WebViewActivity.class, bundle, false);
        }
    }


    @Override
    public void onResult(String jsonStr, int flag) {
        mEmptyLayout.setErrorType(EmptyLayout.NO_ERROR);
        mParentView.setVisibility(View.VISIBLE);
        switch (flag) {
            case GET_BANK://获取所有银行回调
                BankListBean mBankBean = Convert.fromJson(jsonStr, BankListBean.class);
                if (mBankBean == null || !mBankBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取银行失败");
                    return;
                }
                if (mBankBean.isSuccess()) {
                    mBankList = mBankBean.getData();
                    if (mBankList.size()>=3){
                        mBankAdapter.setNewData(mBankList.subList(0,3));
                    }else {
                        mBankAdapter.setNewData(mBankList);
                    }
                }
                break;
            case REFRESH://下拉刷新

                mCardBean = Convert.fromJson(jsonStr, F3CardListBean.class);
                if (mCardBean == null || !mCardBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取信用卡列表失败");
                    mCardAdapter.setNewData(null);
                    mCardAdapter.setEmptyView(errorView);
                    return;
                }
                if (mCardBean.isSuccess()) {
                    TOTAL_COUNTER = mCardBean.getData().getTotalElements();
                    mCardList = mCardBean.getData().getContent();
                    if (mCardList.size()==0){
                        mCardAdapter.setNewData(null);
                        mCardAdapter.setEmptyView(notDataView);
                    }else {
                        mCardAdapter.setNewData(mCardList);
                        mCardAdapter.disableLoadMoreIfNotFullPage();
                        mCurrentCounter = mCardAdapter.getData().size();
                    }
                }

                break;

            case LOADMORE://上拉加载
                mCardAdapter.loadMoreComplete();
                mCardBean = Convert.fromJson(jsonStr, F3CardListBean.class);
                List<F3CardListBean.DataBean.ContentBean> mListMore;
                mListMore = mCardBean.getData().getContent();
                mCardAdapter.addData(mListMore);
                mCurrentCounter = mCardAdapter.getData().size();
                break;

            case APPLY:

                break;
        }
    }


    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(mActivity)==-1) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getBank();
                        page=1;
                        getCard(page,REFRESH);
                        mSwipeLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 上拉加载回调
     */
    @Override
    public void onLoadMoreRequested() {
        if (mCurrentCounter < page_size) {
           mCardAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter>=TOTAL_COUNTER){
                mCardAdapter.loadMoreEnd(false);
            }else {
                if (NetUtil.getNetWorkState(mActivity)!=-1){
                    Observable.timer(1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    page++;
                                    getCard(page,LOADMORE);
                                }
                            });

                }else {
                    ToastUtils.showToast(mActivity,"网络连接异常!");
                    mCardAdapter.loadMoreFail();
                }
            }
        }
    }

    /**
     * 获取所有银行
     */
    private void getBank() {
        Map<String, String> params = new HashMap<>();
        params.put("flag", "1");
        G_api.getInstance().setHandleInterface(Fragment3.this).getRequest(Urls.URL_GET_FIND_BANK_BY_POSITION, params, GET_BANK);
    }

    /**
     * 获取推荐信用卡
     * @param page 页码
     * @param state 下拉刷新或者上拉加载
     */
    private void getCard(int page, int state) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", page+"");
        JSONObject jsonObject = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_FIND_BY_CONDITION_CARD, jsonObject, state);

    }


    /**
     * 点击事件
     */
    @OnClick(R.id.empty_layout)
    public void onViewClicked(){
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefresh();
    }


}
