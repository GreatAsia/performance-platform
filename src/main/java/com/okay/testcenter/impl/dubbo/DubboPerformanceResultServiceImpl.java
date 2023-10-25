package com.okay.testcenter.impl.dubbo;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformanceResult;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import com.okay.testcenter.mapper.dubbo.DubboPerformanceResultMapper;
import com.okay.testcenter.service.dubbo.DubboPerformanceResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("DubboPerformanceResultService")
public class DubboPerformanceResultServiceImpl implements DubboPerformanceResultService {

    @Resource
    DubboPerformanceResultMapper dubboPerformanceResultMapper;

    @Override
    public void insert(DubboPerformanceResult dubboPerformanceResult) {
        dubboPerformanceResultMapper.insert(dubboPerformanceResult);
    }

    @Override
    public void update(DubboPerformanceResult dubboPerformanceResult) {
        dubboPerformanceResultMapper.update(dubboPerformanceResult);
    }

    @Override
    public void delete(int id) {
        dubboPerformanceResultMapper.delete(id);
    }

    @Override
    public List<DubboPerformanceResult> findByCaseId(int caseId) {
        return dubboPerformanceResultMapper.findByCaseId(caseId);
    }

    @Override
    public DubboPerformanceResult findById(int id) {
        return dubboPerformanceResultMapper.findById(id);
    }

    @Override
    public List<DubboPerformanceResult> findList() {
        return dubboPerformanceResultMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboPerformanceResult> list = dubboPerformanceResultMapper.findList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<DubboPerformanceSet> findRunIdList() {
        return dubboPerformanceResultMapper.findRunIdList();
    }

    @Override
    public PageInfo findRunIdList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboPerformanceSet> list = dubboPerformanceResultMapper.findRunIdList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int getLastRunId() {
        Integer result = dubboPerformanceResultMapper.getLastRunId();
        if (result == null) {
            return 1;
        } else {
            return result.intValue() + 1;
        }
    }

    @Override
    public List<DubboPerformanceResult> findByRunId(int runId) {
        return dubboPerformanceResultMapper.findByRunId(runId);
    }
}
