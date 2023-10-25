package com.okay.testcenter.service.job;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Job;

import java.util.List;


public interface JobRunService {


    public void insertJob(Job job);

    public void deleteJob(int id);

    public void updateJob(Job job);

    public Job findJobById(int id);

    public List<Job> findJobByName(String name);

    public PageInfo findJobList(int currentPage, int pageSize);

    public List<Job> findJobList();
}
