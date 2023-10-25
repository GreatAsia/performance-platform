package com.okay.testcenter.domain.dubbo;

import javax.validation.constraints.NotNull;

public class DubboCaseHistory {

    /**
     * 对应 dubbo_casehistory 表
     */

    @NotNull
    private int id;
    @NotNull
    private int historyId;
    @NotNull
    private int modelId;
    @NotNull
    private String caseName;
    @NotNull
    private String requestData;
    @NotNull
    private String env;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;
    @NotNull
    private String responseContent;
    @NotNull
    private String result;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
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

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
