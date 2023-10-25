package com.okay.testcenter.domain.dubbo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zhou
 * @date 2019/12/13
 */
public class DubboPerformance {

    private int id;
    @NotNull(message = "用例ID不能为空")
    @Min(1)
    private int case_id;
    @NotNull(message = "并发数不能为空")
    @Min(1)
    private int threads;
    @NotNull(message = "持续时间不能为空")
    @Min(1)
    private int times;

    private String case_name;
    private int runId;

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }


    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCase_id() {
        return case_id;
    }

    public void setCase_id(int case_id) {
        this.case_id = case_id;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }


}
