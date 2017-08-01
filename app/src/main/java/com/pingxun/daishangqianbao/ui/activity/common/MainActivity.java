package com.pingxun.daishangqianbao.ui.activity.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.ui.fragment.fragment1.Fragment1;
import com.pingxun.daishangqianbao.ui.fragment.fragment2.Fragment2;
import com.pingxun.daishangqianbao.ui.fragment.fragment3.Fragment3;
import com.pingxun.daishangqianbao.ui.fragment.fragment4.Fragment4;
import com.pingxun.daishangqianbao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_framelayout)
    FrameLayout mMainFramelayout;
    @BindView(R.id.main_rb_1)
    RadioButton mMainRb1;//产品大全
    @BindView(R.id.main_rb_2)
    RadioButton mMainRb2;//精准
    @BindView(R.id.main_rb_3)
    RadioButton mMainRb3;//信用卡
    @BindView(R.id.main_rb_4)
    RadioButton mMainRb4;//我的
    @BindView(R.id.main_rg)
    RadioGroup mMainRg;



    private long exitTime = 0;
    private Fragment1 mFragment1;
    private Fragment2 mFragment2;
    private Fragment3 mFragment3;
    private Fragment4 mFragment4;

    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    public final static int TAB_ONE = 0;     //产品大全
    public final static int TAB_TWO = 1;     //精准
    public final static int TAB_THREE = 2;   //信用卡
    public final static int TAB_FOUR = 3;    //我的

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();
        setChioceItem(TAB_ONE);   // 初始化页面加载时显示第一个选项卡
    }


    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);

        switch (index) {
            case TAB_ONE:
                // 如果fg1为空，则创建一个并添加到界面上
                if (mFragment1 == null) {
                    mFragment1 = new Fragment1();
                    fragmentTransaction.add(R.id.main_framelayout, mFragment1);
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(mFragment1);
                }
                break;

            case TAB_TWO:
                if (mFragment2 == null) {
                    mFragment2 = new Fragment2();
                    fragmentTransaction.add(R.id.main_framelayout, mFragment2);
                } else {
                    fragmentTransaction.show(mFragment2);
                }
                break;

            case TAB_THREE:
                if (mFragment3 == null) {
                    mFragment3 = new Fragment3();
                    fragmentTransaction.add(R.id.main_framelayout, mFragment3);
                } else {
                    fragmentTransaction.show(mFragment3);
                }
                break;

            case TAB_FOUR:
                if (mFragment4 == null) {
                    mFragment4 = new Fragment4();
                    fragmentTransaction.add(R.id.main_framelayout, mFragment4);
                } else {
                    fragmentTransaction.show(mFragment4);
                }
                break;
        }
        fragmentTransaction.commit();   // 提交

    }

    /**
     * 隐藏Fragment
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mFragment1 != null) {
            fragmentTransaction.hide(mFragment1);
        }

        if (mFragment2 != null) {
            fragmentTransaction.hide(mFragment2);
        }

        if (mFragment3 != null) {
            fragmentTransaction.hide(mFragment3);
        }

        if (mFragment4 != null) {
            fragmentTransaction.hide(mFragment4);
        }
    }

    /**
     * 双击退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (System.currentTimeMillis() - exitTime > 2000) { // 2s内再次选择back键有效
                ToastUtils.showToast(me, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                closeActivtiy();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.main_rb_1, R.id.main_rb_2, R.id.main_rb_3, R.id.main_rb_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_rb_1:
                setChioceItem(TAB_ONE);
                break;
            case R.id.main_rb_2:
                setChioceItem(TAB_TWO);
                break;
            case R.id.main_rb_3:
                setChioceItem(TAB_THREE);
                break;
            case R.id.main_rb_4:
                setChioceItem(TAB_FOUR);
                break;
        }
    }



}
