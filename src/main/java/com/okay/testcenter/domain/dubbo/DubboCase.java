package com.okay.testcenter.domain.dubbo;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DubboCase {

    /**
     * 对应 dubbo_case 表
     */


    private int id;

    private String name;

    @NotNull(message = "用例名称不能为空")
    @NotBlank(message = "用例名称不能为空")
    private String caseName;

    @NotNull(message = "模块ID不能为空")
    @Min(value = 1, message = "模块ID不能为空")
    private int model;

    @NotNull(message = "接口类名不能为空")
    @NotBlank(message = "接口类名不能为空")
    private String interFaceClassName;

    @NotNull(message = "方法名不能为空")
    @NotBlank(message = "方法名不能为空")
    private String methodName;

    @NotNull(message = "请求参数类型不能为空")
    @NotBlank(message = "请求参数类型不能为空")
    private String requestType;


    @NotNull(message = "请求参数不能为空")
    @NotBlank(message = "请求参数不能为空")
    private String params;

    @NotNull(message = "验证数据不能为空")
    @NotBlank(message = "验证数据不能为空")
    private String checkData;



    @NotNull(message = "版本不能为空")
    @NotBlank(message = "版本不能为空")
    private String version;

    private String createTime;
    private String editTime;

    @NotNull(message = "用例类型不能为空")
    @Min(0)
    @Max(1)
    private int type;



    private String modelName;

    private int envId;
    private String address;
    private List<String> methodList = new ArrayList<>();

    public String getSvr() {
        return svr;
    }

    public void setSvr(String svr) {
        this.svr = svr;
    }

    private String svr;


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<String> methodList) {
        this.methodList = methodList;
    }



    private List<Map<String, Object>> paramList = new ArrayList<>();

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public void setParamList(List<Map<String, Object>> paramList) {
        this.paramList = paramList;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getInterFaceClassName() {
        return interFaceClassName;
    }

    public void setInterFaceClassName(String interFaceClassName) {
        this.interFaceClassName = interFaceClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnvId() {
        return envId;
    }

    public void setEnvId(int envId) {
        this.envId = envId;
    }

}
