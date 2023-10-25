package com.okay.testcenter;



import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleModule;
import com.okay.testcenter.service.middle.MiddleModuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddleModuleTest {


    private static final Logger logger = LoggerFactory.getLogger(MiddleModuleTest.class);
    @Resource
    MiddleModuleService MiddleModuleService;


    @Test
    public void testInsert(){

        MiddleModule middleModule = new MiddleModule();
        middleModule.setProject_id(5);
        middleModule.setName("上课");
        MiddleModuleService.insertMiddleModule(middleModule);

    }

    @Test
    public void testUpdate(){

        MiddleModule middleModule = new MiddleModule();
        middleModule.setName("作业");
        middleModule.setId(1);
        middleModule.setProject_id(1);
        middleModule.setComments("");
        MiddleModuleService.updateMiddleModule(middleModule);

    }

    @Test
    public void testFindMiddleModuleById(){

       MiddleModule MiddleModule =  MiddleModuleService.findMiddleModuleById(1);
       logger.info("[MiddleModule]" + JSONObject.toJSONString(MiddleModule));

    }

    @Test
    public void testFindMiddleModuleByName(){

        MiddleModule MiddleModule =  MiddleModuleService.findMiddleModuleByName("作业");
        logger.info("[MiddleModule]" + JSONObject.toJSONString(MiddleModule));

    }

    @Test
    public void testFindMiddleModuleList(){

        PageInfo MiddleModule =  MiddleModuleService.findMiddleModuleList(1,10);
        logger.info("[MiddleModule]" + JSONObject.toJSONString(MiddleModule));

    }

    @Test
    public void testDeleteMiddleModule(){

        MiddleModuleService.deleteMiddleModule(2);


    }


    @Test
    public void testFindMiddleModuleByProjectId() {

        PageInfo MiddleModule = MiddleModuleService.findMiddleModuleByProjectId(8, 1, 10);
        logger.info("[MiddleModule]" + JSONObject.toJSONString(MiddleModule));


    }


}
