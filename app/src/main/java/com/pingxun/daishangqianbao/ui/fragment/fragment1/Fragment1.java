package com.pingxun.daishangqianbao.ui.fragment.fragment1;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.base.BaseFragment;
import com.pingxun.daishangqianbao.callback.StringDialogCallback;
import com.pingxun.daishangqianbao.data.BannerBean;
import com.pingxun.daishangqianbao.other.Urls;
import com.pingxun.daishangqianbao.utils.Convert;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class Fragment1 extends BaseFragment {
    BannerBean mBannerBean;
    List<BannerBean.DataBean> mBeanList;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initData() {
        getBanner(mActivity);
    }

    /**
     * 获取通用Banner
     * @param activity
     */
    private void getBanner(BaseActivity activity) {
        OkGo.<String>get(Urls.URL_GET_BANNER)
                .tag(this)
                .params("position","center")
                .execute(new StringDialogCallback(activity) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.json(response.body());
                        mBannerBean= Convert.fromJson(response.body(),BannerBean.class);
                        if (mBannerBean.isSuccess()){
                            mBeanList=mBannerBean.getData();
                            for (int i = 0; i < mBeanList.size(); i++) {
                               Logger.d(mBeanList.get(i).getBannerImg());
                            }
                        }
                    }
                });


    }


}
