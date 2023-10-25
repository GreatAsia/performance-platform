package com.okay.testcenter.mapper.job;


import com.okay.testcenter.domain.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobRunMapper {

    public void insertJob(Job job);

    public void deleteJob(int id);

    public void updateJob(Job job);

    public Job findJobById(int id);

    public List<Job> findJobByName(String name);

    public List<Job> findJobList();


}
