package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/1.
 * 轮播图数据源
 */

public class BannerBean {

    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : [{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":31,"name":"信用卡","bannerImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKY4qATQf7AAHPVLzkvFs338.png","bannerPosition":"center","showOrder":1,"isValid":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":32,"name":"信用卡","bannerImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKY8KAIAkXAAG0ALF76aw590.png","bannerPosition":"center","showOrder":2,"isValid":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":33,"name":"信用卡","bannerImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKY-qAHNbjAAINcAgkwQ8294.png","bannerPosition":"center","showOrder":3,"isValid":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":34,"name":"信用卡","bannerImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZZOAGUyRAAEjbyXd8AQ380.png","bannerPosition":"center","showOrder":4,"isValid":true}]
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
         * pageNo : null
         * sizePerPage : 10
         * sortDirection : ASC
         * sortFields : null
         * id : 31
         * name : 信用卡
         * bannerImg : http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKY4qATQf7AAHPVLzkvFs338.png
         * bannerPosition : center
         * showOrder : 1
         * isValid : false
         */

        private Object pageNo;
        private int sizePerPage;
        private String sortDirection;
        private Object sortFields;
        private int id;
        private String name;
        private String bannerImg;
        private String bannerPosition;
        private int showOrder;
        private boolean isValid;

        public Object getPageNo() {
            return pageNo;
        }

        public void setPageNo(Object pageNo) {
            this.pageNo = pageNo;
        }

        public int getSizePerPage() {
            return sizePerPage;
        }

        public void setSizePerPage(int sizePerPage) {
            this.sizePerPage = sizePerPage;
        }

        public String getSortDirection() {
            return sortDirection;
        }

        public void setSortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
        }

        public Object getSortFields() {
            return sortFields;
        }

        public void setSortFields(Object sortFields) {
            this.sortFields = sortFields;
        }

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

        public String getBannerImg() {
            return bannerImg;
        }

        public void setBannerImg(String bannerImg) {
            this.bannerImg = bannerImg;
        }

        public String getBannerPosition() {
            return bannerPosition;
        }

        public void setBannerPosition(String bannerPosition) {
            this.bannerPosition = bannerPosition;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
        }
    }
}
