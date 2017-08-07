/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pingxun.daishangqianbao.data;

import java.io.Serializable;

/**
 * 暂时没用到
 */
public class ServerModel implements Serializable {

    private static final long serialVersionUID = -828322761336296999L;

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
