package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class F3CardListBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : {"content":[{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":1,"name":"白金卡1","bankNumber":"11111","bankName":"中国银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZfKAGB45AABT6qRI_2k206.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZVKAO9x6AAMx9in4U58447.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":1,"clickNum":1,"isValid":true,"androidFlag":true,"appleFlag":true,"wechatFlag":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":2,"name":"会员卡2","bankNumber":"22222","bankName":"工商银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZnWAF-T_AABOzBbdoVw311.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZM6AO3LUAAXJQzBhbOo967.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":2,"clickNum":2,"isValid":true,"androidFlag":true,"appleFlag":true,"wechatFlag":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":3,"name":"会员卡3","bankNumber":"33333","bankName":"建设银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZrKAeOFXAABIl_oNMHU000.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZQ-AZ2C8AAXKTAjZhe4197.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":3,"clickNum":3,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":4,"name":"会员卡4","bankNumber":"44444","bankName":"建设银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZuGACSsrAABXBobU2u0745.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZTCAPLbfAAGhs2n0MuU978.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":4,"clickNum":4,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":5,"name":"会员卡5","bankNumber":"55555","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":5,"clickNum":5,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":6,"name":"会员卡6","bankNumber":"66666","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":6,"clickNum":6,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":7,"name":"会员卡7","bankNumber":"77777","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":7,"clickNum":7,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":8,"name":"会员卡8","bankNumber":"88888","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":8,"clickNum":8,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false}],"last":true,"totalPages":1,"totalElements":8,"sort":[{"direction":"ASC","property":"showOrder","ignoreCase":false,"nullHandling":"NATIVE","ascending":true}],"first":true,"numberOfElements":8,"size":10,"number":0}
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
         * content : [{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":1,"name":"白金卡1","bankNumber":"11111","bankName":"中国银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZfKAGB45AABT6qRI_2k206.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZVKAO9x6AAMx9in4U58447.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":1,"clickNum":1,"isValid":true,"androidFlag":true,"appleFlag":true,"wechatFlag":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":2,"name":"会员卡2","bankNumber":"22222","bankName":"工商银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZnWAF-T_AABOzBbdoVw311.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZM6AO3LUAAXJQzBhbOo967.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":2,"clickNum":2,"isValid":true,"androidFlag":true,"appleFlag":true,"wechatFlag":true},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":3,"name":"会员卡3","bankNumber":"33333","bankName":"建设银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZrKAeOFXAABIl_oNMHU000.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZQ-AZ2C8AAXKTAjZhe4197.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":3,"clickNum":3,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":4,"name":"会员卡4","bankNumber":"44444","bankName":"建设银行","img":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZuGACSsrAABXBobU2u0745.png","recommendImg":"http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZTCAPLbfAAGhs2n0MuU978.png","isRecommend":true,"cardDesc":"123","url":"http://www.baidu.com","showOrder":4,"clickNum":4,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":5,"name":"会员卡5","bankNumber":"55555","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":5,"clickNum":5,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":6,"name":"会员卡6","bankNumber":"66666","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":6,"clickNum":6,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":7,"name":"会员卡7","bankNumber":"77777","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":7,"clickNum":7,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false},{"pageNo":null,"sizePerPage":10,"sortDirection":"ASC","sortFields":null,"id":8,"name":"会员卡8","bankNumber":"88888","bankName":"建设银行","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","recommendImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498056815058&di=c0872ff7a8147be","isRecommend":false,"cardDesc":"123","url":"http://www.baidu.com","showOrder":8,"clickNum":8,"isValid":true,"androidFlag":false,"appleFlag":false,"wechatFlag":false}]
         * last : true
         * totalPages : 1
         * totalElements : 8
         * sort : [{"direction":"ASC","property":"showOrder","ignoreCase":false,"nullHandling":"NATIVE","ascending":true}]
         * first : true
         * numberOfElements : 8
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
             * id : 1
             * name : 白金卡1
             * bankNumber : 11111
             * bankName : 中国银行
             * img : http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZfKAGB45AABT6qRI_2k206.png
             * recommendImg : http://119.23.210.232:8088/group1/M00/00/01/rBINgVlKZVKAO9x6AAMx9in4U58447.png
             * isRecommend : true
             * cardDesc : 123
             * url : http://www.baidu.com
             * showOrder : 1
             * clickNum : 1
             * isValid : true
             * androidFlag : true
             * appleFlag : true
             * wechatFlag : true
             */

            private Object pageNo;
            private int sizePerPage;
            private String sortDirection;
            private Object sortFields;
            private int id;
            private String name;
            private String bankNumber;
            private String bankName;
            private String img;
            private String recommendImg;
            private boolean isRecommend;
            private String cardDesc;
            private String url;
            private int showOrder;
            private int clickNum;
            private boolean isValid;
            private boolean androidFlag;
            private boolean appleFlag;
            private boolean wechatFlag;

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

            public String getBankNumber() {
                return bankNumber;
            }

            public void setBankNumber(String bankNumber) {
                this.bankNumber = bankNumber;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getRecommendImg() {
                return recommendImg;
            }

            public void setRecommendImg(String recommendImg) {
                this.recommendImg = recommendImg;
            }

            public boolean isIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(boolean isRecommend) {
                this.isRecommend = isRecommend;
            }

            public String getCardDesc() {
                return cardDesc;
            }

            public void setCardDesc(String cardDesc) {
                this.cardDesc = cardDesc;
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

            public int getClickNum() {
                return clickNum;
            }

            public void setClickNum(int clickNum) {
                this.clickNum = clickNum;
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
        }

        public static class SortBean {
            /**
             * direction : ASC
             * property : showOrder
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
