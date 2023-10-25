package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformance;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MiddlePerformanceService {

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
    @Deprecated
    String runJmeter(int caseId, int threads, int runTime, int runId, String moreUser);


    /**
     * @param caseId   caseId
     * @param threads  并发数
     * @param runTime  压测时间
     * @param runId    运行ID
     * @param slaveStr 分布式机器
     */
    @Async
    String runJmeter(int caseId, int threads, int runTime, int runId, String moreUser, String slaveStr);


    @Async
    CompletableFuture<Integer> runJmeterSet(int setId);

    void stopJmeter();

    void insert(MiddlePerformance middlePerformance);

    void update(MiddlePerformance middlePerformance);

    void delete(int id);

    List<MiddlePerformance> findByCaseId(int caseId);



    MiddlePerformance findById(int id);

    List<MiddlePerformance> findList();

    PageInfo findList(int currentPage, int pageSize);


}
