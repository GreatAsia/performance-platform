package com.okay.testcenter.domain.dubbo;

import javax.validation.constraints.NotNull;

public class DubboTestHistory {

    /**
     * 对应 dubbo_testhistry 表
     */

    @NotNull
    private int id;
    @NotNull
    private String env;
    @NotNull
    private String result;
    @NotNull
    private int totalCase;
    @NotNull
    private int passCase;
    @NotNull
    private int failCase;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;
    @NotNull
    private int modelId;
    private String modelName;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(int totalCase) {
        this.totalCase = totalCase;
    }

    public int getPassCase() {
        return passCase;
    }

    public void setPassCase(int passCase) {
        this.passCase = passCase;
    }

    public int getFailCase() {
        return failCase;
    }

    public void setFailCase(int failCase) {
        this.failCase = failCase;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
