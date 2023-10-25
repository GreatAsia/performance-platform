package com.okay.testcenter.controller.middle;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.middle.MiddleInterface;
import com.okay.testcenter.domain.middle.MiddleModule;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.service.middle.MiddleCaseService;
import com.okay.testcenter.service.middle.MiddleInterfaceService;
import com.okay.testcenter.service.middle.MiddleModuleService;
import com.okay.testcenter.service.middle.MiddleProjectService;
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

@Api(description = "中间层的项目/模块/接口")
@Controller
@RequestMapping(value = "/perf/middle")
public class MiddleInterfaceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MiddleProjectService middleProjectService;
    @Autowired
    MiddleModuleService middleModuleService;
    @Autowired
    MiddleInterfaceService middleInterfaceService;
    @Autowired
    MiddleCaseService middleCaseService;

    @ApiOperation(value = "添加中间层项目", notes = "添加中间层项目")
    @PostMapping(value = "/addMiddleProject")
    @ResponseBody
    public RetResult<Object> addMiddleProject(@Validated @RequestBody MiddleProject middleProject) {

        middleProjectService.insertMiddleProject(middleProject);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新中间层项目", notes = "更新中间层项目")
    @PostMapping(value = "/updateMiddleProject")
    @ResponseBody
    public RetResult<Object> updateMiddleProject(@Validated @RequestBody MiddleProject middleProject) {

        middleProjectService.updateMiddleProject(middleProject);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除中间层项目", notes = "删除中间层项目")
    @PostMapping(value = "/deleteMiddleProject")
    @ResponseBody
    public RetResult<Object> deleteMiddleProject(@Validated @RequestBody JSONObject request) {

        int id = Integer.parseInt(request.get("id").toString());
        middleProjectService.deleteMiddleProject(id);
        return RetResponse.makeOKRsp();
    }


   
    @ApiOperation(value = "中间层项目列表", notes = "中间层项目列表")
    @GetMapping(value = "/project/getlist")
    @ResponseBody
    public String queryMiddleProject(Page page) {
        PageInfo<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(middleProjectList);
    }

    @ApiOperation(value = "添加中间层模块", notes = "添加中间层模块")
    @PostMapping(value = "/addMiddleModule")
    @ResponseBody
    public RetResult<Object> addMiddleModule(@Validated @RequestBody MiddleModule middleModule) {

        middleModuleService.insertMiddleModule(middleModule);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新中间层模块", notes = "更新中间层模块")
    @PostMapping(value = "/updateMiddleModule")
    @ResponseBody
    public RetResult<Object> updateMiddleModule(@Validated @RequestBody MiddleModule middleModule) {

        middleModuleService.updateMiddleModule(middleModule);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除中间层模块", notes = "删除中间层模块")
    @PostMapping(value = "/deleteMiddleModule")
    @ResponseBody
    public RetResult<Object> deleteMiddleModule(@Validated @RequestBody JSONObject request) {

        int id = Integer.parseInt(request.get("id").toString());
        middleModuleService.deleteMiddleModule(id);
        return RetResponse.makeOKRsp();
    }


    @GetMapping(value = "/module/list")
    public String list(Model model) {

        PageInfo<MiddleModule> middleModuleList = middleModuleService.findMiddleModuleByProjectId(1, 1, 10);
        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();

        model.addAttribute("project_id", 1);
        model.addAttribute("middleModulePageInfo", middleModuleList);
        model.addAttribute("middleProjectList", middleProjectList);
        return "middle/MiddleModule";
    }

    @ApiOperation(value = "通过项目ID获取中间层模块", notes = "通过项目ID获取中间层模块")
    @GetMapping(value = "/module/getlist")
    @ResponseBody
    public String getlist(Integer project_id, Integer currentPage, Integer pageSize) {

        PageInfo<MiddleModule> middleModuleList = middleModuleService.findMiddleModuleByProjectId(project_id, currentPage, pageSize);

        return JSONObject.toJSONString(middleModuleList);
    }

    @ApiOperation(value = "添加中间层接口", notes = "添加中间层接口")
    @PostMapping(value = "/addMiddleInterface")
    @ResponseBody
    public RetResult<Object> addMiddleInterface(@Validated @RequestBody MiddleInterface middleInterface) {

        middleInterfaceService.insertMiddleInterface(middleInterface);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新中间层接口", notes = "更新中间层接口")
    @PostMapping(value = "/updateMiddleInterface")
    @ResponseBody
    public RetResult<Object> updateMiddleInterface(@Validated @RequestBody MiddleInterface middleInterface) {

        middleInterfaceService.updateMiddleInterface(middleInterface);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除中间层接口", notes = "删除中间层接口")
    @PostMapping(value = "/deleteMiddleInterface")
    @ResponseBody
    public RetResult<Object> deleteMiddleInterface(@Validated @RequestBody JSONObject request) {

        int id = Integer.parseInt(request.get("id").toString());
        middleInterfaceService.deleteMiddleInterface(id);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "通过项目ID查找中间层模块(不分页)", notes = "通过项目ID查找中间层模块(不分页)")
    @GetMapping(value = "/queryModuleByProject")
    @ResponseBody
    public List<MiddleModule> queryModuleByProject(@Validated Integer project_id) {

        List<MiddleModule> middleMouleList = middleModuleService.findMiddleModuleByProjectId(project_id);
        return middleMouleList;
    }


    @GetMapping(value = "/interface/list")
    public String InterfaceList(Model model) {

        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();
        model.addAttribute("middleProjectList", middleProjectList);

        int firstProjectId = middleProjectList.get(0).getId();
        List<MiddleModule> middleMouleList = middleModuleService.findMiddleModuleByProjectId(firstProjectId);
        model.addAttribute("middleModuleList", middleMouleList);

        int firstModuleId = middleMouleList.get(0).getId();
        PageInfo<MiddleInterface> middleInterfaceList = middleInterfaceService.findMiddleInterfaceByModuleId(firstModuleId, 1, 10);
        model.addAttribute("middleInterfaceList", middleInterfaceList);


        return "middle/MiddleInterface";
    }

    @ApiOperation(value = "通过模块ID查找中间层接口", notes = "通过模块ID查找中间层接口")
    @GetMapping(value = "/interface/getlist")
    @ResponseBody
    public String InterfaceGetList(Integer module_id, Integer currentPage, Integer pageSize) {

        PageInfo<MiddleInterface> middleInterfaceList = middleInterfaceService.findMiddleInterfaceByModuleId(module_id, currentPage, pageSize);

        return JSONObject.toJSONString(middleInterfaceList);
    }

    @ApiOperation(value = "获取中间层项目列表", notes = "获取中间层项目列表")
    @GetMapping(value = "/middleInterfaceList")
    @ResponseBody
    public List<MiddleProject> middleInterfaceList(Model model) {

        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();
        model.addAttribute("middleProjectList", middleProjectList);


        return middleProjectList;
    }

    @ApiOperation(value = "通过模块ID查找接口", notes = "通过模块ID查找接口")
    @GetMapping(value = "/queryInterfaceByModuleId")
    @ResponseBody
    public List<MiddleInterface> queryInterfaceByModuleId(@Validated Integer module_Id) {

        List<MiddleInterface> middleCaseList = middleInterfaceService.findMiddleInterfaceByModuleId(module_Id);
        return middleCaseList;
    }

    @ApiOperation(value = "通过接口名称查找接口", notes = "通过接口名称查找接口")
    @GetMapping(value = "/findMiddleInterfaceByName")
    @ResponseBody
    public String findMiddleInterfaceByName(String interface_name) {

        List<MiddleInterface> middleInterfaceList;

        if (interface_name.contains("/")) {

            middleInterfaceList = middleInterfaceService.findMiddleInterfaceByPath(interface_name);

        } else {

            middleInterfaceList = middleInterfaceService.findMiddleInterfaceByName(interface_name);
        }


        return JSONObject.toJSONString(middleInterfaceList);
    }


}
