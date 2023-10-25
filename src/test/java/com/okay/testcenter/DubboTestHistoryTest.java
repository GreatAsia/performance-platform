package com.okay.testcenter;



import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import com.okay.testcenter.service.dubbo.DubboTestHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboTestHistoryTest {

    private static final Logger logger = LoggerFactory.getLogger(DubboTestHistoryTest.class);
    @Resource
    DubboTestHistoryService dubboTestHistoryService;

    @Test
    public void testInsertHistory() {

        DubboTestHistory dubboTestHistory = new DubboTestHistory();
        dubboTestHistory.setEnv("dev");
        dubboTestHistory.setResult("FAIL");
        dubboTestHistory.setTotalCase(2);
        dubboTestHistory.setPassCase(1);
        dubboTestHistory.setFailCase(1);
        dubboTestHistory.setStartTime("2019-01-25:18:58:01");
        dubboTestHistory.setEndTime("2019-01-25:18:59:02");
        dubboTestHistory.setModelId(2);
        dubboTestHistoryService.insertTestHistory(dubboTestHistory);
    }

    @Test
    public void testUpdateHistory() {

        DubboTestHistory dubboTestHistory = new DubboTestHistory();
        dubboTestHistory.setId(1);
        dubboTestHistory.setEnv("dev");
        dubboTestHistory.setResult("FAIL");
        dubboTestHistory.setTotalCase(2);
        dubboTestHistory.setPassCase(1);
        dubboTestHistory.setFailCase(1);
        dubboTestHistory.setStartTime("2019-01-25:18:58:01");
        dubboTestHistory.setEndTime("2019-01-25:18:59:02");
        dubboTestHistory.setModelId(1);
        dubboTestHistoryService.updateTestHistory(dubboTestHistory);

    }

    @Test
    public void testFindHistoryById() {

        DubboTestHistory dubboTestHistory = dubboTestHistoryService.findTestHistoryById(107);
        logger.info("[dubboTestHistory]:" + JSONObject.toJSONString(dubboTestHistory));

    }

    @Test
    public void testDeleteHistory() {

        dubboTestHistoryService.deleteTestHistory(2);

    }


    @Test
    public void testFindHistoryList() {

        PageInfo pageInfo = dubboTestHistoryService.findTestHistoryList(1, 10);
        System.out.println("pageInfo:" + JSONObject.toJSONString(pageInfo));

    }

    @Test
    public void testGetLastTestHistoryId() {

        int id = dubboTestHistoryService.getLastTestHistoryId();
        System.out.println("【id】:" + id);
    }

    @Test
    public void testFindTestHistoryListByModelAndEnv() {

        PageInfo pageInfo = dubboTestHistoryService.findTestHistoryListByModelIdAndEnv(2, "hotfix", 1, 10);
        logger.info("【pageInfo:】" + JSONObject.toJSON(pageInfo.getList()));
    }
}
