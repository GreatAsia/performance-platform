package com.okay.testcenter;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.AddUserHistory;
import com.okay.testcenter.service.tool.AddUserHistoryService;
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
public class AddUserHistoryTest {


    private static final Logger logger = LoggerFactory.getLogger(AddUserHistoryTest.class);
    @Resource
    AddUserHistoryService addUserHistoryService;


    @Test
    public void testInsert() {

        AddUserHistory addUserHistory = new AddUserHistory();
        addUserHistory.setUserType("学生");
        addUserHistory.setTotalCount(10);
        addUserHistory.setCreateTime("2020-03-02:21:33:12");
        addUserHistory.setUpdateTime("2020-03-02:21:33:15");
        int id = addUserHistoryService.insertHistory(addUserHistory);
        logger.info("id==" + id);
    }

    @Test
    public void updateInsert() {

        AddUserHistory addUserHistory = new AddUserHistory();
        addUserHistory.setId(1);
        addUserHistory.setUserType("老师");
        addUserHistory.setTotalCount(20);
        addUserHistory.setCreateTime("2020-03-02:21:33:12");
        addUserHistory.setUpdateTime("2020-03-02:21:33:15");
        addUserHistoryService.updateHistory(addUserHistory);
    }


    @Test
    public void findById() {

        AddUserHistory addUserHistory = addUserHistoryService.findHistoryById(1);
        logger.info("[addUserHistory]" + JSONObject.toJSONString(addUserHistory));

    }

    @Test
    public void findList() {

        List<AddUserHistory> addUserHistoryList = addUserHistoryService.findHistoryList();
        logger.info("[addUserHistoryList]" + JSONObject.toJSONString(addUserHistoryList));
    }
}
