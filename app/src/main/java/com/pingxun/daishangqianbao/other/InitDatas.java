package com.pingxun.daishangqianbao.other;

import android.graphics.Bitmap;

import com.pingxun.daishangqianbao.data.F2JobDataBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 初始数据
 */

public class InitDatas {



    /**
     * 贷款最低金额
     */
    public static int limitLowAmount=1000;


    /**
     * 贷款最高金额
     */
    public static int limitHeightAmount=50000;

    /**
     * 日 单位 起始
     */
    public static String dayStart="1天";
    /**
     * 日 单位 结束
     */
    public static String dayEnd="360天";

    /**
     * 月 单位 开始
     */
    public static String mounthStart="1月";
    /**
     * 月 单位 结束
     */
    public static String mounthEnd="36月";

    /**
     * 贷款单位
     */
    public static int loanUnit=0;

    /**
     * 贷款期限
     */
    public static int loanDate=0;

    /**
     * 贷款金额 0 天 1 月 2 年
     */
    public static int loanAmount=0;

    /**
     * 拖动条拖动的比例
     */
    public static int progressData=0;

    /**
     * 请求结果
     */
    public static String requestResult="";

    /**
     * 产品列表总页数
     */
    public static int totalPages;

    /**
     * 产品列表总条数
     */
    public static int totalElements;

    /**
     * 每页条数
     */
    public static int size;

    /**
     * 当前页数
     */
    public static int number;

    /**
     * 网络资源图片
     */
    public static Bitmap bitmap;

    /**
     * 服务器session
     */
    public static String JSESSIONID;

    /**
     * 当前用户手机号
     */
    public static String currentPhoneNum;

    /**
     * 年利率
     */
    public static BigDecimal rateYear;

    /**
     * 日利率
     */
    public static BigDecimal rateDay=BigDecimal.ONE;

    /**
     * 月利率
     */
    public static BigDecimal rateMounth;
    /**
     * 职业选择缓存数据
     */
    public static List<F2JobDataBean.DataBean> jobTypeData;
    /**
     * 是否已经拉取首页初始化数据
     */
    public static boolean isPullInitData=false;

    /**
     * 经度
     */
    public static double longitude=0.0;
    /**
     * 纬度
     */
    public static double latitude=0.0;

    /**
     *
     */
    public static int permissionRequestResult=0;

    public static boolean isLogin=false;

    public static String APP_NAME="DSQB";
    public static String CHANNEL_NO="android";//渠道类型：ios,android,wechat
    public static String ErrorMsg="获取信息失败!";
    public static String SP_NAME=APP_NAME+"_UserInfo";//sp文件名

    //用于SP存储用户信息的Key
    public static String UserPhone=APP_NAME+"_Phone";//用户手机号key
    public static String UserPw=APP_NAME+"_Pw";//用户验证码(密码)key
    public static String UserIsLogin=APP_NAME+"_IsLogin";//用户是否登录key

    //用于bundle传值的key
    public static String PERIOD="period";
    public static String DATECYCLE="dateCycle";
    public static String AMOUNT="amount";
    public static String PROUDUCT_ID="id";//产品ID

    /**
     * 渠道编号
     */
   // public static final String CURRENT_MARKET= ENUM_SOURCE_MARKET_CODE.SAMSUNG.value;

}
