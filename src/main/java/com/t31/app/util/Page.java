package com.t31.app.util;

import java.util.List;

public class Page {
        private Integer pageIndex=1;    //当前页
        private Integer pageSize=5;     //页面显示记录数
        private Integer totalCount;     //总条数
        private Integer totalPage;      //总页数
        private List<?> dataList;   //分页数据

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
