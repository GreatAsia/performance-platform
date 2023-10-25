package com.okay.testcenter.impl.job;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Job;
import com.okay.testcenter.mapper.job.JobRunMapper;
import com.okay.testcenter.service.job.JobRunService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service("JobRunService")
public class JobRunServiceImpl implements JobRunService {

    @Resource
    JobRunMapper jobRunMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJob(Job job) {
        jobRunMapper.insertJob(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(int id) {
        jobRunMapper.deleteJob(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job) {
        jobRunMapper.updateJob(job);
    }

    @Override
    public Job findJobById(int id) {
        return jobRunMapper.findJobById(id);
    }

    @Override
    public List<Job> findJobByName(String name) {
        return jobRunMapper.findJobByName(name);
    }

    @Override
    public PageInfo findJobList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<Job> list = jobRunMapper.findJobList();
        PageInfo<Job> page = new PageInfo<Job>(list);
        return page;

    }

    @Override
    public List<Job> findJobList() {
        return jobRunMapper.findJobList();
    }

}
