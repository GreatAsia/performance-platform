package com.okay.testcenter;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import com.okay.testcenter.service.middle.MiddlePerformanceSetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhou
 * @date 2019/12/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlePerformanceSetTest {

    private static final Logger logger = LoggerFactory.getLogger(MiddleModuleTest.class);

    @Autowired
    MiddlePerformanceSetService middlePerformanceSetService;

   @Test
    public void testInsert(){
       MiddlePerformanceSet middlePerformanceSet = new MiddlePerformanceSet();
       middlePerformanceSet.setPlan_set("1,2");
       middlePerformanceSet.setSet_name("测试");
       middlePerformanceSetService.insert(middlePerformanceSet);

   }

   @Test
    public void findByName(){
      MiddlePerformanceSet middlePerformanceSet =  middlePerformanceSetService.findByName("测试");
       logger.info("middlePerformanceSet==" + JSONObject.toJSONString(middlePerformanceSet));
   }

    @Test
    public void testRunSet() {

    }


}
