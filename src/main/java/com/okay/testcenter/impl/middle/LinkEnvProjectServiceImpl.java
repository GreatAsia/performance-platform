package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.LinkEnvProject;
import com.okay.testcenter.mapper.middle.LinkEnvProjectMapper;
import com.okay.testcenter.service.middle.LinkEnvProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("LinkEnvProjectService")
public class LinkEnvProjectServiceImpl implements LinkEnvProjectService {

    @Resource
    LinkEnvProjectMapper linkEnvProjectMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLinkEnvProject(LinkEnvProject linkEnvProject) {
        linkEnvProjectMapper.insertLinkEnvProject(linkEnvProject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLinkEnvProject(LinkEnvProject linkEnvProject) {
        linkEnvProjectMapper.updateLinkEnvProject(linkEnvProject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLinkEnvProject(int id) {
        linkEnvProjectMapper.deleteLinkEnvProject(id);
    }

    @Override
    public LinkEnvProject findLinkEnvProjectById(int id) {
        return linkEnvProjectMapper.findLinkEnvProjectById(id);
    }

    @Override
    public List<LinkEnvProject> findLinkEnvProjectByProjectId(int project_id) {
        return linkEnvProjectMapper.findLinkEnvProjectByProjectId(project_id);
    }

    @Override
    public PageInfo findLinkEnvProjectByProjectList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectMapper.findLinkEnvProjectByProjectList();
        PageInfo pageInfo = new PageInfo(linkEnvProjectList);
        return pageInfo;
    }

    @Override
    public List<LinkEnvProject> findLinkEnvProjectByProjectName(String project_name) {

        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectMapper.findLinkEnvProjectByProjectName(project_name);
        return linkEnvProjectList;
    }
}
