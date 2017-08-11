package com.pingxun.daishangqianbao.ui.fragment.fragment4;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.ApplyListRecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.ApplyListBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.common.LoginActivity;
import com.pingxun.daishangqianbao.ui.activity.other.ProductInfoActivity;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;
import com.pingxun.daishangqianbao.utils.VerticalItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 申请记录界面
 */
public class ApplyListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, G_api.OnResultHandler ,BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.tv_topview_title)
    TextView mTvTopviewTitle;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    private View notDataView;//无数据View
    private View errorView;//网络异常View

    private int TOTAL_COUNTER;//总数
    private static final int REFRESH = 1;//下拉刷新标识
    private static final int LOADMORE = 2;//上拉加载标识
    private ApplyListRecyclerViewAdapter mAdapter;
    private List<ApplyListBean.DataBean.ContentBean> mApplyList;
    private int page_size = 10;//每一次请求加载的条数
    private int mCurrentCounter = 0;//上一次加载的个数
    private int page=1;
    private ApplyListBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_list;
    }

    @Override
    protected void initData() {
        initTopView("申请记录");
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);

        onRefresh();
        initAdapter();

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRv.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRv.getParent(), false);


        mAdapter = new ApplyListRecyclerViewAdapter(R.layout.rv_item_apply_list,mApplyList);
        mAdapter.setOnLoadMoreListener(this, mRv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRv.setAdapter(mAdapter);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(me));
        mRv.addItemDecoration(new VerticalItemDecoration(me, 1));
        mRv.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showToast(me,String.valueOf(mApplyList.get(position).getProductId()));
                isLogin(String.valueOf(mApplyList.get(position).getProductId()));
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






    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag){
            case REFRESH:
                Logger.json(jsonStr);
                mBean = Convert.fromJson(jsonStr,ApplyListBean.class);
                if (mBean ==null||!mBean.isSuccess()){
                    ToastUtils.showToast(me,"获取申请记录失败!");
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                    return;
                }
                if (mBean.isSuccess()){
                    TOTAL_COUNTER= mBean.getData().getTotalElements();
                  //  Log.e("总数==>>",TOTAL_COUNTER+"");
                    mApplyList = mBean.getData().getContent();
                    if (mApplyList.size()==0){
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(notDataView);
                    }else {
                        mAdapter.setNewData(mApplyList);
                        mCurrentCounter = mAdapter.getData().size();//获取adapter的size
                    }
                }
                break;

            case LOADMORE:
                mAdapter.loadMoreComplete();
                mBean = Convert.fromJson(jsonStr, ApplyListBean.class);
                List<ApplyListBean.DataBean.ContentBean> mListMore;
                mListMore = mBean.getData().getContent();
                mAdapter.addData(mListMore);
                mCurrentCounter = mAdapter.getData().size();//获取adapter的size
               // Log.e("获取adapter的size==>>",mCurrentCounter+"");
                break;

        }

    }

    @Override
    public void onError(int flag) {

    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        page=1;
                        HashMap<String, String> params = new HashMap<>();
                        params.put("pageNo", page+"");
                        params.put("appName", InitDatas.APP_NAME);
                        G_api.getInstance().setHandleInterface(ApplyListActivity.this).getRequest(Urls.URL_POST_FIND_APPLY_LIST,params,REFRESH);
                        mSwipeLayout.setRefreshing(false);
                    }
                });

    }

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

                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("pageNo", page+"");
                                    params.put("appName", InitDatas.APP_NAME);
                                    G_api.getInstance().setHandleInterface(ApplyListActivity.this).getRequest(Urls.URL_POST_FIND_APPLY_LIST,params,LOADMORE);
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
     * 判断是否登录，登录了才能跳转到产品详情界面
     *
     * @param sId
     */
    private void isLogin(String sId) {
        if (!SharedPrefsUtil.getValue(me, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
            ActivityUtil.goForward(me, LoginActivity.class, null, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(InitDatas.PROUDUCT_ID, sId);
            ActivityUtil.goForward(me, ProductInfoActivity.class, bundle, false);
        }
    }
}
