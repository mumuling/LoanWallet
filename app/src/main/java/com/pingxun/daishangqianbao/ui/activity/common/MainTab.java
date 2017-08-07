package com.pingxun.daishangqianbao.ui.activity.common;


import com.pingxun.daishangqianbao.R;
import com.pingxun.daishangqianbao.ui.fragment.fragment1.Fragment1;
import com.pingxun.daishangqianbao.ui.fragment.fragment2.Fragment2;
import com.pingxun.daishangqianbao.ui.fragment.fragment3.Fragment3;
import com.pingxun.daishangqianbao.ui.fragment.fragment4.Fragment4;

public enum MainTab {

    FRAGMENT1(0, R.string.home_tab_1, R.drawable.activity_main_one, Fragment1.class),
    FRAGMENT2(1, R.string.home_tab_2, R.drawable.activity_main_two, Fragment2.class),
    FRAGMENT3(2, R.string.home_tab_3, R.drawable.activity_main_three, Fragment3.class),
    FRAGMENT4(3,R.string.home_tab_4,R.drawable.activity_main_four , Fragment4.class);

    private final int index;
    private final int resName;
    private final int resIcon;
    private final Class<?> clazz;

    MainTab(int index, int resName, int resIcon, Class<?> clazz) {
        this.index = index;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clazz = clazz;
    }

    public int getIndex() {
        return index;
    }

    public int getResName() {
        return resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}
