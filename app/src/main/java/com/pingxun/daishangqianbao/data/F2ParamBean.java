package com.pingxun.daishangqianbao.data;

/**
 * Created by LH on 2017/8/2.
 * 精准Fragment贷款参数Bean
 */

public class F2ParamBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : {"startAmount":1000,"endAmount":50000,"startPeriodDay":10,"endPeriodDay":20,"startPeriodMonth":3,"endPeriodMonth":60,"loanRate":9}
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
         * startAmount : 1000.0
         * endAmount : 50000.0
         * startPeriodDay : 10
         * endPeriodDay : 20
         * startPeriodMonth : 3
         * endPeriodMonth : 60
         * loanRate : 9.0
         */

        private double startAmount;
        private double endAmount;
        private int startPeriodDay;
        private int endPeriodDay;
        private int startPeriodMonth;
        private int endPeriodMonth;
        private double loanRate;

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

        public int getStartPeriodDay() {
            return startPeriodDay;
        }

        public void setStartPeriodDay(int startPeriodDay) {
            this.startPeriodDay = startPeriodDay;
        }

        public int getEndPeriodDay() {
            return endPeriodDay;
        }

        public void setEndPeriodDay(int endPeriodDay) {
            this.endPeriodDay = endPeriodDay;
        }

        public int getStartPeriodMonth() {
            return startPeriodMonth;
        }

        public void setStartPeriodMonth(int startPeriodMonth) {
            this.startPeriodMonth = startPeriodMonth;
        }

        public int getEndPeriodMonth() {
            return endPeriodMonth;
        }

        public void setEndPeriodMonth(int endPeriodMonth) {
            this.endPeriodMonth = endPeriodMonth;
        }

        public double getLoanRate() {
            return loanRate;
        }

        public void setLoanRate(double loanRate) {
            this.loanRate = loanRate;
        }
    }
}
