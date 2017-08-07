package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/7.
 * F1产品推荐数据源
 */

public class F1ProductRecommendBean {


    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : [{"id":11,"name":"一信贷","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l2ud6ALMK2AACk6SqQC6Y399.png","serviceRate":2,"startPeriod":1,"endPeriod":6,"periodType":"月","startAmount":1000,"endAmount":5000,"isValid":true,"viewNum":10,"clickNum":76,"productFlag":"0"},{"id":13,"name":"宜人贷","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l2uEiAKGo6AACqls0aXrc093.jpg","serviceRate":1.89,"startPeriod":12,"endPeriod":48,"periodType":"月","startAmount":10000,"endAmount":200000,"isValid":true,"viewNum":8,"clickNum":21,"productFlag":"0"},{"id":14,"name":"小额钱贷","img":"http://119.23.64.92:8099/group1/M00/00/00/rBKc91l2t3aAJ4qgAAATpxP7lGQ660.png","serviceRate":0.9,"startPeriod":7,"endPeriod":14,"periodType":"日","startAmount":1000,"endAmount":2000,"isValid":true,"viewNum":33,"clickNum":119,"productFlag":"0"},{"id":15,"name":"极速现金侠","img":"http://119.23.64.92:8099/group1/M00/00/00/rBKc91l2tByAVboNAAAsoep-65g809.png","serviceRate":0.3,"startPeriod":14,"endPeriod":21,"periodType":"日","startAmount":200,"endAmount":5000,"isValid":true,"viewNum":29,"clickNum":81,"productFlag":"0"}]
     */

    private boolean success;
    private String code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * name : 一信贷
         * img : http://119.23.64.92:8099/group1/M00/00/01/rBKc91l2ud6ALMK2AACk6SqQC6Y399.png
         * serviceRate : 2.0
         * startPeriod : 1
         * endPeriod : 6
         * periodType : 月
         * startAmount : 1000.0
         * endAmount : 5000.0
         * isValid : true
         * viewNum : 10
         * clickNum : 76
         * productFlag : 0
         */

        private int id;
        private String name;
        private String img;
        private double serviceRate;
        private int startPeriod;
        private int endPeriod;
        private String periodType;
        private double startAmount;
        private double endAmount;
        private boolean isValid;
        private int viewNum;
        private int clickNum;
        private String productFlag;

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

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
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

        public String getPeriodType() {
            return periodType;
        }

        public void setPeriodType(String periodType) {
            this.periodType = periodType;
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

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
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

        public String getProductFlag() {
            return productFlag;
        }

        public void setProductFlag(String productFlag) {
            this.productFlag = productFlag;
        }
    }
}
