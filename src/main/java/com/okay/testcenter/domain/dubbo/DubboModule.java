package com.okay.testcenter.domain.dubbo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class DubboModule {

    /**
     * 对应 dubbo_module 表
     */


    private int id;
    @NotBlank(message = "模块名称不能为空")
    @NotNull(message = "模块名称不能为空")

    private String name;
    @NotBlank(message = "模块描述不能为空")
    @NotNull(message = "模块描述不能为空")
    private String comments;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
