package com.okay.testcenter.service.middle;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;

import java.util.List;

public interface MiddlePerformanceResultService {


    void insert(MiddlePerformanceResult middlePerformanceResult);

    void update(MiddlePerformanceResult middlePerformanceResult);

    void delete(int id);

    List<MiddlePerformanceResult> findByCaseId(int caseId);

    MiddlePerformanceResult findById(int id);

    List<MiddlePerformanceResult> findList();

    PageInfo findList(int currentPage, int pageSize);

    List<MiddlePerformanceSet> findRunIdList();

    PageInfo findRunIdList(int currentPage, int pageSize);

    int getLastRunId();

    List<MiddlePerformanceResult> findByRunId(int runId);


}
