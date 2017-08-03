package com.pingxun.daishangqianbao.data;

/**
 * Created by LH on 2017/8/3.
 * 产品详情数据源
 */

public class ProductInfoBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : {"id":87,"name":"魔借","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png","detailImg":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wyAVGd6AAEJyMl2Erg493.png","productType":"","productSubType":null,"serviceType":"day","serviceRate":8,"rateMemo":"年化利率","periodType":"month","periodTypeStr":"月","startPeriod":2,"endPeriod":12,"startAmount":3000,"endAmount":200000,"loanDay":"1","applyFlow":"1.首页登录 2.手机号注册 3.申请借款 4.身份认证 5.基本信息填写 6.四要素认证 7.签约 8.放款","applyCondition":"有信用卡、还款正常的客户","applyMaterial":"身份证、信用卡","sourceType":null,"channel":"点融网魔","url":"https://www.dianrong.com/mkt/bor_mojie_v3/index.html?clientsourcetype=BD&referredBy=d_pingxun","showOrder":15,"viewNum":50,"clickNum":12,"startDate":null,"endDate":null,"isValid":true,"androidFlag":true,"appleFlag":true,"wechatFlag":true,"productFlag":"0","belongApp":"MBD,XSD,JDS,JQW,DSQB","showOrderJds":25,"showOrderXsd":21,"showOrderJqw":24,"showOrderDkqb":null,"showOrderDsqb":21,"showOrderLsqd":null,"showOrderLyb":null,"isRecommend":null}
     */

    private boolean success;
    private String code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 87
         * name : 魔借
         * img : http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png
         * detailImg : http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wyAVGd6AAEJyMl2Erg493.png
         * productType :
         * productSubType : null
         * serviceType : day
         * serviceRate : 8.0
         * rateMemo : 年化利率
         * periodType : month
         * periodTypeStr : 月
         * startPeriod : 2
         * endPeriod : 12
         * startAmount : 3000.0
         * endAmount : 200000.0
         * loanDay : 1
         * applyFlow : 1.首页登录 2.手机号注册 3.申请借款 4.身份认证 5.基本信息填写 6.四要素认证 7.签约 8.放款
         * applyCondition : 有信用卡、还款正常的客户
         * applyMaterial : 身份证、信用卡
         * sourceType : null
         * channel : 点融网魔
         * url : https://www.dianrong.com/mkt/bor_mojie_v3/index.html?clientsourcetype=BD&referredBy=d_pingxun
         * showOrder : 15
         * viewNum : 50
         * clickNum : 12
         * startDate : null
         * endDate : null
         * isValid : true
         * androidFlag : true
         * appleFlag : true
         * wechatFlag : true
         * productFlag : 0
         * belongApp : MBD,XSD,JDS,JQW,DSQB
         * showOrderJds : 25
         * showOrderXsd : 21
         * showOrderJqw : 24
         * showOrderDkqb : null
         * showOrderDsqb : 21
         * showOrderLsqd : null
         * showOrderLyb : null
         * isRecommend : null
         */

        private int id;
        private String name;
        private String img;
        private String detailImg;
        private String productType;
        private Object productSubType;
        private String serviceType;
        private double serviceRate;
        private String rateMemo;
        private String periodType;
        private String periodTypeStr;
        private int startPeriod;
        private int endPeriod;
        private double startAmount;
        private double endAmount;
        private String loanDay;
        private String applyFlow;
        private String applyCondition;
        private String applyMaterial;
        private Object sourceType;
        private String channel;
        private String url;
        private int showOrder;
        private int viewNum;
        private int clickNum;
        private Object startDate;
        private Object endDate;
        private boolean isValid;
        private boolean androidFlag;
        private boolean appleFlag;
        private boolean wechatFlag;
        private String productFlag;
        private String belongApp;
        private int showOrderJds;
        private int showOrderXsd;
        private int showOrderJqw;
        private Object showOrderDkqb;
        private int showOrderDsqb;
        private Object showOrderLsqd;
        private Object showOrderLyb;
        private Object isRecommend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDetailImg() {
            return detailImg;
        }

        public void setDetailImg(String detailImg) {
            this.detailImg = detailImg;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public Object getProductSubType() {
            return productSubType;
        }

        public void setProductSubType(Object productSubType) {
            this.productSubType = productSubType;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public String getRateMemo() {
            return rateMemo;
        }

        public void setRateMemo(String rateMemo) {
            this.rateMemo = rateMemo;
        }

        public String getPeriodType() {
            return periodType;
        }

        public void setPeriodType(String periodType) {
            this.periodType = periodType;
        }

        public String getPeriodTypeStr() {
            return periodTypeStr;
        }

        public void setPeriodTypeStr(String periodTypeStr) {
            this.periodTypeStr = periodTypeStr;
        }

        public int getStartPeriod() {
            return startPeriod;
        }

        public void setStartPeriod(int startPeriod) {
            this.startPeriod = startPeriod;
        }

        public int getEndPeriod() {
            return endPeriod;
        }

        public void setEndPeriod(int endPeriod) {
            this.endPeriod = endPeriod;
        }

        public double getStartAmount() {
            return startAmount;
        }

        public void setStartAmount(double startAmount) {
            this.startAmount = startAmount;
        }

        public double getEndAmount() {
            return endAmount;
        }

        public void setEndAmount(double endAmount) {
            this.endAmount = endAmount;
        }

        public String getLoanDay() {
            return loanDay;
        }

        public void setLoanDay(String loanDay) {
            this.loanDay = loanDay;
        }

        public String getApplyFlow() {
            return applyFlow;
        }

        public void setApplyFlow(String applyFlow) {
            this.applyFlow = applyFlow;
        }

        public String getApplyCondition() {
            return applyCondition;
        }

        public void setApplyCondition(String applyCondition) {
            this.applyCondition = applyCondition;
        }

        public String getApplyMaterial() {
            return applyMaterial;
        }

        public void setApplyMaterial(String applyMaterial) {
            this.applyMaterial = applyMaterial;
        }

        public Object getSourceType() {
            return sourceType;
        }

        public void setSourceType(Object sourceType) {
            this.sourceType = sourceType;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }

        public int getViewNum() {
            return viewNum;
        }

        public void setViewNum(int viewNum) {
            this.viewNum = viewNum;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
        }

        public boolean isAndroidFlag() {
            return androidFlag;
        }

        public void setAndroidFlag(boolean androidFlag) {
            this.androidFlag = androidFlag;
        }

        public boolean isAppleFlag() {
            return appleFlag;
        }

        public void setAppleFlag(boolean appleFlag) {
            this.appleFlag = appleFlag;
        }

        public boolean isWechatFlag() {
            return wechatFlag;
        }

        public void setWechatFlag(boolean wechatFlag) {
            this.wechatFlag = wechatFlag;
        }

        public String getProductFlag() {
            return productFlag;
        }

        public void setProductFlag(String productFlag) {
            this.productFlag = productFlag;
        }

        public String getBelongApp() {
            return belongApp;
        }

        public void setBelongApp(String belongApp) {
            this.belongApp = belongApp;
        }

        public int getShowOrderJds() {
            return showOrderJds;
        }

        public void setShowOrderJds(int showOrderJds) {
            this.showOrderJds = showOrderJds;
        }

        public int getShowOrderXsd() {
            return showOrderXsd;
        }

        public void setShowOrderXsd(int showOrderXsd) {
            this.showOrderXsd = showOrderXsd;
        }

        public int getShowOrderJqw() {
            return showOrderJqw;
        }

        public void setShowOrderJqw(int showOrderJqw) {
            this.showOrderJqw = showOrderJqw;
        }

        public Object getShowOrderDkqb() {
            return showOrderDkqb;
        }

        public void setShowOrderDkqb(Object showOrderDkqb) {
            this.showOrderDkqb = showOrderDkqb;
        }

        public int getShowOrderDsqb() {
            return showOrderDsqb;
        }

        public void setShowOrderDsqb(int showOrderDsqb) {
            this.showOrderDsqb = showOrderDsqb;
        }

        public Object getShowOrderLsqd() {
            return showOrderLsqd;
        }

        public void setShowOrderLsqd(Object showOrderLsqd) {
            this.showOrderLsqd = showOrderLsqd;
        }

        public Object getShowOrderLyb() {
            return showOrderLyb;
        }

        public void setShowOrderLyb(Object showOrderLyb) {
            this.showOrderLyb = showOrderLyb;
        }

        public Object getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(Object isRecommend) {
            this.isRecommend = isRecommend;
        }
    }
}
