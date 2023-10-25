package com.okay.testcenter.job;
import com.alibaba.fastjson.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

public class BaseJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Map<String, Object> params = jobExecutionContext.getJobDetail().getJobDataMap();
        JSONObject jsonParam = JSONObject.parseObject(String.valueOf(params.get("json")));
        runMonitor(jsonParam);
    }


    public void runMonitor(JSONObject jsonObject) {
    }


}
