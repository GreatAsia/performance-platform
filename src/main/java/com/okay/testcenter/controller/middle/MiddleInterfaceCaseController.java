package com.okay.testcenter.controller.middle;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.middle.MiddleCase;
import com.okay.testcenter.domain.middle.MiddleInterface;
import com.okay.testcenter.domain.middle.MiddleModule;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.service.middle.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "中间层用例接口")
@Controller
@RequestMapping(value = "/perf/middle")
public class MiddleInterfaceCaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MiddleProjectService middleProjectService;
    @Autowired
    MiddleModuleService middleModuleService;
    @Autowired
    MiddleInterfaceService middleInterfaceService;
    @Autowired
    MiddleCaseService middleCaseService;
    @Autowired
    EnvService envService;

    @ApiOperation(value = "添加中间层用例", notes = "添加中间层用例")
    @PostMapping(value = "/addMiddleCase")
    @ResponseBody
    public RetResult<Object> addMiddleCase(@Validated @RequestBody MiddleCase middleCase) {

        middleCaseService.insertMiddleCase(middleCase);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新中间层用例", notes = "更新中间层用例")
    @PostMapping(value = "/updateMiddleCase")
    @ResponseBody
    public RetResult<Object> updateMiddleCase(@Validated @RequestBody MiddleCase middleCase) {

        middleCaseService.updateMiddleCase(middleCase);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除中间层用例", notes = "删除中间层用例")
    @PostMapping(value = "/deleteMiddleCase")
    @ResponseBody
    public RetResult<Object> deleteMiddleCase(@Validated @RequestBody JSONObject request) {

        int id = request.getInteger("id");
        middleCaseService.deleteMiddleCase(id);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "通过ID查找中间层用例", notes = "通过ID查找中间层用例")
    @GetMapping(value = "/queryCaseById")
    @ResponseBody
    public MiddleCase queryCaseById(@Validated Integer id) {

        MiddleCase middleCaseList = middleCaseService.findMiddleCaseById(id);
        return middleCaseList;
    }

    @ApiOperation(value = "通过名称查找中间层用例", notes = "通过名称查找中间层用例")
    @GetMapping(value = "/queryCaseByName")
    @ResponseBody
    public String queryCaseByName(@Validated String caseName) {
        List<MiddleCase> middleCaseList;

        if (caseName.contains("/")) {

            middleCaseList = middleCaseService.findMiddleCaseByPath(caseName);

        } else {

            middleCaseList = middleCaseService.findMiddleCaseByName(caseName);
        }

        return JSONObject.toJSONString(middleCaseList);
    }


    @GetMapping(value = "/case/list")
    public String caseList(Model model) {

        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();
        model.addAttribute("middleProjectList", middleProjectList);

        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList",envList);

        int projectId = middleProjectList.get(0).getId();
        int envId = envList.get(0).getId();

        List<MiddleModule> moduleList = middleModuleService.findMiddleModuleByProjectId(projectId);
        model.addAttribute("moduleList", moduleList);

        int moduleId = moduleList.get(0).getId();
        List<MiddleInterface> interfaceList = middleInterfaceService.findMiddleInterfaceByModuleId(moduleId);
        model.addAttribute("interfaceList", interfaceList);

        PageInfo<MiddleCase> middleCasePageInfo = middleCaseService.findMiddleCaseByEnvAndInterface(envId, projectId, 1, 10);
        model.addAttribute("middleCasePageInfo", middleCasePageInfo);


        return "middle/MiddleInterfaceCase";
    }

    @ApiOperation(value = "通过环境ID和接口ID获取中间层用例列表", notes = "通过环境ID和接口ID获取中间层用例列表")
    @GetMapping(value = "/case/getlist")
    @ResponseBody
    public String caseGetList(Integer env_id, Integer interface_id, Integer currentPage, Integer pageSize) {

        PageInfo<MiddleCase> middleCasePageInfo = middleCaseService.findMiddleCaseByEnvAndInterface(env_id, interface_id, currentPage, pageSize);

        return JSONObject.toJSONString(middleCasePageInfo);
    }

    @ApiOperation(value = "获取中间层用例列表", notes = "获取中间层用例列表")
    @GetMapping(value = "/middleCaseList")
    @ResponseBody
    public PageInfo middleCaseList(Page page) {

        PageInfo middleCaseList = middleCaseService.findMiddleCaseList(page.getCurrentPage(), page.getPageSize());

        return middleCaseList;
    }


}
