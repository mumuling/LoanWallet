package com.pingxun.daishangqianbao.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingxun.daishangqianbao.R;


/**
 * Created by wang on 2015/10/22.
 * 错误提示页面
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener {
    /**
     * 表示没有错误的状态
     */
    public static final int NO_ERROR = -1;

    /**
     * 错误提示的状体，隐藏view
     */
    public static final int HIDE_LAYOUT = 4;

    /**
     * 没有网络连接的错误提示
     */
    public static final int NETWORK_ERROR = 1;

    /**
     * 网络加载中的错误提示
     */
    public static final int NETWORK_LOADING = 2;

    /**
     * 没有数据的错误提示
     */
    public static final int NODATA = 3;

    /**
     * 没有错误并且可以点击的错误提示
     */
    public static final int NODATA_ENABLE_CLICK = 5;

    /**
     * 没有登录状态提示
     */
    public static final int NO_LOGIN = 6;
    /**
     * 没有上传图片到沃摄云盘，暂无数据
     */
    public static final int NOWOSHEDATA = 7;
    /**
     * 进度条
     */
    private ProgressBar animProgress;

    /**
     * 是否可以点击
     */
    private boolean clickEnable = true;
    private final Context context;
    /**
     * 错误图片
     */
    public ImageView img;
    private OnClickListener listener;
    /**
     * 错误状态
     */
    private int mErrorState;
    /**
     * 整个布局的view对象
     */
    @SuppressWarnings("unused")
    private RelativeLayout mLayout;
    private String strNoDataContent = "";
    /**
     * 错误提示的textview
     */
    private TextView tv;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * 初始化各个view控件，设置图片的点击事件
     */
    private void init() {
        View view = View.inflate(context, R.layout.view_error_layout, null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress);

        setOnClickListener(this);
        //设置图片点击事件
//        img.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clickEnable) {
//                    //setErrorType(NETWORK_LOADING)
//                    if (listener != null) {
//                        listener.onClick(v);
//                    }
//                }
//            }
//        });

        addView(view);
        changeErrorLayoutBgMode(context);
    }

    /**
     * 没有实现这个方法
     */
    public void changeErrorLayoutBgMode(Context context) {

    }

    /**
     * 隐藏这个布局view
     */
    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // MyApplication.getInstance().getAtSkinObserable().registered(this);
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // MyApplication.getInstance().getAtSkinObserable().unregistered(this);
    }

    /**
     * 改变皮肤(没有实现)
     */
    public void onSkinChanged() {
        // mLayout.setBackgroundColor(SkinsUtil
        // .getColor(getContext(), "bgcolor01"));
        // tv.setTextColor(SkinsUtil.getColor(getContext(), "textcolor05"));
    }

    public void setDayNight(boolean flag) {
    }

    /**
     * 设置错误信息
     *
     * @param msg
     */
    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    /**
     * 新添设置背景
     *
     * @author 火蚁 2015-1-27 下午2:14:00
     */
    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    /**
     * 设置错误的状态
     */
    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;

                tv.setText(R.string.error_view_network_error_click_to_refresh);
                img.setBackgroundResource(R.mipmap.error);

                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                tv.setText(R.string.error_view_loading);
                clickEnable = false;
                break;
            case NO_ERROR:
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.nodata);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.mipmap.error);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NO_LOGIN:
                mErrorState = NO_LOGIN;
                img.setBackgroundResource(R.mipmap.nodata);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoLoginContent();
                clickEnable = true;
                break;
            case NOWOSHEDATA:
                mErrorState = NOWOSHEDATA;
                animProgress.setVisibility(View.GONE);
                tv.setText(R.string.error_view_no_woshe);
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    /**
     * 设置没有数据时的内容
     *
     * @param noDataContent
     */
    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    /**
     * 设置布局点击的监听
     *
     * @param listener
     */
    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置没有登录时的文字错误提示，如果有文字，就显示文字，没有显示默认文字
     */
    public void setTvNoLoginContent() {
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else
            tv.setText("未登录");
    }

    /**
     * 设置没有数据时的文字错误提示，如果有文字，就显示文字，没有显示默认文字
     */
    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else
            tv.setText("没有数据");
    }

    /**
     * 设置布局是否可见
     */
    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
