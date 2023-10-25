package com.okay.testcenter;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleInterface;
import com.okay.testcenter.service.middle.MiddleInterfaceService;
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
public class MiddleInterfaceTest {


    private static final Logger logger = LoggerFactory.getLogger(MiddleInterfaceTest.class);
    @Resource
    MiddleInterfaceService MiddleInterfaceService;


    @Test
    public void testInsert(){

        MiddleInterface middleInterface = new MiddleInterface();
        middleInterface.setModule_id(1);
        middleInterface.setName("登录");
        middleInterface.setUrl("/login");
        middleInterface.setRequest_method("GET");
        MiddleInterfaceService.insertMiddleInterface(middleInterface);

    }

    @Test
    public void testUpdate(){

        MiddleInterface middleInterface = new MiddleInterface();
        middleInterface.setId(1);
        middleInterface.setModule_id(1);
        middleInterface.setName("注册");
        middleInterface.setUrl("/login");
        middleInterface.setRequest_method("GET");
        MiddleInterfaceService.updateMiddleInterface(middleInterface);

    }

    @Test
    public void testFindMiddleInterfaceById(){

       MiddleInterface MiddleInterface =  MiddleInterfaceService.findMiddleInterfaceById(1);
       logger.info("[MiddleInterface]" + JSONObject.toJSONString(MiddleInterface));

    }

    @Test
    public void testFindMiddleInterfaceByName(){

        List<MiddleInterface> MiddleInterface = MiddleInterfaceService.findMiddleInterfaceByName("登录");
        logger.info("[MiddleInterface]" + JSONObject.toJSONString(MiddleInterface));

    }

    @Test
    public void testFindMiddleInterfaceByPath() {

        List<MiddleInterface> MiddleInterface = MiddleInterfaceService.findMiddleInterfaceByPath("/home/course_pkg_list");
        logger.info("[MiddleInterface]" + JSONObject.toJSONString(MiddleInterface));

    }

    @Test
    public void testFindMiddleInterfaceList(){

        PageInfo MiddleInterface =  MiddleInterfaceService.findMiddleInterfaceList(1,10);
        logger.info("[MiddleInterface]" + JSONObject.toJSONString(MiddleInterface));

    }

    @Test
    public void testDeleteMiddleInterface(){

        MiddleInterfaceService.deleteMiddleInterface(2);


    }

    @Test
    public void testFindMiddleInterfaceListByModuleId() {

        PageInfo MiddleInterface = MiddleInterfaceService.findMiddleInterfaceByModuleId(1, 1, 10);
        logger.info("[MiddleInterface]" + JSONObject.toJSONString(MiddleInterface));

    }

}
