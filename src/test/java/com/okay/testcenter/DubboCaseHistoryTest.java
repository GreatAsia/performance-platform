package com.okay.testcenter;



import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import com.okay.testcenter.service.dubbo.DubboInterfaceHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboCaseHistoryTest {

    @Resource
    DubboInterfaceHistoryService dubboInterfaceHistoryService;

    private static final Logger logger = LoggerFactory.getLogger(DubboCaseHistoryTest.class);

    @Test
    public void testInsertHistory() {

        DubboCaseHistory dubboCaseHistory = new DubboCaseHistory();
        dubboCaseHistory.setHistoryId(166);
        dubboCaseHistory.setModelId(1);
        dubboCaseHistory.setCaseName("登录");
        dubboCaseHistory.setRequestData("key:value");
        dubboCaseHistory.setEnv("dev");
        dubboCaseHistory.setStartTime("2019-1-23");
        dubboCaseHistory.setEndTime("2019-1-23");
        dubboCaseHistory.setResponseContent("code:0");
        dubboCaseHistory.setResult("PASS");

        dubboInterfaceHistoryService.insertHistory(dubboCaseHistory);
    }

    @Test
    public void testUpdateHistory() {

        DubboCaseHistory dubboCaseHistory = new DubboCaseHistory();
        dubboCaseHistory.setId(2);
        dubboCaseHistory.setHistoryId(2);
        dubboCaseHistory.setModelId(1);
        dubboCaseHistory.setCaseName("注册");
        dubboCaseHistory.setRequestData("key:value");
        dubboCaseHistory.setEnv("dev");
        dubboCaseHistory.setStartTime("2018-12-27");
        dubboCaseHistory.setEndTime("2018-12-27");
        dubboCaseHistory.setResponseContent("code:0");
        dubboCaseHistory.setResult("PASS");

        dubboInterfaceHistoryService.updateHistory(dubboCaseHistory);
    }

    @Test
    public void testFindHistory() {

        DubboCaseHistory dubboCaseHistory = dubboInterfaceHistoryService.findHistoryById(1);
        logger.info("[dubboInterfaceHistory]:" + JSONObject.toJSONString(dubboCaseHistory));

    }

    @Test
    public void testDeleteHistory() {

        dubboInterfaceHistoryService.deleteHistory(1);

    }


    @Test
    public void testFindHistoryList() {

        PageInfo pageInfo = dubboInterfaceHistoryService.findHistoryList(1, 10);
        logger.info("pageInfo" + JSONObject.toJSONString(pageInfo));

    }

    @Test
    public void testFindHistoryByModel() {

        PageInfo pageInfo = dubboInterfaceHistoryService.findHistoryByModel(1, 1, 10);
        logger.info("[pageInfo]:" + pageInfo);

    }


    @Test
    public void testFindHistoryByHistoryId() {

        List<DubboCaseHistory> dubboCaseHistoryList = dubboInterfaceHistoryService.findHistoryByHistoryId(108);
        logger.info("【dubboInterfaceHistoryList】:" + JSONObject.toJSON(dubboCaseHistoryList));
    }

}
