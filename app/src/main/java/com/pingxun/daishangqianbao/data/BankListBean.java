package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class BankListBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : [{"id":1,"name":"123","icon":"","url":"1","isValid":true,"isRecommend":true,"showOrder":1},{"id":2,"name":"123","icon":"","url":"1","isValid":true,"isRecommend":true,"showOrder":2},{"id":3,"name":"123","icon":"","url":"1","isValid":true,"isRecommend":true,"showOrder":3}]
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
         * id : 1
         * name : 123
         * icon :
         * url : 1
         * isValid : true
         * isRecommend : true
         * showOrder : 1
         */

        private int id;
        private String name;
        private String icon;
        private String url;
        private boolean isValid;
        private boolean isRecommend;
        private int showOrder;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
        }

        public boolean isIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(boolean isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }
    }
}
