package com.okay.testcenter.domain.report;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MiddleTestHistory {


    /**
     * 对应 middle_testhistory表
     */
    @NotNull
    private int id;
    @NotNull
    private int env;
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
    private int projectId;

    private String projectName;

    private List<MiddleRequestHistory> data = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
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


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public List<MiddleRequestHistory> getData() {
        return data;
    }

    public void setData(List<MiddleRequestHistory> data) {
        this.data = data;
    }
}
