package com.pingxun.daishangqianbao.ui.fragment.fragment3;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.ToastUtils;
import com.pingxun.daishangqianbao.utils.VerticalItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LH on 2017/7/31.
 * 信用卡Fragment
 */

public class Fragment3 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, G_api.OnResultHandler {
    @BindView(R.id.tv_topview_title)
    TextView mTvTopviewTitle;
    @BindView(R.id.rv1)
    RecyclerView mRv1;//九宫格
    @BindView(R.id.rv2)
    RecyclerView mRv2;//推荐信用卡
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;

    private F3_Bank_RecyclerViewAdapter mBankAdapter;//银行Adapter;
    private F3_Card_RecyclerViewAdapter mCardAdapter;//信用卡Adapter;
    private static final int GET_BANK = 1;
    private static final int GET_CARD = 2;

    private List<BankListBean.DataBean> mBankList;
    private List<F3CardListBean.DataBean.ContentBean> mCardList;


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_3;
    }

    @Override
    protected void initData() {
        mTvTopviewTitle.setText("信用卡");
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);

        onRefresh();
        initAdapter();

    }

    @Override
    public void onRefresh() {

        mSwipeLayout.setRefreshing(true);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getBank();
                        getCard();
                        mSwipeLayout.setRefreshing(false);
                    }
                });
    }


    private void initAdapter() {
        mBankAdapter = new F3_Bank_RecyclerViewAdapter(R.layout.rv_item_bank, mBankList);
        mCardAdapter =new F3_Card_RecyclerViewAdapter(R.layout.rv_item_credit_card,mCardList);
        // mBankAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRv1.setHasFixedSize(true);
        mRv1.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRv1.setAdapter(mBankAdapter);
        mRv1.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(mActivity, mBankList.get(position).getName());
//                Bundle bundle=new Bundle();
//                bundle.putString(InitDatas.PROUDUCT_ID,String.valueOf(mListBean.get(position).getId()));
//                ActivityUtil.goForward(me,ProductInfoActivity.class,bundle,false);
            }
        });

        mRv2.setHasFixedSize(true);
        mRv2.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv2.addItemDecoration(new VerticalItemDecoration(mActivity, 1));
        mRv2.setAdapter(mCardAdapter);
        mRv2.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(mActivity, mCardList.get(position).getName());

            }
        });
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
     */
    private void getCard() {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", "1");
        JSONObject jsonObject = new JSONObject(params);
        G_api.getInstance().setHandleInterface(this).upJson(Urls.URL_POST_FIND_BY_CONDITION_CARD, jsonObject, GET_CARD);

    }

    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag) {
            case GET_BANK://获取所有银行回调
                BankListBean mBankBean = Convert.fromJson(jsonStr, BankListBean.class);
                if (mBankBean == null || !mBankBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取银行失败");
                    return;
                }
                if (mBankBean.isSuccess()) {
                    mBankList = mBankBean.getData();
                    mBankAdapter.setNewData(mBankList);
                }

                break;
            case GET_CARD://获取推荐信用卡回调
                F3CardListBean mCardBean=Convert.fromJson(jsonStr,F3CardListBean.class);

                if(mCardBean==null||!mCardBean.isSuccess()){
                    ToastUtils.showToast(mActivity, "获取信用卡列表失败");
                    return;}
                if (mCardBean.isSuccess()){
                    mCardList = mCardBean.getData().getContent();
                    mCardAdapter.setNewData(mCardList);
                }

                break;

        }
    }

    @Override
    public void onError(int flag) {

    }
}
