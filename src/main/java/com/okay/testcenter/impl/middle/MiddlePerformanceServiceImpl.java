package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.*;
import com.okay.testcenter.jmeter.RunJmeter;
import com.okay.testcenter.mapper.middle.MiddlePerformanceMapper;
import com.okay.testcenter.mapper.middle.MiddlePerformanceResultMapper;
import com.okay.testcenter.mapper.middle.MiddlePerformanceSetMapper;
import com.okay.testcenter.performance.PerTask;
import com.okay.testcenter.performance.base.Concurrent;
import com.okay.testcenter.performance.base.PerformanceResultBean;
import com.okay.testcenter.performance.base.ThreadBase;
import com.okay.testcenter.service.middle.MiddlePerformanceService;
import com.okay.testcenter.service.middle.MiddleRunService;
import com.okay.testcenter.service.tool.AddUserDetailService;
import com.okay.testcenter.service.tool.AddUserHistoryService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service("MiddlePerformanceService")
public class MiddlePerformanceServiceImpl implements MiddlePerformanceService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    MiddlePerformanceMapper middlePerformanceMapper;
    @Resource
    MiddlePerformanceSetMapper middlePerformanceSetMapper;
    @Resource
    MiddlePerformanceResultMapper service;
    @Resource
    MiddleRunService middleRunService;
    @Resource
    MiddlePerformanceResultMapper middlePerformanceResultMapper;
    @Resource
    AddUserDetailService addUserDetailService;
    @Resource
    AddUserHistoryService addUserHistoryService;


    @Value("${jmeter.path}")
    private String jmeterPath;
    @Value("${jmeter.data.path}")
    private String jmeterDataPath;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runSingle(int id, int threads, int time) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PerTask perTask = new PerTask(id, time);
                List<ThreadBase> list = new ArrayList<>();
                for (int i = 0; i < threads; i++) {
                    PerTask clone = perTask.clone();
                    list.add(clone);
                }
                PerformanceResultBean start = new Concurrent(list).start();
                MiddlePerformanceResult result = new MiddlePerformanceResult();
                result.setThroughput((float) start.getQps());
                result.setResponse_time(start.getRt());
                DecimalFormat df = new DecimalFormat("#.00");
                double errorRate = start.getErrorRate();
                result.setError_rate(errorRate + "");
                result.setCase_id(id);
                result.setStart_time(start.getStartTime());
                result.setEnd_time(start.getEndTime());
                service.insert(result);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String runJmeter(int caseId, int threads, int runTime, int runId, String moreUser) {

                try {

                    ResponseSampler responseSampler = (ResponseSampler) middleRunService.runSingle(caseId);
                    RequestSampler requestInfo = responseSampler.getRequestSampler();
                    if (!"不用".equals(moreUser)) {
                        AddUserHistory addUserHistory = addUserHistoryService.findHistoryByUserTypeAndEnvId(moreUser, requestInfo.getEnv_id());
                        addUserHistoryService.updateHistoryData(addUserHistory.getId());
                        addUserDetailService.createFile(requestInfo.getEnv_id(), moreUser);
                    }
                    requestInfo.setThreads(threads);
                    requestInfo.setRunTime(runTime);
                    requestInfo.setRun_id(runId);
                    requestInfo.setMoreUser(moreUser);
                    requestInfo.setJmeterPath(jmeterPath);
                    requestInfo.setJmeterDataPath(jmeterDataPath);
                    RunJmeter.setFlag(true);
                    RunJmeter.getInstance(requestInfo).start();
                } catch (Exception e) {
                    logger.error("压测出现异常==" + e.getMessage());
                    e.printStackTrace();
                }

        return "pass";

    }

    @Override
    public synchronized String runJmeter(int caseId, int threads, int runTime, int runId, String moreUser, String slaveStr) {

        try {

            ResponseSampler responseSampler = (ResponseSampler) middleRunService.runSingle(caseId);
            RequestSampler requestInfo = responseSampler.getRequestSampler();
            if (!"不用".equals(moreUser)) {
                AddUserHistory addUserHistory = addUserHistoryService.findHistoryByUserTypeAndEnvId(moreUser, requestInfo.getEnv_id());
                addUserHistoryService.updateHistoryData(addUserHistory.getId());
                addUserDetailService.createFile(requestInfo.getEnv_id(), moreUser);
            }
            requestInfo.setThreads(threads);
            requestInfo.setRunTime(runTime);
            requestInfo.setRun_id(runId);
            requestInfo.setMoreUser(moreUser);
            requestInfo.setJmeterPath(jmeterPath);
            requestInfo.setJmeterDataPath(jmeterDataPath);
            requestInfo.setSlaveStr(slaveStr);
            RunJmeter.setFlag(true);
            RunJmeter.getInstance(requestInfo).start();
        } catch (Exception e) {
            logger.error("压测出现异常==" + e.getMessage());
            e.printStackTrace();
        }

        return "pass";
    }

    @Override
    public CompletableFuture<Integer> runJmeterSet(int setId) {

        int runId = 0;
        try {
            runId = middlePerformanceResultMapper.getLastRunId();

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (runId == 0) {
                runId = 1;
            } else {
                runId = runId + 1;
            }
        }
        MiddlePerformanceSet middleSet = middlePerformanceSetMapper.findById(setId);
        String planSet = middleSet.getPlan_set();
        String[] sets = new String[]{};
        try {
            if (planSet.contains(",")) {
                sets = planSet.split(",");
            } else {
                sets = new String[]{planSet};
            }
            RunJmeter.setFlag(true);

            for (int i = 0; i < sets.length; i++) {

                if(false == RunJmeter.getFlag()){
                    break;
                }
                    int runCount = i + 1;
                String type = "";
                    logger.info("运行第" + runCount + "个压测");

                    MiddlePerformance middlePerformance = middlePerformanceMapper.findById(Integer.parseInt(sets[i]));
                    ResponseSampler responseSampler = (ResponseSampler) middleRunService.runSingle(middlePerformance.getCase_id());

                    RequestSampler requestInfo = responseSampler.getRequestSampler();
                if (!"不用".equals(middlePerformance.getMoreUser())) {
                    AddUserHistory addUserHistory = addUserHistoryService.findHistoryByUserTypeAndEnvId(middlePerformance.getMoreUser(), requestInfo.getEnv_id());
                    addUserHistoryService.updateHistoryData(addUserHistory.getId());
                    addUserDetailService.createFile(requestInfo.getEnv_id(), middlePerformance.getMoreUser());
                }
                    requestInfo.setThreads(middlePerformance.getThreads());
                    requestInfo.setRunTime(middlePerformance.getTimes());
                    requestInfo.setRun_id(runId);
                    requestInfo.setSet_id(setId);
                requestInfo.setMoreUser(middlePerformance.getMoreUser());
                    requestInfo.setJmeterPath(jmeterPath);
                requestInfo.setJmeterDataPath(jmeterDataPath);
                    RunJmeter runJmeter = RunJmeter.getInstance(requestInfo);
                    runJmeter.start();
                    logger.info("第" + runCount + "次压测完成");

                    if (i < sets.length - 1) {
                        logger.info("等待60秒");
                        Thread.sleep(60000);
                    }
            }

        } catch (Exception e) {
            logger.error("压测集合有报错==" + ExceptionUtils.getStackTrace(e));

        }

        return CompletableFuture.completedFuture(runId);

    }

    @Override
    public void stopJmeter() {

        RunJmeter.setFlag(false);
        RunJmeter.stop();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(MiddlePerformance middlePerformance) {
        middlePerformanceMapper.insert(middlePerformance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MiddlePerformance middlePerformance) {
        middlePerformanceMapper.update(middlePerformance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        middlePerformanceMapper.delete(id);
    }

    @Override
    public List<MiddlePerformance> findByCaseId(int caseId) {
        return middlePerformanceMapper.findByCaseId(caseId);
    }


    @Override
    public MiddlePerformance findById(int id) {
        return middlePerformanceMapper.findById(id);
    }

    @Override
    public List<MiddlePerformance> findList() {
        return middlePerformanceMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<MiddlePerformance> middleModuleList = middlePerformanceMapper.findList();
        PageInfo pageInfo = new PageInfo(middleModuleList);
        return pageInfo;
    }


}
