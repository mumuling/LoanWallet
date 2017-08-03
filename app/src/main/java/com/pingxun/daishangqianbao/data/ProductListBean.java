package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/2.
 * 产品列表数据源
 */

public class ProductListBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : {"content":[{"id":87,"name":"魔借","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png","serviceRate":8,"startPeriod":2,"endPeriod":12,"periodType":"月","startAmount":3000,"endAmount":200000,"isValid":true,"viewNum":49,"clickNum":12,"productFlag":"0"},{"id":89,"name":"房金所","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91mBP-mAXvNYAACk7NZH5EA512.png","serviceRate":0.42,"startPeriod":1,"endPeriod":60,"periodType":"月","startAmount":100000,"endAmount":5.0E7,"isValid":true,"viewNum":42,"clickNum":10,"productFlag":"0"},{"id":88,"name":"好易借","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l_3GOAb7_ZAAAlZGfRLTA157.jpg","serviceRate":0.82,"startPeriod":7,"endPeriod":14,"periodType":"日","startAmount":500,"endAmount":2000,"isValid":true,"viewNum":35,"clickNum":11,"productFlag":"0"}],"last":true,"totalPages":1,"totalElements":3,"sort":[{"direction":"ASC","property":"showOrderDsqb","ignoreCase":false,"nullHandling":"NATIVE","ascending":true}],"first":true,"numberOfElements":3,"size":10,"number":0}
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
         * content : [{"id":87,"name":"魔借","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png","serviceRate":8,"startPeriod":2,"endPeriod":12,"periodType":"月","startAmount":3000,"endAmount":200000,"isValid":true,"viewNum":49,"clickNum":12,"productFlag":"0"},{"id":89,"name":"房金所","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91mBP-mAXvNYAACk7NZH5EA512.png","serviceRate":0.42,"startPeriod":1,"endPeriod":60,"periodType":"月","startAmount":100000,"endAmount":5.0E7,"isValid":true,"viewNum":42,"clickNum":10,"productFlag":"0"},{"id":88,"name":"好易借","img":"http://119.23.64.92:8099/group1/M00/00/01/rBKc91l_3GOAb7_ZAAAlZGfRLTA157.jpg","serviceRate":0.82,"startPeriod":7,"endPeriod":14,"periodType":"日","startAmount":500,"endAmount":2000,"isValid":true,"viewNum":35,"clickNum":11,"productFlag":"0"}]
         * last : true
         * totalPages : 1
         * totalElements : 3
         * sort : [{"direction":"ASC","property":"showOrderDsqb","ignoreCase":false,"nullHandling":"NATIVE","ascending":true}]
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
             * id : 87
             * name : 魔借
             * img : http://119.23.64.92:8099/group1/M00/00/01/rBKc91l-7wWAYtkiAABP1_8dhwM676.png
             * serviceRate : 8.0
             * startPeriod : 2
             * endPeriod : 12
             * periodType : 月
             * startAmount : 3000.0
             * endAmount : 200000.0
             * isValid : true
             * viewNum : 49
             * clickNum : 12
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

        public static class SortBean {
            /**
             * direction : ASC
             * property : showOrderDsqb
             * ignoreCase : false
             * nullHandling : NATIVE
             * ascending : true
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
