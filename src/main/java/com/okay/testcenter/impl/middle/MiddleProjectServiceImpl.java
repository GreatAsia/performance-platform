package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.mapper.middle.MiddleProjectMapper;
import com.okay.testcenter.service.middle.MiddleProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("MiddleProjectService")
public class MiddleProjectServiceImpl implements MiddleProjectService {

    @Resource
    MiddleProjectMapper middleProjectMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMiddleProject(MiddleProject middleProject) {
        middleProjectMapper.insertMiddleProject(middleProject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleProject(int id) {
        middleProjectMapper.deleteMiddleProject(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleProject(MiddleProject middleProject) {
        middleProjectMapper.updateMiddleProject(middleProject);
    }

    @Override
    public MiddleProject findMiddleProjectById(int id) {
        return middleProjectMapper.findMiddleProjectById(id);
    }

    @Override
    public MiddleProject findMiddleProjectByName(String name) {
        return middleProjectMapper.findMiddleProjectByName(name);
    }

    @Override
    public PageInfo findMiddleProjectList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<MiddleProject> middleProjectList = middleProjectMapper.findMiddleProjectList();
        PageInfo pageInfo = new PageInfo(middleProjectList);
        return pageInfo;
    }

    @Override
    public List<MiddleProject> findMiddleProjectList() {
        return middleProjectMapper.findMiddleProjectList();
    }
}
