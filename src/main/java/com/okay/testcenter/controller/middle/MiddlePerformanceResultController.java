package com.okay.testcenter.controller.middle;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.middle.MiddlePerformanceReport;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Api(description = "中间层压测结果接口")
@Controller
@RequestMapping(value = "/perf/middle/per/result")
@Validated
public class MiddlePerformanceResultController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    MiddlePerformanceResultService middlePerformanceResultService;


    @GetMapping(value = "/list")
    public String list(Model model) {

        PageInfo pageInfo = middlePerformanceResultService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "report/MiddlePerformanceReport";
    }

    @ApiOperation(value = "中间层压测结果列表", notes = "中间层压测结果列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(Page page) {

        PageInfo pageInfo = middlePerformanceResultService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "通过caseId查找中间层压测计划", notes = "通过caseId查找中间层压测计划")
    @GetMapping(value = "/findByCaseId")
    @ResponseBody
    public String findByCaseId(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId) {

        List<MiddlePerformanceResult> middlePerformance = middlePerformanceResultService.findByCaseId(caseId);
        return JSONObject.toJSONString(middlePerformance);
    }

    @ApiOperation(value = "中间层压测结果详情", notes = "中间层压测结果详情")
    @GetMapping(value = "/detail/{caseId}")
    public String reportDetail(@PathVariable("caseId") int caseId, Model model) {

        MiddlePerformanceReport errorRateReport = new MiddlePerformanceReport();
        MiddlePerformanceReport responseTimeReport = new MiddlePerformanceReport();
        MiddlePerformanceReport throughPutReport = new MiddlePerformanceReport();
        ArrayList errorRate = new ArrayList();
        ArrayList responseTime = new ArrayList();
        ArrayList throughPut = new ArrayList();
        ArrayList runTime = new ArrayList();
        List<MiddlePerformanceResult> middleResultList = middlePerformanceResultService.findByCaseId(caseId);
        String caseName = middleResultList.get(0).getCase_name();

        for (MiddlePerformanceResult result : middleResultList) {
            errorRate.add(Float.parseFloat(result.getError_rate()));
            responseTime.add(result.getResponse_time());
            throughPut.add(result.getThroughput());
            runTime.add(result.getStart_time());
        }
        errorRateReport.setErrorRate(errorRate);
        errorRateReport.setRunTime(runTime);
        errorRateReport.setCaseName(caseName);

        responseTimeReport.setResponseTime(responseTime);
        responseTimeReport.setRunTime(runTime);
        responseTimeReport.setCaseName(caseName);

        throughPutReport.setThroughPut(throughPut);
        throughPutReport.setRunTime(runTime);
        throughPutReport.setCaseName(caseName);

        model.addAttribute("errorRateReport", errorRateReport);
        model.addAttribute("responseTimeReport", responseTimeReport);
        model.addAttribute("throughPutReport", throughPutReport);

        return "report/MiddlePerReportDetail";
    }


    @GetMapping(value = "/runId/list")
    public String runIdList(Model model) {


        PageInfo pageInfo = middlePerformanceResultService.findRunIdList(1, 10);

        model.addAttribute("pageInfo", pageInfo);
        return "report/MiddlePerformanceRunIdList";
    }

    @ApiOperation(value = "中间层压测结果列表", notes = "中间层压测结果列表")
    @GetMapping(value = "/runId/getlist")
    @ResponseBody
    public String runIdGetlist(Page page) {

        PageInfo pageInfo = middlePerformanceResultService.findRunIdList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "中间层压测结果RunId详情", notes = "中间层压测结果RunId详情")
    @GetMapping(value = "/runId/detail/{runId}")
    public String runIdReportDetail(@PathVariable("runId") int runId, Model model) {

        List<MiddlePerformanceResult> middlePerformance = middlePerformanceResultService.findByRunId(runId);
        model.addAttribute("pageInfo", middlePerformance);
        return "report/MiddlePerformanceRunIdReport";


    }

}
