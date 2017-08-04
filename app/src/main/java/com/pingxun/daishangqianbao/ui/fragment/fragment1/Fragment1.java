package com.pingxun.daishangqianbao.ui.fragment.fragment1;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.adapter.F1_Type_RecyclerViewAdapter;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.BannerBean;
import com.pingxun.daishangqianbao.data.F1ProductTypeBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;
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

    private F1_Type_RecyclerViewAdapter mTypeAdapter;
    private List<F1ProductTypeBean.DataBean> mTypeList;


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initData() {
        mSwipeLayout.setColorSchemeResources(R.color.tab_font_bright);
        mSwipeLayout.setOnRefreshListener(this);
        onRefresh();
        initAdapter();
    }



    @OnClick({R.id.rb_dydk, R.id.rb_gxdk, R.id.rb_xydk, R.id.rb_xesd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_dydk://抵押

                break;
            case R.id.rb_gxdk://工薪

                break;
            case R.id.rb_xydk://信用

                break;
            case R.id.rb_xesd://小额

                break;
        }
    }

    private void initAdapter() {
        mTypeAdapter = new F1_Type_RecyclerViewAdapter(R.layout.rv_item_f1_product_type, mTypeList);
     //   mCardAdapter =new F3_Card_RecyclerViewAdapter(R.layout.rv_item_credit_card,mCardList);

        mRv1.setHasFixedSize(true);
        mRv1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRv1.setAdapter(mTypeAdapter);
        mRv1.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(mActivity, mTypeList.get(position).getName());
//                Bundle bundle=new Bundle();
//                bundle.putString(InitDatas.PROUDUCT_ID,String.valueOf(mListBean.get(position).getId()));
//                ActivityUtil.goForward(me,ProductInfoActivity.class,bundle,false);
            }
        });

    }

    /**
     * 信用卡推荐
     */
    private void getCreditCard() {
        Map<String, String> params = new HashMap<>();
        params.put("flag", "推荐银行");
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_FINDBANK_BY_POSITION, mActivity, params, GET_CREDIT_CARD);
    }

    /**
     * 产品分类
     */
    private void getProductType() {
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_TYPE,null, GET_PRODUCT_TYPE);
    }

    /**
     * 获取产品推荐
     */
    private void getProductRecommend() {
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_RECOMMEND,null, GET_PRODUCT_RECOMMEND);
    }

    /**
     * 获取Banner
     */
    private void getBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("position", "dsqb_android_center");
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_BANNER, params, GET_BANNER);
    }


    @Override
    public void onResult(String jsonStr, int flag) {
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

            case GET_PRODUCT_RECOMMEND://产品推荐
                Logger.json(jsonStr);
                break;


            case GET_PRODUCT_TYPE://产品分类回调
                F1ProductTypeBean mBean=Convert.fromJson(jsonStr,F1ProductTypeBean.class);
                if (mBean==null || !mBean.isSuccess()){
                    ToastUtils.showToast(mActivity,"获取产品分类失败");
                    return;
                }
                if (mBean.isSuccess()){
                    mTypeList = mBean.getData();
                    mTypeAdapter.setNewData(mTypeList);
                }
                break;

            case GET_CREDIT_CARD://信用卡推荐

//                Logger.json(jsonStr);
                break;


        }

    }


    @Override
    public void onError(int flag) {
        Logger.d(flag);
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
        mBanner.setData(imgUrlList, null);
        mBanner.setAdapter(Fragment1.this);
    }


    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.mipmap.tu_jiazai)
                .error(R.mipmap.tu_jiazai)
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
//                        getCreditCard();
                        mSwipeLayout.setRefreshing(false);
                    }
                });

    }



}
