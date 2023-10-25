package com.okay.testcenter.domain.middle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LinkEnvProject {



    private int id;

    @NotNull(message = "项目ID不能为空")
    @Min(1)
    private int project_id;

    @NotNull(message = "环境ID不能为空")
    @NotBlank(message = "环境ID不能为空")
    private String env_id;

    @NotNull(message = "请求URL不能为空")
    @NotBlank(message = "请求URL不能为空")
    private String url_header;

    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String uname;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String pwd;

    private String project_name;
    private String env_name;



    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getEnv_name() {
        return env_name;
    }

    public void setEnv_name(String env_name) {
        this.env_name = env_name;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getEnv_id() {
        return env_id;
    }

    public void setEnv_id(String env_id) {
        this.env_id = env_id;
    }

    public String getUrl_header() {
        return url_header;
    }

    public void setUrl_header(String url_header) {
        this.url_header = url_header;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
