package com.okay.testcenter.service.dubbo;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformance;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DubboPerformanceService {

    /**
     * @param id      caseId
     * @param threads 并发数
     * @param time    压测时间
     */
    void runSingle(int id, int threads, int time);

    /**
     * @param caseId  caseId
     * @param threads 并发数
     * @param runTime 压测时间
     * @param runId   运行ID
     */
    @Async
    void runJmeter(int caseId, int threads, int runTime, int runId);


    @Async
    CompletableFuture<Integer> runJmeterSet(int setId);

    void stopJmeter();

    void insert(DubboPerformance dubboPerformance);

    void update(DubboPerformance dubboPerformance);

    void delete(int id);

    List<DubboPerformance> findByCaseId(int caseId);


    DubboPerformance findById(int id);

    List<DubboPerformance> findList();

    PageInfo findList(int currentPage, int pageSize);


}
