package com.pingxun.daishangqianbao.other;

/**
 * Created by LH on 2017/7/31.
 */

public class Urls {

    public static final String SERVER = "https://192.168.1.100:1234/";
    public static final String URL_GET_BANNER=SERVER+"common/findBanner.json";//获取Banner
    public static final String URL_GET_PRODUCT_RECOMMEND=SERVER+"front/product/findRecommendProduct.json";//获取产品推荐
    public static final String URL_GET_PRODUCT_TYPE=SERVER+"front/product/findProductType.json";//产品分类(用钱不必等那一栏)
    public static final String URL_GET_PROUDCT_FIND=SERVER+"front/product/findByCondition.json";//产品搜索
    public static final String URL_GET_FINDBANK_BY_POSITION=SERVER+"front/creditCard/findBankByPosition.json";//查询银行接口



    public static final String URL_POST_CREDIT_CARD=SERVER+"front/creditCard/findRecommend.json";//查询信用卡推荐服务


}
