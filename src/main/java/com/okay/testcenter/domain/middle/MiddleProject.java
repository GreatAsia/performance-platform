package com.okay.testcenter.domain.middle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
  middle_project表
 */
public class MiddleProject {

    private int id;

    @NotNull(message = "项目名称不能为空")
    @NotBlank(message = "项目名称不能为空")
    private String name;
    @NotNull(message = "登录类型不能为空")
    @Min(1)
    private int loginType;


    @NotNull(message = "运行类型不能为空")
    @Min(1)
    private int runType;

    private String loginParam;

    private String secretUrl;

    private String loginUrl;

    private String requestPre;

    private String loginTypeName;
    private String runTypeName;

    public String getLoginTypeName() {
        return loginTypeName;
    }

    public void setLoginTypeName(String loginTypeName) {
        this.loginTypeName = loginTypeName;
    }

    public String getRunTypeName() {
        return runTypeName;
    }

    public void setRunTypeName(String runTypeName) {
        this.runTypeName = runTypeName;
    }


    public String getRequestPre() {
        return requestPre;
    }

    public void setRequestPre(String requestPre) {
        this.requestPre = requestPre;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getRunType() {
        return runType;
    }

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public String getLoginParam() {
        return loginParam;
    }

    public void setLoginParam(String loginParam) {
        this.loginParam = loginParam;
    }

    public String getSecretUrl() {
        return secretUrl;
    }

    public void setSecretUrl(String secretUrl) {
        this.secretUrl = secretUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
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
}
