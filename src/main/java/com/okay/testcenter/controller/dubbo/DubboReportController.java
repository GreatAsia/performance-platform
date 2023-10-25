package com.okay.testcenter.controller.dubbo;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import com.okay.testcenter.service.dubbo.DubboInterfaceHistoryService;
import com.okay.testcenter.service.dubbo.DubboModelService;
import com.okay.testcenter.service.dubbo.DubboTestHistoryService;
import com.okay.testcenter.service.middle.EnvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "Dubbo报告接口")
@Controller
@RequestMapping(value = "/perf/dubbo/report")
public class DubboReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DubboInterfaceHistoryService dubboInterfaceHistoryService;
    @Resource
    DubboTestHistoryService dubboTestHistoryService;
    @Resource
    DubboModelService dubboModelService;
    @Resource
    EnvService envService;


    @GetMapping(value = "/detail/{id}")
    public String dubboReportDetail(@PathVariable("id") int id, Model model) {

        DubboTestHistory dubboTestHistory = dubboTestHistoryService.findTestHistoryById(id);
        List<DubboCaseHistory> dubboCaseHistoryList = dubboInterfaceHistoryService.findHistoryByHistoryId(id);

        model.addAttribute("dubboTestHistory", dubboTestHistory);
        model.addAttribute("dubboInterfaceHistoryList", dubboCaseHistoryList);
        return "report/DobboInterfaceReportDetail";


    }


    @GetMapping(value = "/list")
    public String dubboReportList(Model model) {

        PageInfo dubboReportList;

        dubboReportList = dubboTestHistoryService.findTestHistoryListByModelIdAndEnv(1, "dev", 1, 10);
        List<DubboModule> dubboModuleList = dubboModelService.findModelList();

        model.addAttribute("dubboReportList", dubboReportList);
        model.addAttribute("dubboModelList", dubboModuleList);
        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList", envList);
        return "report/DobboReportList";

    }

    @ApiOperation(value = "Dubbo报告列表", notes = "获取Dubbo报告列表")
    @GetMapping(value = "/getList")
    @ResponseBody
    public String getDubboReportList(int moduleId, String env, int currentPage, int pageSize) {

        PageInfo getDubboReportList = dubboTestHistoryService.findTestHistoryListByModelIdAndEnv(moduleId, env, currentPage, pageSize);
        return JSONObject.toJSONString(getDubboReportList);

    }

}
