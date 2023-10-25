package com.okay.testcenter;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.report.MiddleRequestHistory;
import com.okay.testcenter.service.middle.MiddleRequestHisoryService;
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
public class MiddleRequestHistroyTest {


    private static final Logger logger = LoggerFactory.getLogger(MiddleRequestHistroyTest.class);

    @Resource
    MiddleRequestHisoryService middleRequestHisoryService;

    @Test
    public void testinsertMiddleRequestHistory() {

        MiddleRequestHistory middleRequestHistory = new MiddleRequestHistory();
        middleRequestHistory.setHistoryId(1);
        middleRequestHistory.setInterfaceId(100);
        middleRequestHistory.setRequestId("000");
        middleRequestHistory.setUrl("/login");
        middleRequestHistory.setCaseName("登录");
        middleRequestHistory.setRequestData("username=61951377365&password=Okay@123&validate_code=");
        middleRequestHistory.setEnv("1");
        middleRequestHistory.setStartTime("2019-6-13:15:27:01");
        middleRequestHistory.setEndTime("2019-6-13:15:28:01");
        middleRequestHistory.setResponseContent("result");
        middleRequestHistory.setResult("PASS");
        middleRequestHistory.setResponseCode(200);
        middleRequestHistory.setElapsed_time("2000");
        middleRequestHisoryService.insertMiddleRequestHistory(middleRequestHistory);
    }


    @Test
    public void testupdateMiddleRequestHistory() {

        MiddleRequestHistory middleRequestHistory = new MiddleRequestHistory();
        middleRequestHistory.setId(1);
        middleRequestHistory.setHistoryId(1);
        middleRequestHistory.setInterfaceId(1);
        middleRequestHistory.setRequestId("010670064456");
        middleRequestHistory.setUrl("/login");
        middleRequestHistory.setCaseName("登录");
        middleRequestHistory.setRequestData("username=61951377365&password=Okay@123&validate_code=");
        middleRequestHistory.setEnv("1");
        middleRequestHistory.setStartTime("2019-6-13:15:27:01");
        middleRequestHistory.setEndTime("2019-6-13:15:28:01");
        middleRequestHistory.setResponseContent("result");
        middleRequestHistory.setResult("PASS");
        middleRequestHistory.setElapsed_time("3000");
        middleRequestHisoryService.updateMiddleRequestHistory(middleRequestHistory);


    }


    @Test
    public void testdeleteMiddleRequestHistory() {
        middleRequestHisoryService.deleteMiddleRequestHistory(2);
    }


    @Test
    public void testfindMiddleRequestHistoryById() {

        MiddleRequestHistory middleRequestHistory = middleRequestHisoryService.findMiddleRequestHistoryById(1);
        logger.info("[middleRequestHistory]==" + JSONObject.toJSONString(middleRequestHistory));
    }

    @Test
    public void testfindMiddleRequestHistoryList() {

        PageInfo pageInfo = middleRequestHisoryService.findMiddleRequestHistoryList(1, 10);
        logger.info("[pageInfo]==" + JSONObject.toJSONString(pageInfo));
    }


    @Test
    public void testfindHistoryByHistoryId() {

        List<MiddleRequestHistory> middleRequestHistoryList = middleRequestHisoryService.findHistoryByHistoryId(1);
        logger.info("[pageInfo]==" + JSONObject.toJSONString(middleRequestHistoryList));
    }

    @Test
    public void testfindMiddleRequestHistoryByInterfaceId() {

        PageInfo pageInfo = middleRequestHisoryService.findMiddleRequestHistoryByInterfaceId(1, 1, 10);
        logger.info("[pageInfo]==" + JSONObject.toJSONString(pageInfo));
    }


}
