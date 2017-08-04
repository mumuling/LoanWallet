package com.pingxun.daishangqianbao.data;

import java.util.List;

/**
 * Created by LH on 2017/8/4.
 *
 */

public class F1ProductTypeBean {
    /**
     * success : true
     * code : 000000
     * message : 成功
     * data : [{"id":1,"name":"111","description":"11","img":"11"},{"id":2,"name":"222","description":"22","img":"222"},{"id":3,"name":"333","description":"33","img":"333"}]
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
         * name : 111
         * description : 11
         * img : 11
         */

        private int id;
        private String name;
        private String description;
        private String img;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
