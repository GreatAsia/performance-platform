package com.okay.testcenter;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Job;
import com.okay.testcenter.service.job.JobRunService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JobRunTest {


    private static final Logger logger = LoggerFactory.getLogger(JobRunTest.class);

    @Resource
    JobRunService jobRunService;


    @Test
    public void testInsertJob() {

        Job job = new Job();
        job.setJobName("com.okay.testcenter.job.NewJob");
        job.setJobGroup("test");
        job.setCron("0/10 * * * * ? ");
        job.setStatus("1");

        jobRunService.insertJob(job);
    }


    @Test
    public void testUpdateJob() {

        Job job = new Job();
        job.setId(1);
        job.setJobName("com.okay.testcenter.job.HelloJob");
        job.setJobGroup("test");
        job.setCron("0/6 * * * * ? ");
        job.setStatus("1");

        jobRunService.updateJob(job);
    }


    @Test
    public void testDeleteJob() {

        jobRunService.deleteJob(3);
    }


    @Test
    public void testFindJobById() {

        Job job = jobRunService.findJobById(1);
        logger.info("[job]==" + JSONObject.toJSONString(job));
    }


    @Test
    public void testFindJobByName() {

        List<Job> job = jobRunService.findJobByName("com.okay.testcenter.job.HelloJob");
        logger.info("[job]==" + JSONObject.toJSONString(job));
    }


    @Test
    public void testFindJobList() {

        PageInfo job = jobRunService.findJobList(1, 10);
        logger.info("[job]==" + JSONObject.toJSONString(job));
    }

}
