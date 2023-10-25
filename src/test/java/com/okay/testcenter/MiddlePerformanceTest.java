package com.okay.testcenter;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformance;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import com.okay.testcenter.service.middle.MiddlePerformanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlePerformanceTest {

    private static final Logger logger = LoggerFactory.getLogger(MiddleModuleTest.class);

    @Autowired
    MiddlePerformanceService middlePerformanceService;
    @Autowired
    MiddlePerformanceResultService middlePerformanceResultService;


    @Test
    public void testInsert() {
        MiddlePerformance middlePerformance = new MiddlePerformance();
        middlePerformance.setCase_id(1);
        middlePerformance.setThreads(10);
        middlePerformance.setTimes(10);
        middlePerformanceService.insert(middlePerformance);
    }

    @Test
    public void testUpdate() {
        MiddlePerformance middlePerformance = new MiddlePerformance();
        middlePerformance.setId(1);
        middlePerformance.setCase_id(2);
        middlePerformance.setThreads(10);
        middlePerformance.setTimes(10);
        middlePerformanceService.update(middlePerformance);
    }

    @Test
    public void testFindByCaseId() {
        List<MiddlePerformance> middlePerformance = middlePerformanceService.findByCaseId(2);
        logger.info("middlePerformance==" + JSONObject.toJSONString(middlePerformance));
    }


    @Test
    public void testInsertResult() {
        MiddlePerformanceResult middlePerformanceResult = new MiddlePerformanceResult();
        middlePerformanceResult.setCase_id(1);
        middlePerformanceResult.setRun_id(1);
        middlePerformanceResult.setError_rate("15%");
        middlePerformanceResult.setResponse_time(1000);
        middlePerformanceResult.setThroughput(67);
        middlePerformanceResult.setStart_time("2019年12月19日15分35秒");
        middlePerformanceResult.setEnd_time("2019年12月19日15分55秒");
        middlePerformanceResult.setRun_time(120);
        middlePerformanceResult.setThreads(200);
        middlePerformanceResultService.insert(middlePerformanceResult);
    }

    @Test
    public void testFindResultByCaseId() {
        List<MiddlePerformanceResult> middlePerformanceResult = middlePerformanceResultService.findByCaseId(1);
        logger.info("[middleResult]==" + JSONObject.toJSONString(middlePerformanceResult));
    }


    @Test
    public void testFindList() {
        PageInfo pageInfo = middlePerformanceService.findList(1, 10);
        logger.info("[middleResult]==" + JSONObject.toJSONString(pageInfo));
        logger.info("[size]==" + pageInfo.getList().size());
    }

    @Test
    public void testGetLastRunId() {
        int runId = 0;
        try{
             runId = middlePerformanceResultService.getLastRunId();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(runId == 0)
            {
                logger.info("[runId==]==" + runId);
            }
        }

        logger.info("[runId]==" + runId);

    }

    @Test
    public void testRunIdList() {
        PageInfo pageInfo = middlePerformanceResultService.findRunIdList(1, 10);
        logger.info("[pageInfo]==" + pageInfo.getList());

    }

    @Test
    public void testFindByRunId() {
        List<MiddlePerformanceResult> middlePerformanceResults = middlePerformanceResultService.findByRunId(1);
        logger.info("[middlePerformanceResults]==" + JSONObject.toJSONString(middlePerformanceResults));

    }
//    @Test
//    public void testRun() {
//       middlePerformanceService.runJmeterSet(8);
//    }


//    @Test
//    public void testPerformance(){
//
//      for(int i=0; i<5; i++){
//          middlePerformanceService.runSingle(1,1,5);
//
//        }
//
//    }
}
