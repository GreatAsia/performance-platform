package com.okay.testcenter;



import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.service.middle.MiddleProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddleProjectTest {


    private static final Logger logger = LoggerFactory.getLogger(MiddleProjectTest.class);
    @Resource
    MiddleProjectService middleProjectService;


    @Test
    public void testInsert(){

        MiddleProject middleProject = new MiddleProject();
        middleProject.setName("教师空间");
        middleProjectService.insertMiddleProject(middleProject);

    }

    @Test
    public void testUpdate(){

        MiddleProject middleProject = new MiddleProject();
        middleProject.setName("学生Pad");
        middleProject.setId(1);
        middleProjectService.updateMiddleProject(middleProject);

    }

    @Test
    public void testFindMiddleProjectById(){

       MiddleProject middleProject =  middleProjectService.findMiddleProjectById(1);
       logger.info("[middleProject]" + JSONObject.toJSONString(middleProject));

    }

    @Test
    public void testFindMiddleProjectByName(){

        MiddleProject middleProject =  middleProjectService.findMiddleProjectByName("教师空间");
        logger.info("[middleProject]" + JSONObject.toJSONString(middleProject));

    }

    @Test
    public void testFindMiddleProjectList(){

        PageInfo middleProject =  middleProjectService.findMiddleProjectList(1,10);
        logger.info("[middleProject]" + JSONObject.toJSONString(middleProject));

    }

    @Test
    public void testDeleteMiddleProject(){

        middleProjectService.deleteMiddleProject(2);


    }



}
