package com.okay.testcenter;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.middle.LinkEnvProject;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.service.middle.LinkEnvProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkEnvProjectTest {

    @Resource
    LinkEnvProjectService linkEnvProjectService;

    @Resource
    EnvService envService;

    @Test
    public void testFindList() {

        PageInfo linkEnvProjectList = linkEnvProjectService.findLinkEnvProjectByProjectList(1, 10);
        System.out.println("[linkEnvProjectList]==" + JSONObject.toJSONString(linkEnvProjectList));
    }


    @Test
    public void testFindByProjectIdList() {

        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectService.findLinkEnvProjectByProjectId(1);
        System.out.println("[linkEnvProjectList]==" + JSONObject.toJSONString(linkEnvProjectList));
    }


    @Test
    public void testFindByProjectIdListByName() {
        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectService.findLinkEnvProjectByProjectName("teacher");
        System.out.println("[linkEnvProjectList]==" + JSONObject.toJSONString(linkEnvProjectList));
    }


    @Test
    public void testFindEnvByName() {

        Env env = envService.findEnvByName("docker");
        System.out.println("[env]==" + JSONObject.toJSONString(env));

    }

    @Test
    public void testFindEnvList() {

        List<Env> env = envService.findEnvList();
        System.out.println("[envList]==" + JSONObject.toJSONString(env));

    }

}
