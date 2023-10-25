package com.okay.testcenter.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Job {


    private int id;

    @NotNull(message = "任务名称不能为空")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @NotNull(message = "任务组不能为空")
    @NotBlank(message = "任务组不能为空")
    private String jobGroup;

    @NotNull(message = "时间表达式不能为空")
    @NotBlank(message = "时间表达式不能为空")
    private String cron;

    
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
