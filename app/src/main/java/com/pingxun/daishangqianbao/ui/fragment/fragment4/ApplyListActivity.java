package com.pingxun.daishangqianbao.ui.fragment.fragment4;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.ApplyListRecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.data.ApplyListBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;
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
public class ApplyListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, G_api.OnResultHandler {

    @BindView(R.id.tv_topview_title)
    TextView mTvTopviewTitle;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    private View notDataView;//无数据View
    private View errorView;//网络异常View

    private static final int REFRESH = 1;//下拉刷新标识
    private static final int LOADMORE = 2;//上拉加载标识
    private ApplyListRecyclerViewAdapter mAdapter;
    private List<ApplyListBean.DataBean.ContentBean> mApplyList;

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

        mRv.setLayoutManager(new LinearLayoutManager(me));
        mRv.addItemDecoration(new VerticalItemDecoration(me, 1));
        mAdapter = new ApplyListRecyclerViewAdapter(R.layout.rv_item_apply_list,mApplyList);
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
//                Bundle bundle=new Bundle();
//                bundle.putString(InitDatas.PROUDUCT_ID,String.valueOf(mListBean.get(position).getId()));
//                ActivityUtil.goForward(me,ProductInfoActivity.class,bundle,false);
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
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        postApplyList();
                        mSwipeLayout.setRefreshing(false);
                    }
                });

    }

    /**
     * 获取申请记录
     */
    private void postApplyList() {

        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", "1");
        params.put("appName", InitDatas.APP_NAME);

        G_api.getInstance().setHandleInterface(this).getRequest(Urls.URL_POST_FIND_APPLY_LIST,params,REFRESH);
    }

    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag){
            case REFRESH:
                ApplyListBean mBean= Convert.fromJson(jsonStr,ApplyListBean.class);
                if (mBean==null||!mBean.isSuccess()){
                    ToastUtils.showToast(me,"获取申请记录失败!");
                    return;
                }
                if (mBean.isSuccess()){
                    mApplyList = mBean.getData().getContent();
                    mAdapter.setNewData(mApplyList);
                }
                break;

        }

    }

    @Override
    public void onError(int flag) {

    }
}
