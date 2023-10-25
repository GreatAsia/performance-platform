package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface MiddlePerformanceResultMapper {

    void insert(MiddlePerformanceResult middlePerformanceResult);

    void update(MiddlePerformanceResult middlePerformanceResult);

    void delete(int id);

    List<MiddlePerformanceResult> findByCaseId(int caseId);

    MiddlePerformanceResult findById(int id);

    List<MiddlePerformanceResult> findList();

    Integer getLastRunId();

    List<MiddlePerformanceSet> findRunIdList();

    List<MiddlePerformanceSet> findRunIdList(int currentPage, int pageSize);

    List<MiddlePerformanceResult> findByRunId(int runId);
}
