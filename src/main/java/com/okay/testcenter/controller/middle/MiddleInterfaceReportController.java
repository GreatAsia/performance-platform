package com.okay.testcenter.controller.middle;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.middle.MiddleProject;
import com.okay.testcenter.domain.report.MiddleRequestHistory;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.service.middle.MiddleProjectService;
import com.okay.testcenter.service.middle.MiddleRequestHisoryService;
import com.okay.testcenter.service.middle.MiddleTestHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(description = "中间层报告接口")
@Controller
@RequestMapping(value = "/perf/middle/report")
public class MiddleInterfaceReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MiddleTestHistoryService middleTestHistoryService;
    @Autowired
    MiddleRequestHisoryService middleRequestHisoryService;
    @Autowired
    MiddleProjectService middleProjectService;
    @Autowired
    EnvService envService;

    @GetMapping(value = "/list")
    public String historyList(Model model) {

        PageInfo historyList = middleTestHistoryService.findMiddleTestHistoryByEnvAndProjectId(1, 1, 1, 10);
        List<MiddleProject> projectList =  middleProjectService.findMiddleProjectList();
        model.addAttribute("historyList",historyList);
        model.addAttribute("projectList",projectList);
        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList",envList);
        return "report/MiddleInterfaceReport";

    }


    @GetMapping(value = "/detail/{id}")
    public String reportDetail(@PathVariable("id") int id, Model model){

        MiddleTestHistory middleTestHistory = middleTestHistoryService.findMiddleTestHistoryById(id);
        List<MiddleRequestHistory> middleRequestHistoryList = middleRequestHisoryService.findHistoryByHistoryId(id);
        model.addAttribute("middleTestHistory",middleTestHistory);
        model.addAttribute("middleRequestHistoryList",middleRequestHistoryList);

        return "MiddlePerReportDetail";
    }

    @ApiOperation(value = "中间层报告列表", notes = "中间层报告列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(int env_id, int project_id, int currentPage, int pageSize) {

        PageInfo historyList = middleTestHistoryService.findMiddleTestHistoryByEnvAndProjectId(env_id, project_id, currentPage, pageSize);

        return JSONObject.toJSONString(historyList);

    }


}
