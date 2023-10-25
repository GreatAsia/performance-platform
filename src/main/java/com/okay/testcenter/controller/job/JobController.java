package com.okay.testcenter.controller.job;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Job;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.impl.job.ScheduleTriggerService;
import com.okay.testcenter.job.BaseJob;
import com.okay.testcenter.service.job.JobPerformanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "定时任务接口")
@Controller
@RequestMapping(value = "/perf/job")
public class JobController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ScheduleTriggerService scheduleTriggerService;

    @Autowired
    private JobPerformanceService jobPerformanceService;



    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

    @ApiOperation(value = "添加定时任务", notes = "添加定时任务")
    @PostMapping(value = "/addjob")
    @ResponseBody
    public RetResult<Object> addjob(@Validated @RequestBody Job job) {

        jobPerformanceService.insertJob(job);
        scheduleTriggerService.refreshTrigger();
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "暂停定时任务", notes = "暂停定时任务")
    @PostMapping(value = "/pausejob")
    @ResponseBody
    public RetResult<Object> pausejob(@Validated @RequestBody Job job) {
        job.setStatus("0");
        jobPerformanceService.updateJob(job);
        scheduleTriggerService.refreshTrigger();
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "恢复定时任务", notes = "恢复定时任务")
    @PostMapping(value = "/resumejob")
    @ResponseBody
    public RetResult<Object> resumejob(@Validated @RequestBody Job job) {

        job.setStatus("1");
        jobPerformanceService.updateJob(job);
        scheduleTriggerService.refreshTrigger();
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新定时任务", notes = "更新定时任务")
    @PostMapping(value = "/updatejob")
    @ResponseBody
    public RetResult<Object> updateJob(@Validated @RequestBody Job job) {

        jobPerformanceService.updateJob(job);
        scheduleTriggerService.refreshTrigger();
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @PostMapping(value = "/deletejob")
    @ResponseBody
    public RetResult<Object> deletejob(@RequestBody JSONObject request) {

        int jobId = Integer.parseInt(request.get("jobId").toString());
        jobPerformanceService.deleteJob(jobId);
        scheduleTriggerService.refreshTrigger();
        return RetResponse.makeOKRsp();
    }

    @GetMapping(value = "/list")
    public String queryjob(Model model) {
        PageInfo<Job> jobList = jobPerformanceService.findJobList(1, 10);
        model.addAttribute("jobList", jobList);
        return "job/JobEdit";
    }

    @ApiOperation(value = "定时任务列表", notes = "获取定时任务列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String list(@Validated Page page) {
        PageInfo<Job> jobList = jobPerformanceService.findJobList(page.getCurrentPage(), page.getPageSize());

        return JSONObject.toJSONString(jobList);
    }

    @ApiOperation(value = "通过名称查找定时任务", notes = "通过名称查找定时任务")
    @GetMapping(value = "/findJobByName")
    @ResponseBody
    public String findJobByName(String jobName) {

        List<Job> job = jobPerformanceService.findJobByName(jobName);

        return JSONObject.toJSONString(job);
    }

    @ApiOperation(value = "触发定时任务", notes = "触发定时任务")
    @GetMapping(value = "/refreshTrigger")
    @ResponseBody
    public  RetResult<Object> refreshTrigger() {

        scheduleTriggerService.refreshTrigger();

        logger.info("定时器任务开始执行，请注意观察控制台");
        return RetResponse.makeOKRsp();
    }


}
