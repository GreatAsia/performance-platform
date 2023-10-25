package com.okay.testcenter;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import com.okay.testcenter.service.middle.MiddleTestHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddleTestHistoryTest {

    private static final Logger logger = LoggerFactory.getLogger(MiddleTestHistoryTest.class);

    @Resource
    MiddleTestHistoryService middleTestHistoryService;


    @Test
    public void testInsertMiddleTestHistory() {

        MiddleTestHistory middleTestHistory = new MiddleTestHistory();
        middleTestHistory.setEnv(1);
        middleTestHistory.setResult("PASS");
        middleTestHistory.setTotalCase(2);
        middleTestHistory.setPassCase(1);
        middleTestHistory.setFailCase(1);
        middleTestHistory.setStartTime("2019-06-12:10:42:10");
        middleTestHistory.setEndTime("2019-06-12:10:42:15");
        middleTestHistory.setProjectId(1);
        middleTestHistoryService.insertMiddleTestHistory(middleTestHistory);

    }

    @Test
    public void testupdateMiddleTestHistory() {

        MiddleTestHistory middleTestHistory = new MiddleTestHistory();
        middleTestHistory.setId(1);
        middleTestHistory.setEnv(1);
        middleTestHistory.setResult("FAIL");
        middleTestHistory.setTotalCase(2);
        middleTestHistory.setPassCase(1);
        middleTestHistory.setFailCase(1);
        middleTestHistory.setStartTime("2019-06-12:10:42:10");
        middleTestHistory.setEndTime("2019-06-12:10:42:15");
        middleTestHistory.setProjectId(1);
        middleTestHistoryService.updateMiddleTestHistory(middleTestHistory);

    }

    @Test
    public void testDeleteMiddleTestHistory() {


        middleTestHistoryService.deleteMiddleTestHistory(2);

    }

    @Test
    public void testfindMiddleTestHistoryById() {

        MiddleTestHistory middleTestHistory = middleTestHistoryService.findMiddleTestHistoryById(1);
        logger.info("[middleTestHistory]==" + JSONObject.toJSONString(middleTestHistory));
    }

    @Test
    public void testfindMiddleTestHistoryList() {

        PageInfo pageInfo = middleTestHistoryService.findMiddleTestHistoryList(1, 10);
        logger.info("[pageInfo]==" + JSONObject.toJSONString(pageInfo));
    }


    @Test
    public void testfindMiddleTestHistoryByEnvAndProjectId() {

        PageInfo pageInfo = middleTestHistoryService.findMiddleTestHistoryByEnvAndProjectId(1, 1, 1, 10);
        logger.info("[pageInfo]==" + JSONObject.toJSONString(pageInfo));
    }


    @Test
    public void testfindLoginInfoByProjectAndEnv() {
        RequestSampler requestSampler = middleTestHistoryService.findLoginInfoByProjectAndEnv(1, 1);
        logger.info("[requestSampler]==" + JSONObject.toJSONString(requestSampler));
    }


}
