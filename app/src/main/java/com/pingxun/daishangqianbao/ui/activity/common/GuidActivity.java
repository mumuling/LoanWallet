package com.pingxun.daishangqianbao.ui.activity.common;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.other.InitDatas;
import com.pingxun.daishangqianbao.utils.ActivityUtil;
import com.pingxun.daishangqianbao.utils.SharedPrefsUtil;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 引导页
 */
public class GuidActivity extends BaseActivity {
    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_guid;
    }

    @Override
    protected void initData() {

        SharedPrefsUtil.putValue(me, InitDatas.SP_NAME, InitDatas.UserIsFirstSplash, true);//第一次进入APP将值设为true
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        bannerGuideBackground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                ActivityUtil.goForward(me,MainActivity.class,null,true);
            }
        });
        bannerGuideBackground.setData(R.mipmap.guid_1,R.mipmap.guid_2,R.mipmap.guid_3);
    }




}
