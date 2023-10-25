package com.okay.testcenter.impl.job;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Job;
import com.okay.testcenter.mapper.job.JobPerformanceMapper;
import com.okay.testcenter.service.job.JobPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("JobPerformanceService")
public class JobPerformanceServiceImpl implements JobPerformanceService {

    @Resource
    JobPerformanceMapper jobPerformanceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJob(Job job) {
        jobPerformanceMapper.insertJob(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(int id) {
        jobPerformanceMapper.deleteJob(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job) {
        jobPerformanceMapper.updateJob(job);
    }

    @Override
    public Job findJobById(int id) {
        return jobPerformanceMapper.findJobById(id);
    }

    @Override
    public List<Job> findJobByName(String name) {
        return jobPerformanceMapper.findJobByName(name);
    }

    @Override
    public PageInfo findJobList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<Job> list = jobPerformanceMapper.findJobList();
        PageInfo<Job> page = new PageInfo<Job>(list);
        return page;

    }

    @Override
    public List<Job> findJobList() {
        return jobPerformanceMapper.findJobList();
    }

}
