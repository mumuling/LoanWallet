package com.pingxun.daishangqianbao.ui.fragment.fragment1;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.F1_Bank_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.adapter.F1_Recommend_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.adapter.F1_Type_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.BankListBean;
import com.pingxun.daishangqianbao.data.BannerBean;
import com.pingxun.daishangqianbao.data.F1ProductRecommendBean;
import com.pingxun.daishangqianbao.data.F1ProductTypeBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.ui.activity.common.LoginActivity;
import com.pingxun.daishangqianbao.ui.activity.other.ProductInfoActivity;
import com.pingxun.daishangqianbao.ui.activity.other.ProductListActivity;
import com.pingxun.daishangqianbao.ui.activity.other.WebViewActivity;
import com.pingxun.daishangqianbao.ui.view.EmptyLayout;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.Convert;
import com.pingxun.daishangqianbao.utils.NetUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LH on 2017/7/31.
 * 产品大全主页
 */
public class Fragment1 extends BaseFragment implements G_api.OnResultHandler, BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String>, SwipeRefreshLayout.OnRefreshListener {


    private static final int GET_BANNER = 1;
    private static final int GET_PRODUCT_RECOMMEND = 2;
    private static final int GET_PRODUCT_TYPE = 3;
    private static final int GET_CREDIT_CARD = 4;

    @BindView(R.id.banner)
    BGABanner mBanner;//轮播图
    @BindView(R.id.rb_dydk)
    RadioButton mRbDydk;//抵押贷款
    @BindView(R.id.rb_gxdk)
    RadioButton mRbGxdk;//工薪贷款
    @BindView(R.id.rb_xydk)
    RadioButton mRbXydk;//信用贷款
    @BindView(R.id.rb_xesd)
    RadioButton mRbXesd;//小额速贷
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.rv1)
    RecyclerView mRv1;//产品分类
    @BindView(R.id.rv2)
    RecyclerView mRv2;//产品推荐
    @BindView(R.id.rv3)
    RecyclerView mRv3;//办卡专区
    //  @BindView(R.id.multiple_status_view)
//  MultipleStatusView mMultipleStatusView;
    @BindView(R.id.parent_view)
    LinearLayout mParentView;
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;



    private F1_Type_RecyclerViewAdapter mTypeAdapter;//产品分类adapter
    private F1_Bank_RecyclerViewAdapter mF1_bank_recyclerViewAdapter;//办卡专区adapter
    private F1_Recommend_RecyclerViewAdapter mF1_recommend_recyclerViewAdapter;//产品推荐adapter
    private List<F1ProductTypeBean.DataBean> mTypeList;
    private List<BankListBean.DataBean> mBankList;
    private List<F1ProductRecommendBean.DataBean> mRecommendList;
//    private View mSonView;


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initData() {
//        mSonView = LayoutInflater.from(mActivity).inflate(R.layout.error_view, mParentView, false);
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        onRefresh();
        initAdapter();
    }


    @OnClick({R.id.rb_dydk, R.id.rb_gxdk, R.id.rb_xydk, R.id.rb_xesd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_dydk://抵押
                goList("diyaloan");
                break;
            case R.id.rb_gxdk://工薪
                goList("salaryloan");
                break;
            case R.id.rb_xydk://信用
                goList("creditcardloan");
                break;
            case R.id.rb_xesd://小额
                goList("xiaoejishuloan");
                break;
        }
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        //===============================初始化产品分类adapter=========================================//
        mTypeAdapter = new F1_Type_RecyclerViewAdapter(R.layout.rv_item_f1_product_type, mTypeList);
        mRv1.setHasFixedSize(true);
        mRv1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRv1.setAdapter(mTypeAdapter);
        mRv1.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showToast(mActivity, mTypeList.get(position).getCode());
                goList(mTypeList.get(position).getCode());
            }
        });


        //===============================初始化产品推荐adapter=========================================//
        mF1_recommend_recyclerViewAdapter = new F1_Recommend_RecyclerViewAdapter(R.layout.rv_item_f1_product_recommend, mRecommendList);
        mRv2.setHasFixedSize(true);
        mRv2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRv2.setAdapter(mF1_recommend_recyclerViewAdapter);
        mRv2.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isLogin(String.valueOf(mRecommendList.get(position).getId()));
            }
        });


        //===============================初始化办卡专区adapter=========================================//
        mF1_bank_recyclerViewAdapter = new F1_Bank_RecyclerViewAdapter(R.layout.rv_item_f1_card_recommend, mBankList);
        mRv3.setHasFixedSize(true);
        mRv3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRv3.setAdapter(mF1_bank_recyclerViewAdapter);
        mRv3.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goWebView(mBankList.get(position).getUrl(), mBankList.get(position).getName());
            }
        });


    }


    @Override
    public void onResult(String jsonStr, int flag) {
        mEmptyLayout.setErrorType(EmptyLayout.NO_ERROR);
        mParentView.setVisibility(View.VISIBLE);
        switch (flag) {
            case GET_BANNER://获取Banner回调
                BannerBean mBannerBean = Convert.fromJson(jsonStr, BannerBean.class);
                if (mBannerBean == null || !mBannerBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取Banner失败!");
                    return;
                }
                if (mBannerBean.isSuccess()) {
                    List<BannerBean.DataBean> mBannerList = mBannerBean.getData();
                    setShowpic(mBannerList);
                }
                break;

            case GET_PRODUCT_RECOMMEND://产品推荐回调

                F1ProductRecommendBean f1ProductRecommendBean = Convert.fromJson(jsonStr, F1ProductRecommendBean.class);
                if (f1ProductRecommendBean == null || !f1ProductRecommendBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取产品推荐失败");
                    return;
                }
                if (f1ProductRecommendBean.isSuccess()) {
                    mRecommendList = f1ProductRecommendBean.getData();
                    if (mRecommendList.size() >= 3) {
                        mF1_recommend_recyclerViewAdapter.setNewData(mRecommendList.subList(0, 3));//只取前三条数据
                    } else {
                        mF1_recommend_recyclerViewAdapter.setNewData(mRecommendList);//只取前三条数据
                    }
                }

                break;


            case GET_PRODUCT_TYPE://产品分类回调

                F1ProductTypeBean mProductTypeBean = Convert.fromJson(jsonStr, F1ProductTypeBean.class);
                if (mProductTypeBean == null || !mProductTypeBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取产品分类失败");
                    return;
                }
                if (mProductTypeBean.isSuccess()) {
                    mTypeList = mProductTypeBean.getData();
                    if (mTypeList.size() >= 4) {
                        mTypeAdapter.setNewData(mTypeList.subList(0, 4));
                    } else {
                        mTypeAdapter.setNewData(mTypeList);
                    }
                }

                break;

            case GET_CREDIT_CARD://信用卡推荐回调
                BankListBean mBankBean = Convert.fromJson(jsonStr, BankListBean.class);
                if (mBankBean == null || !mBankBean.isSuccess()) {
                    ToastUtils.showToast(mActivity, "获取银行失败");
                    return;
                }
                if (mBankBean.isSuccess()) {
                    mBankList = mBankBean.getData();
                    if (mBankList.size() >= 3) {
                        mF1_bank_recyclerViewAdapter.setNewData(mBankList.subList(0, 3));//只取前三条数据
                    } else {
                        mF1_bank_recyclerViewAdapter.setNewData(mBankList);
                    }
                }
                break;
        }

    }


    @Override
    public void onError(int flag) {
//        mParentView.setVisibility(View.GONE);
        if (NetUtil.getNetWorkState(mActivity)==-1){
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    /**
     * 设置轮播图
     *
     * @param mBannerlist Banner集合
     */
    private void setShowpic(List<BannerBean.DataBean> mBannerlist) {
        List<String> imgUrlList = new ArrayList<>();

        for (int i = 0; i < mBannerlist.size(); i++) {
            //获得每张图片的地址
            String url = mBannerlist.get(i).getBannerImg();
            imgUrlList.add(i, url);
        }

        if (imgUrlList.size() == 0) {
            mBanner.setBackgroundResource(R.mipmap.tu_banner);
            return;
        }
        mBanner.setData(imgUrlList, null);
        mBanner.setAdapter(Fragment1.this);
    }

    /**
     * 判断是否登录，登录了才能跳转到产品详情界面
     *
     * @param sId
     */
    private void isLogin(String sId) {
        if (!SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
            ActivityUtil.goForward(mActivity, LoginActivity.class, null, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(InitDatas.PROUDUCT_ID, sId);
            ActivityUtil.goForward(mActivity, ProductInfoActivity.class, bundle, false);
        }
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

    /**
     * 跳转到产品列表界面
     *
     * @param sId loanType
     */
    private void goList(String sId) {
        Bundle bundle = new Bundle();
        bundle.putString(InitDatas.PROUDUCT_TYPE_ID, sId);
        ActivityUtil.goForward(mActivity, ProductListActivity.class, bundle, false);
    }


    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.mipmap.tu_banner)
                .error(R.mipmap.tu_banner)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {

    }


    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(true);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getBanner();
                        getProductRecommend();
                        getProductType();
                        getCreditCard();
                        mSwipeLayout.setRefreshing(false);
                    }
                });

    }


    /**
     * 信用卡推荐
     */
    private void getCreditCard() {
        Map<String, String> params = new HashMap<>();
        params.put("flag", "2");
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_FIND_BANK_BY_POSITION, params, GET_CREDIT_CARD);
    }

    /**
     * 产品分类
     */
    private void getProductType() {
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_TYPE, null, GET_PRODUCT_TYPE);
    }

    /**
     * 获取产品推荐
     */
    private void getProductRecommend() {
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_RECOMMEND, null, GET_PRODUCT_RECOMMEND);
    }

    /**
     * 获取Banner
     */
    private void getBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("position", "dsqb_android_center");
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_BANNER, params, GET_BANNER);
    }




    @OnClick(R.id.empty_layout)
    public void onViewClicked() {
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefresh();
    }

}
