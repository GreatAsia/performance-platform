package com.okay.testcenter.domain.dubbo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhou
 * @date 2019/12/13
 */
public class DubboPerformanceResult {

    private int id;

    @NotNull(message = "用例ID不能为空")
    @Min(1)
    private int case_id;

    @NotNull(message = "错误率不能为空")
    @NotBlank(message = "错误率不能为空")
    private String error_rate;

    @NotNull(message = "响应时间不能为空")
    @Min(1)
    private int response_time;

    @NotNull(message = "吞吐量不能为空")
    private float throughput;

    @NotNull(message = "开始时间不能为空")
    @NotBlank(message = "开始时间不能为空")
    private String start_time;

    @NotNull(message = "结束时间不能为空")
    @NotBlank(message = "结束时间不能为空")
    private String end_time;

    @NotNull(message = "运行ID不能为空")
    @Min(1)
    private int run_id;

    private String case_name;
    private int set_id;

    private String error_data;

    @NotNull(message = "运行时间不能为空")
    @Min(1)
    private int run_time;


    @NotNull(message = "并发数不能为空")
    @Min(1)
    private int threads;


    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }


    public int getRun_time() {
        return run_time;
    }

    public void setRun_time(int run_time) {
        this.run_time = run_time;
    }

    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public String getError_rate() {
        return error_rate;
    }

    public void setError_rate(String error_rate) {
        this.error_rate = error_rate;
    }

    public int getResponse_time() {
        return response_time;
    }

    public void setResponse_time(int response_time) {
        this.response_time = response_time;
    }

    public float getThroughput() {
        return throughput;
    }

    public void setThroughput(float throughput) {
        this.throughput = throughput;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public String getError_data() {
        return error_data;
    }

    public void setError_data(String error_data) {
        this.error_data = error_data;
    }
}
