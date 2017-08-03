package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/2.
 * 精准Fragment职业数据源
 */

public class F2JobDataBean {
    /**
     * success : true
     * code : 000000
     * message : 查询成功
     * data : [{"code":"student","name":"学生","showOrder":0,"cycle":"","startValue":0,"endValue":0},{"code":"teacher","name":"工薪层","showOrder":1,"cycle":"","startValue":0,"endValue":0},{"code":"person","name":"个体","showOrder":2,"cycle":"","startValue":0,"endValue":0},{"code":"other","name":"其他","showOrder":3,"cycle":"","startValue":0,"endValue":0}]
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
         * code : student
         * name : 学生
         * showOrder : 0
         * cycle :
         * startValue : 0
         * endValue : 0
         */

        private String code;
        private String name;
        private int showOrder;
        private String cycle;
        private int startValue;
        private int endValue;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public int getStartValue() {
            return startValue;
        }

        public void setStartValue(int startValue) {
            this.startValue = startValue;
        }

        public int getEndValue() {
            return endValue;
        }

        public void setEndValue(int endValue) {
            this.endValue = endValue;
        }
    }
}
