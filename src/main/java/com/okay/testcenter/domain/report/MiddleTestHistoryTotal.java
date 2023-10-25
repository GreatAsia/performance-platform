package com.okay.testcenter.domain.report;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MiddleTestHistoryTotal {

    @NotNull
    private int totalCase;
    @NotNull
    private int passCase;
    @NotNull
    private int failCase;

    private List<MiddleTestHistory> data = new ArrayList<>();

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

    public List<MiddleTestHistory> getData() {
        return data;
    }

    public void setData(List<MiddleTestHistory> data) {
        this.data = data;
    }
}
