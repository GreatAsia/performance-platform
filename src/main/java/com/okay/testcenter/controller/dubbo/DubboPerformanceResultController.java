package com.okay.testcenter.controller.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.dubbo.DubboPerformanceReport;
import com.okay.testcenter.domain.dubbo.DubboPerformanceResult;
import com.okay.testcenter.service.dubbo.DubboPerformanceResultService;
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

@Api(description = "dubbo压测结果接口")
@Controller
@RequestMapping(value = "/perf/dubbo/per/result")
@Validated
public class DubboPerformanceResultController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DubboPerformanceResultService dubboPerformanceResultService;


    @GetMapping(value = "/list")
    public String list(Model model) {

        PageInfo pageInfo = dubboPerformanceResultService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "report/DubboPerformanceReport";
    }

    @ApiOperation(value = "中间层压测结果列表", notes = "中间层压测结果列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(Page page) {

        PageInfo pageInfo = dubboPerformanceResultService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "通过caseId查找中间层压测计划", notes = "通过caseId查找中间层压测计划")
    @GetMapping(value = "/findByCaseId")
    @ResponseBody
    public String findByCaseId(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId) {

        List<DubboPerformanceResult> list = dubboPerformanceResultService.findByCaseId(caseId);
        return JSONObject.toJSONString(list);
    }

    @ApiOperation(value = "中间层压测结果详情", notes = "中间层压测结果详情")
    @GetMapping(value = "/detail/{caseId}")
    public String reportDetail(@PathVariable("caseId") int caseId, Model model) {

        DubboPerformanceReport errorRateReport = new DubboPerformanceReport();
        DubboPerformanceReport responseTimeReport = new DubboPerformanceReport();
        DubboPerformanceReport throughPutReport = new DubboPerformanceReport();
        ArrayList errorRate = new ArrayList();
        ArrayList responseTime = new ArrayList();
        ArrayList throughPut = new ArrayList();
        ArrayList runTime = new ArrayList();
        List<DubboPerformanceResult> dubboPerformanceResults = dubboPerformanceResultService.findByCaseId(caseId);
        String caseName = dubboPerformanceResults.get(0).getCase_name();

        for (DubboPerformanceResult result : dubboPerformanceResults) {
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

        return "report/DubboPerReportDetail";
    }


    @GetMapping(value = "/runId/list")
    public String runIdList(Model model) {


        PageInfo pageInfo = dubboPerformanceResultService.findRunIdList(1, 10);

        model.addAttribute("pageInfo", pageInfo);
        return "report/DubboPerformanceRunIdList";
    }

    @ApiOperation(value = "中间层压测结果列表", notes = "中间层压测结果列表")
    @GetMapping(value = "/runId/getlist")
    @ResponseBody
    public String runIdGetlist(Page page) {

        PageInfo pageInfo = dubboPerformanceResultService.findRunIdList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "中间层压测结果RunId详情", notes = "中间层压测结果RunId详情")
    @GetMapping(value = "/runId/detail/{runId}")
    public String runIdReportDetail(@PathVariable("runId") int runId, Model model) {

        List<DubboPerformanceResult> list = dubboPerformanceResultService.findByRunId(runId);
        model.addAttribute("pageInfo", list);
        return "report/DubboPerformanceRunIdReport";


    }

}
