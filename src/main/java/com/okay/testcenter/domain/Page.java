package com.okay.testcenter.domain;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class Page {


    @NotNull(message = "当前页不能为空")
    @Min(value = 1, message = "当前页不能小于1")
    private int currentPage;


    @NotNull(message = "页面大小不能为空")
    @Min(value = 1, message = "页面大小不能小于1")
    private int pageSize;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
