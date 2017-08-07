package com.pingxun.daishangqianbao.other;

/**
 * Created by LH on 2017/7/31.
 */

public class Urls {
    //GET接口
    //"https://119.23.64.92/"外网
    //"https://192.168.1.100:1234/"内网
    public static final String SERVER = "https://192.168.1.100:1234/";
    public static final String URL_GET_BANNER=SERVER+"common/findBanner.json";//获取Banner
    public static final String URL_GET_PRODUCT_RECOMMEND=SERVER+"front/product/findRecommendProduct.json";//获取产品推荐
    public static final String URL_GET_PRODUCT_TYPE=SERVER+"front/product/findProductType.json";//产品分类(用钱不必等那一栏)
    public static final String URL_GET_FINDBANK_BY_POSITION=SERVER+"front/creditCard/findBankByPosition.json";//查询银行接口
    public static final String URL_GET_FIND_PARAMETER=SERVER+"front/parameter/findParameter.json";//首页请求参数配置接口
    public static final String URL_GET_FIND_BY_TYPE=SERVER+"common/findByType.json";//通用下拉列表数据
    public static final String URL_GET_FIND_BY_ID=SERVER+"front/product/findById.json";//查询详情
    public static final String URL_GET_FIND_BANK_BY_POSITION=SERVER+"front/creditCard/findBankByPosition.json";//查询银行接口


    //post接口
    public static final String URL_POST_SEND_SMS=SERVER+"common/sendSmsVerify.json";//发送验证码
    public static final String URL_POST_LOGIN=SERVER+"common/passwordLogin.json";//登录
    public static final String URL_POST_FIND_BY_CONDITION=SERVER+"front/product/findByCondition.json";//产品搜索
    public static final String URL_POST_CREDIT_CARD=SERVER+"front/creditCard/findRecommend.json";//查询信用卡推荐服务
    public static final String URL_POST_FIND_BY_CONDITION_CARD=SERVER+"front/creditCard/findByCondition.json";//查询信用卡列表服务
    public static final String URL_POST_APPLY_LOAN=SERVER+"front/product/applyLoan.json";//立即申请产品
    public static final String URL_POST_FIND_APPLY_LIST=SERVER+"front/product/findApplyList.json";//获取申请列表
    public static final String URL_POST_IS_APPLY=SERVER+"front/product/applyInvestigation.json";//产品申请调查接口

}
