package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleCase;
import com.okay.testcenter.mapper.middle.MiddleCaseMapper;
import com.okay.testcenter.service.middle.MiddleCaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("MiddleCaseService")
public class MiddleCaseServiceImpl implements MiddleCaseService {

   @Resource
   MiddleCaseMapper middleCaseMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMiddleCase(MiddleCase middleCase) {

        middleCaseMapper.insertMiddleCase(middleCase);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleCase(int id) {
        middleCaseMapper.deleteMiddleCase(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleCase(MiddleCase middleCase) {
        middleCaseMapper.updateMiddleCase(middleCase);
    }

    @Override
    public MiddleCase findMiddleCaseById(int id) {
        return middleCaseMapper.findMiddleCaseById(id);
    }

    @Override
    public List<MiddleCase> findMiddleCaseByName(String name) {
        return middleCaseMapper.findMiddleCaseByName(name);
    }

    @Override
    public List<MiddleCase> findMiddleCaseByPath(String path) {
        return middleCaseMapper.findMiddleCaseByPath(path);
    }

    @Override
    public PageInfo findMiddleCaseList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddleCase> middleCaseList = middleCaseMapper.findMiddleCaseList();
        PageInfo pageInfo = new PageInfo(middleCaseList);
        return pageInfo;
    }

    @Override
    public List<MiddleCase> findMiddleCaseList() {
        return middleCaseMapper.findMiddleCaseList();
    }

    @Override
    public PageInfo findMiddleCaseByEnvAndInterface(int env_id, int interface_id, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddleCase> middleCaseList = middleCaseMapper.findMiddleCaseByEnvAndInterface(env_id,interface_id);
        PageInfo pageInfo = new PageInfo(middleCaseList);
        return pageInfo;
    }

    @Override
    public List<MiddleCase> findMiddleCaseByEnvAndInterface(int env_id, int interface_id) {
        List<MiddleCase> middleCaseList = middleCaseMapper.findMiddleCaseByEnvAndInterface(env_id, interface_id);
        return middleCaseList;
    }
}
