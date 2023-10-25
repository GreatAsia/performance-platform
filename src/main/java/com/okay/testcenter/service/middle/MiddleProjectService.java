package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleProject;

import java.util.List;


public interface MiddleProjectService {

    public void insertMiddleProject(MiddleProject middleProject);

    public void deleteMiddleProject(int id);

    public void updateMiddleProject(MiddleProject middleProject);

    public MiddleProject findMiddleProjectById(int id);

    public MiddleProject findMiddleProjectByName(String name);

    public PageInfo findMiddleProjectList(int currentPage,int pageSize);

    public List<MiddleProject> findMiddleProjectList();
}
