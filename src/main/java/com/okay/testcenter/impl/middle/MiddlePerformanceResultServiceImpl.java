package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import com.okay.testcenter.mapper.middle.MiddlePerformanceResultMapper;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("MiddlePerformanceResultService")
public class MiddlePerformanceResultServiceImpl implements MiddlePerformanceResultService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    MiddlePerformanceResultMapper middlePerformanceResultMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(MiddlePerformanceResult middlePerformanceResult) {
        middlePerformanceResultMapper.insert(middlePerformanceResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MiddlePerformanceResult middlePerformanceResult) {
        middlePerformanceResultMapper.update(middlePerformanceResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        middlePerformanceResultMapper.delete(id);
    }

    @Override
    public List<MiddlePerformanceResult> findByCaseId(int caseId) {
        return middlePerformanceResultMapper.findByCaseId(caseId);
    }

    @Override
    public MiddlePerformanceResult findById(int id) {
        return middlePerformanceResultMapper.findById(id);
    }

    @Override
    public List<MiddlePerformanceResult> findList() {
        return middlePerformanceResultMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<MiddlePerformanceResult> middleModuleList = middlePerformanceResultMapper.findList();
        PageInfo pageInfo = new PageInfo(middleModuleList);
        return pageInfo;
    }

    @Override
    public List<MiddlePerformanceSet> findRunIdList() {
        return middlePerformanceResultMapper.findRunIdList();
    }

    @Override
    public PageInfo findRunIdList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddlePerformanceSet> runIdList = middlePerformanceResultMapper.findRunIdList(currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(runIdList);
        return pageInfo;
    }

    @Override
    public int getLastRunId() {

        int runId = middlePerformanceResultMapper.getLastRunId();

        return runId;
    }


    @Override
    public List<MiddlePerformanceResult> findByRunId(int runId) {
        return middlePerformanceResultMapper.findByRunId(runId);
    }
}
