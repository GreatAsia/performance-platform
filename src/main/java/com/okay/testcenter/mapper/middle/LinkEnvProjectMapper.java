package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.middle.LinkEnvProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LinkEnvProjectMapper {


    public void insertLinkEnvProject(LinkEnvProject linkEnvProject);

    public void updateLinkEnvProject(LinkEnvProject linkEnvProject);

    public void deleteLinkEnvProject(int id);

    public LinkEnvProject findLinkEnvProjectById(int id);

    public List<LinkEnvProject> findLinkEnvProjectByProjectId(int project_id);

    public List<LinkEnvProject> findLinkEnvProjectByProjectList();

    public List<LinkEnvProject> findLinkEnvProjectByProjectName(String project_name);
}
