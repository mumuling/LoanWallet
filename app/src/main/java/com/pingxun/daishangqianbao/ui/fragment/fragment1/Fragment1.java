package com.pingxun.daishangqianbao.ui.fragment.fragment1;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.data.BannerBean;
import com.pingxun.daishangqianbao.other.G_api;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by LH on 2017/7/31.
 * 产品大全主页
 */
public class Fragment1 extends BaseFragment implements G_api.OnResultHandler, BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    private BannerBean mBannerBean;
    private List<BannerBean.DataBean> mBannerlist;
    private List<String> imgUrlList = new ArrayList<>();

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


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initData() {
        getBanner();
        getProductRecommend();
        getProductType();
        getCreditCard();
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
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_TYPE, mActivity, null, GET_PRODUCT_TYPE);
    }

    /**
     * 获取产品推荐
     */
    private void getProductRecommend() {
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_PRODUCT_RECOMMEND, mActivity, null, GET_PRODUCT_RECOMMEND);
    }

    /**
     * 获取Banner
     */
    private void getBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("position", "dsqb_android_center");
        G_api.getInstance().setHandleInterface(Fragment1.this).getRequest(Urls.URL_GET_BANNER, mActivity, params, GET_BANNER);
    }


    @Override
    public void onResult(String jsonStr, int flag) {
        switch (flag) {
            case GET_BANNER://获取Banner
                mBannerBean = Convert.fromJson(jsonStr, BannerBean.class);
                if (mBannerBean.getCode().equals("000000")) {
                    mBannerlist = mBannerBean.getData();
                    setShowpic();
                }
                break;
            case GET_PRODUCT_RECOMMEND://产品推荐
//                Logger.json(jsonStr);
                break;
            case GET_PRODUCT_TYPE://产品分类
//                Logger.json(jsonStr);
                break;
            case GET_CREDIT_CARD://信用卡推荐
//                  Logger.json(jsonStr);
                break;
        }

    }




    @Override
    public void onError(int flag) {
        Logger.d(flag);
    }


    /**
     * 设置轮播图
     */
    private void setShowpic() {
        for (int i = 0; i < mBannerlist.size(); i++) {
            //获得每张图片的地址
            String url = mBannerlist.get(i).getBannerImg();
            imgUrlList.add(i, url);
        }
        mBanner.setData(imgUrlList, null);
        mBanner.setAdapter(Fragment1.this);
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

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.mipmap.holder)
                .error(R.mipmap.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {

    }
}
