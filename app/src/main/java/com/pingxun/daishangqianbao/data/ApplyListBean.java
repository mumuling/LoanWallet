package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/4.
 * 申请记录列表数据源
 */

public class ApplyListBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : {"content":[{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":53,"productId":89,"productName":"房金所","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91mBP-mAXvNYAACk7NZH5EA512.png","serviceRate":0.42,"startPeriod":1,"endPeriod":60,"periodType":"月","channelNo":"android","startAmount":100000,"endAmount":5.0E7,"loanDay":"1-3","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:05:26","appName":"DSQB"},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":52,"productId":88,"productName":"好易借","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l_3GOAb7_ZAAAlZGfRLTA157.jpg","serviceRate":0.82,"startPeriod":7,"endPeriod":14,"periodType":"天","channelNo":"android","startAmount":500,"endAmount":2000,"loanDay":"1","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:04:57","appName":"DSQB"},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":51,"productId":87,"productName":"魔借","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png","serviceRate":8,"startPeriod":2,"endPeriod":12,"periodType":"月","channelNo":"android","startAmount":3000,"endAmount":200000,"loanDay":"1","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:04:46","appName":"DSQB"}],"last":true,"totalPages":1,"totalElements":3,"sort":[{"direction":"DESC","property":"applyDate","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}],"first":true,"numberOfElements":3,"size":10,"number":0}
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
         * content : [{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":53,"productId":89,"productName":"房金所","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91mBP-mAXvNYAACk7NZH5EA512.png","serviceRate":0.42,"startPeriod":1,"endPeriod":60,"periodType":"月","channelNo":"android","startAmount":100000,"endAmount":5.0E7,"loanDay":"1-3","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:05:26","appName":"DSQB"},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":52,"productId":88,"productName":"好易借","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l_3GOAb7_ZAAAlZGfRLTA157.jpg","serviceRate":0.82,"startPeriod":7,"endPeriod":14,"periodType":"天","channelNo":"android","startAmount":500,"endAmount":2000,"loanDay":"1","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:04:57","appName":"DSQB"},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":51,"productId":87,"productName":"魔借","productFlag":"0","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png","serviceRate":8,"startPeriod":2,"endPeriod":12,"periodType":"月","channelNo":"android","startAmount":3000,"endAmount":200000,"loanDay":"1","userId":29,"loanAmount":null,"applyArea":"0.0；0.0","applyDate":"2017-08-04 17:04:46","appName":"DSQB"}]
         * last : true
         * totalPages : 1
         * totalElements : 3
         * sort : [{"direction":"DESC","property":"applyDate","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
         * first : true
         * numberOfElements : 3
         * size : 10
         * number : 0
         */

        private boolean last;
        private int totalPages;
        private int totalElements;
        private boolean first;
        private int numberOfElements;
        private int size;
        private int number;
        private List<ContentBean> content;
        private List<SortBean> sort;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public List<SortBean> getSort() {
            return sort;
        }

        public void setSort(List<SortBean> sort) {
            this.sort = sort;
        }

        public static class ContentBean {
            /**
             * pageNo : null
             * sizePerPage : 10
             * sortDirection : ASC
             * sortFields : null
             * id : 53
             * productId : 89
             * productName : 房金所
             * productFlag : 0
             * img : http://119.23.64.92:8099/group1/M00/00/01/rBKc91mBP-mAXvNYAACk7NZH5EA512.png
             * serviceRate : 0.42
             * startPeriod : 1
             * endPeriod : 60
             * periodType : 月
             * channelNo : android
             * startAmount : 100000.0
             * endAmount : 5.0E7
             * loanDay : 1-3
             * userId : 29
             * loanAmount : null
             * applyArea : 0.0；0.0
             * applyDate : 2017-08-04 17:05:26
             * appName : DSQB
             */

            private Object pageNo;
            private int sizePerPage;
            private String sortDirection;
            private Object sortFields;
            private int id;
            private int productId;
            private String productName;
            private String productFlag;
            private String img;
            private double serviceRate;
            private int startPeriod;
            private int endPeriod;
            private String periodType;
            private String channelNo;
            private double startAmount;
            private double endAmount;
            private String loanDay;
            private int userId;
            private Object loanAmount;
            private String applyArea;
            private String applyDate;
            private String appName;

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

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductFlag() {
                return productFlag;
            }

            public void setProductFlag(String productFlag) {
                this.productFlag = productFlag;
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

            public String getChannelNo() {
                return channelNo;
            }

            public void setChannelNo(String channelNo) {
                this.channelNo = channelNo;
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public Object getLoanAmount() {
                return loanAmount;
            }

            public void setLoanAmount(Object loanAmount) {
                this.loanAmount = loanAmount;
            }

            public String getApplyArea() {
                return applyArea;
            }

            public void setApplyArea(String applyArea) {
                this.applyArea = applyArea;
            }

            public String getApplyDate() {
                return applyDate;
            }

            public void setApplyDate(String applyDate) {
                this.applyDate = applyDate;
            }

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
            }
        }

        public static class SortBean {
            /**
             * direction : DESC
             * property : applyDate
             * ignoreCase : false
             * nullHandling : NATIVE
             * ascending : false
             */

            private String direction;
            private String property;
            private boolean ignoreCase;
            private String nullHandling;
            private boolean ascending;

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public boolean isIgnoreCase() {
                return ignoreCase;
            }

            public void setIgnoreCase(boolean ignoreCase) {
                this.ignoreCase = ignoreCase;
            }

            public String getNullHandling() {
                return nullHandling;
            }

            public void setNullHandling(String nullHandling) {
                this.nullHandling = nullHandling;
            }

            public boolean isAscending() {
                return ascending;
            }

            public void setAscending(boolean ascending) {
                this.ascending = ascending;
            }
        }
    }
}
