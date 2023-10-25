package com.okay.testcenter.domain.middle;

/*
   middle_interface表
 */

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MiddleInterface {


    private int id;

    @NotNull(message = "模块ID不能为空")
    @Min(1)
    private int module_id;

    @NotNull(message = "模块名称不能为空")
    @NotBlank(message = "模块名称不能为空")
    private String name;

    @NotNull(message = "URL不能为空")
    @NotBlank(message = "URL不能为空")
    private String url;

    @NotNull(message = "请求方法不能为空")
    @NotBlank(message = "请求方法不能为空")
    private String request_method;


    private String comments;

    private String project_id;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest_method() {
        return request_method;
    }

    public void setRequest_method(String request_method) {
        this.request_method = request_method;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
