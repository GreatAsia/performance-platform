package com.okay.testcenter.impl.middle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleInterface;
import com.okay.testcenter.mapper.middle.MiddleInterfaceMapper;
import com.okay.testcenter.service.middle.MiddleInterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("MiddleInterfaceService")

public class MiddleInterfaceServiceImpl implements MiddleInterfaceService {

    @Resource
    MiddleInterfaceMapper middleInterfaceMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMiddleInterface(MiddleInterface middleInterface) {
        middleInterfaceMapper.insertMiddleInterface(middleInterface);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleInterface(int id) {
       middleInterfaceMapper.deleteMiddleInterface(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleInterface(MiddleInterface middleInterface) {
        middleInterfaceMapper.updateMiddleInterface(middleInterface);
    }

    @Override
    public MiddleInterface findMiddleInterfaceById(int id) {
        return middleInterfaceMapper.findMiddleInterfaceById(id);
    }

    @Override
    public List<MiddleInterface> findMiddleInterfaceByName(String name) {
        return middleInterfaceMapper.findMiddleInterfaceByName(name);
    }

    @Override
    public List<MiddleInterface> findMiddleInterfaceByPath(String path) {
        return middleInterfaceMapper.findMiddleInterfaceByPath(path);
    }

    @Override
    public PageInfo findMiddleInterfaceList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<MiddleInterface> middleInterfaceList = middleInterfaceMapper.findMiddleInterfaceList();
        PageInfo pageInfo = new PageInfo(middleInterfaceList);
        return pageInfo;
    }

    @Override
    public PageInfo findMiddleInterfaceByModuleId(int moduleId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddleInterface> middleInterfaceList = middleInterfaceMapper.findMiddleInterfaceByModuleId(moduleId);
        PageInfo pageInfo = new PageInfo(middleInterfaceList);
        return pageInfo;
    }

    @Override
    public List<MiddleInterface> findMiddleInterfaceByModuleId(int module_Id) {
        return middleInterfaceMapper.findMiddleInterfaceByModuleId(module_Id);
    }
}
