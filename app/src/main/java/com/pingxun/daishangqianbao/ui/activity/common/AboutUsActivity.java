package com.pingxun.daishangqianbao.ui.activity.common;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {
        initTopView("关于我们");
    }
}
