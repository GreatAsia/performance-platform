package com.okay.testcenter.mapper.dubbo;


import com.okay.testcenter.domain.dubbo.DubboPerformanceResult;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface DubboPerformanceResultMapper {

    void insert(DubboPerformanceResult dubboPerformanceResult);

    void update(DubboPerformanceResult dubboPerformanceResult);

    void delete(int id);

    List<DubboPerformanceResult> findByCaseId(int caseId);

    DubboPerformanceResult findById(int id);

    List<DubboPerformanceResult> findList();

    Integer getLastRunId();

    List<DubboPerformanceSet> findRunIdList();

    List<DubboPerformanceSet> findRunIdList(int currentPage, int pageSize);

    List<DubboPerformanceResult> findByRunId(int runId);
}
