package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.LinkEnvProject;

import java.util.List;

public interface LinkEnvProjectService {

    public void insertLinkEnvProject(LinkEnvProject linkEnvProject);

    public void updateLinkEnvProject(LinkEnvProject linkEnvProject);

    public void deleteLinkEnvProject(int id);

    public LinkEnvProject findLinkEnvProjectById(int id);

    public List<LinkEnvProject> findLinkEnvProjectByProjectId(int project_id);

    public PageInfo findLinkEnvProjectByProjectList(int currentPage, int pageSize);

    public List<LinkEnvProject> findLinkEnvProjectByProjectName(String project_name);

}
