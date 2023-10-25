package com.okay.testcenter.domain.dubbo;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class DubboReportList {

    @NotNull(message = "模块ID不能为空")
    @Min(value = 1, message = "模块ID不能小于1")
    private int moduleId;


    @NotNull(message = "环境不能为空")
    @NotBlank(message = "环境不能为空")
    private int env;


    @NotNull(message = "当前页不能为空")
    @Min(value = 1, message = "当前页不能小于1")
    private int currentPage;

    @NotNull(message = "页面大小不能为空")
    @Min(value = 1, message = "页面大小不能小于1")
    private int pageSize;


    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

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

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

}
