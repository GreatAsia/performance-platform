package com.okay.testcenter.impl.dubbo;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import com.okay.testcenter.mapper.dubbo.DubboPerformanceSetMapper;
import com.okay.testcenter.service.dubbo.DubboPerformanceSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("DubboPerformanceSetService")
public class DubboPerformanceSetServiceImpl implements DubboPerformanceSetService {

    @Resource
    DubboPerformanceSetMapper dubboPerformanceSetMapper;

    @Override
    public void insert(DubboPerformanceSet dubboPerformanceSet) {
        dubboPerformanceSetMapper.insert(dubboPerformanceSet);
    }

    @Override
    public void update(DubboPerformanceSet dubboPerformanceSet) {
        dubboPerformanceSetMapper.update(dubboPerformanceSet);
    }

    @Override
    public void delete(int id) {
        dubboPerformanceSetMapper.delete(id);
    }

    @Override
    public DubboPerformanceSet findByName(String name) {
        return dubboPerformanceSetMapper.findByName(name);
    }

    @Override
    public DubboPerformanceSet findById(int id) {
        return dubboPerformanceSetMapper.findById(id);
    }

    @Override
    public List<DubboPerformanceSet> findList() {
        return dubboPerformanceSetMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboPerformanceSet> list = dubboPerformanceSetMapper.findList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
