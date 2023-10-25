package com.okay.testcenter.service.dubbo;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformanceResult;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;

import java.util.List;

public interface DubboPerformanceResultService {


    void insert(DubboPerformanceResult dubboPerformanceResult);

    void update(DubboPerformanceResult dubboPerformanceResult);

    void delete(int id);

    List<DubboPerformanceResult> findByCaseId(int caseId);

    DubboPerformanceResult findById(int id);

    List<DubboPerformanceResult> findList();

    PageInfo findList(int currentPage, int pageSize);

    List<DubboPerformanceSet> findRunIdList();

    PageInfo findRunIdList(int currentPage, int pageSize);

    int getLastRunId();

    List<DubboPerformanceResult> findByRunId(int runId);


}
