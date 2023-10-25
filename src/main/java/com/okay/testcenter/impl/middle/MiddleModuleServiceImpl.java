package com.okay.testcenter.impl.middle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleModule;
import com.okay.testcenter.mapper.middle.MiddleModuleMapper;
import com.okay.testcenter.service.middle.MiddleModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service("MiddleModuleService")
public class MiddleModuleServiceImpl implements MiddleModuleService {

    @Resource
    MiddleModuleMapper middleModuleMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMiddleModule(MiddleModule middleModule) {
       middleModuleMapper.insertMiddleModule(middleModule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleModule(int id) {
        middleModuleMapper.deleteMiddleModule(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleModule(MiddleModule middleModule) {
       middleModuleMapper.updateMiddleModule(middleModule);
    }

    @Override
    public MiddleModule findMiddleModuleById(int id) {
        return middleModuleMapper.findMiddleModuleById(id);
    }

    @Override
    public MiddleModule findMiddleModuleByName(String name) {
        return middleModuleMapper.findMiddleModuleByName(name);
    }

    @Override
    public PageInfo findMiddleModuleList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);

        List<MiddleModule> middleModuleList = middleModuleMapper.findMiddleModuleList();
        PageInfo pageInfo = new PageInfo(middleModuleList);
        return pageInfo;
    }

    @Override
    public PageInfo findMiddleModuleByProjectId(int project_id, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<MiddleModule> middleModuleList = middleModuleMapper.findMiddleModuleByProjectId(project_id);
        PageInfo pageInfo = new PageInfo(middleModuleList);
        return pageInfo;
    }

    @Override
    public List<MiddleModule> findMiddleModuleByProjectId(int project_id) {

        return middleModuleMapper.findMiddleModuleByProjectId(project_id);
    }
}
