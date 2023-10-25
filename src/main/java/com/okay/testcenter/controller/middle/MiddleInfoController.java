package com.okay.testcenter.controller.middle;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.middle.LinkEnvProject;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.service.middle.LinkEnvProjectService;
import com.okay.testcenter.service.middle.MiddleProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "中间层信息管理接口")
@Controller
@RequestMapping(value = "/perf/middle/info")
public class MiddleInfoController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    LinkEnvProjectService linkEnvProjectService;
    @Resource
    MiddleProjectService middleProjectService;
    @Resource
    EnvService envService;

    @ApiOperation(value = "添加中间层账号信息", notes = "添加中间层账号信息")
    @PostMapping(value = "/insert")
    @ResponseBody
    public RetResult<Object> insert(@Validated @RequestBody LinkEnvProject linkEnvProject) {

        linkEnvProjectService.insertLinkEnvProject(linkEnvProject);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "更新中间层账号信息", notes = "更新中间层账号信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RetResult<Object> update(@Validated @RequestBody LinkEnvProject linkEnvProject) {

        linkEnvProjectService.updateLinkEnvProject(linkEnvProject);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "删除中间层账号信息", notes = "删除中间层账号信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RetResult<Object> delete(@Validated @RequestBody JSONObject request) {
        int id = request.getInteger("id");
        linkEnvProjectService.deleteLinkEnvProject(id);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "通过ID查找中间层账号信息", notes = "通过ID查找中间层账号信息")
    @GetMapping(value = "/queryById")
    @ResponseBody
    public LinkEnvProject queryById(@Validated Integer id) {

        LinkEnvProject linkEnvProject = linkEnvProjectService.findLinkEnvProjectById(id);
        return linkEnvProject;
    }

    @ApiOperation(value = "通过项目ID查找中间层账号信息", notes = "通过项目ID查找中间层账号信息")
    @GetMapping(value = "/queryByProjectId")
    @ResponseBody
    public List<LinkEnvProject> queryByProjectId(@Validated Integer project_id) {

        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectService.findLinkEnvProjectByProjectId(project_id);
        return linkEnvProjectList;
    }

    @ApiOperation(value = "通过项目名称查找中间层账号信息", notes = "通过项目名称查找中间层账号信息")
    @GetMapping(value = "/queryByProjectName")
    @ResponseBody
    public String queryByProjectName(@Validated String project_name) {

        List<LinkEnvProject> linkEnvProjectList = linkEnvProjectService.findLinkEnvProjectByProjectName(project_name);
        return JSONObject.toJSONString(linkEnvProjectList);
    }


    @GetMapping(value = "/list")
    public String list(Model model) {

        PageInfo pageInfo = linkEnvProjectService.findLinkEnvProjectByProjectList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();
        model.addAttribute("middleProjectList", middleProjectList);
        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList",envList);

        return "middle/LinkEnvProject";
    }

    @ApiOperation(value = "中间层账号信息列表", notes = "中间层账号信息列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(@Validated Page page) {

        PageInfo pageInfo = linkEnvProjectService.findLinkEnvProjectByProjectList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


}
